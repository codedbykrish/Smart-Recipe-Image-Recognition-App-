package com.example.imagepro;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Pair;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class objectDetectionClass {
    // this is used to load model and predict
    private Interpreter interpreter;
    // Over here i store all label in array
    private List<String> labelList;
    private int INPUT_SIZE;
    private int PIXEL_SIZE=3; // This is for RGB
    private int IMAGE_MEAN=0;
    private  float IMAGE_STD=255.0f;
    // This i use to initialize the gpu in the app
    private GpuDelegate gpuDelegate;
    private int height=0;
    private  int width=0;


    objectDetectionClass(AssetManager assetManager, String modelPath, String labelPath, int inputSize) throws IOException{
        INPUT_SIZE=inputSize;
        // use to define gpu or cpu // no. of threads
        Interpreter.Options options=new Interpreter.Options();
        gpuDelegate=new GpuDelegate();
        options.addDelegate(gpuDelegate);
        options.setNumThreads(4); // This is where i set tht number of threads
        // Over here we load model
        interpreter=new Interpreter(loadModelFile(assetManager,modelPath),options);
        // Over here we load labelmap
        labelList=loadLabelList(assetManager,labelPath);
    }

    // Method to load labels from the asset file
    private List<String> loadLabelList(AssetManager assetManager, String labelPath) throws IOException {
        // This line is used to store label
        List<String> labelList=new ArrayList<>();
        // Over here i create a new reader
        BufferedReader reader=new BufferedReader(new InputStreamReader(assetManager.open(labelPath)));
        String line;
        // Here i loop through each line and store it to labelList
        while ((line=reader.readLine())!=null){
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    // Method to load the TensorFlow Lite model from assets
    private ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        // This code here is used to get description of file
        AssetFileDescriptor fileDescriptor=assetManager.openFd(modelPath);
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset =fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength);
    }

    // create new Mat function
    public Mat recognizeImage(Mat mat_image){
        // Rotate original image by 90 degree get get portrait frame
        Mat rotated_mat_image=new Mat();
        Mat a  = mat_image.t(); // Transpose the img matrix

        Core.flip(a,rotated_mat_image,1); // Fliping the matrix horizontally
        a.release();

        // Below i convert it to bitmap
        Bitmap bitmap=null;
        bitmap=Bitmap.createBitmap(rotated_mat_image.cols(),rotated_mat_image.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(rotated_mat_image,bitmap);

        height=bitmap.getHeight();
        width=bitmap.getWidth();

        Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,INPUT_SIZE,INPUT_SIZE,false); // Here we Scale the bitmap to the model's input size

        ByteBuffer byteBuffer=convertBitmapToByteBuffer(scaledBitmap); // We Convert bitmap to byte buffer for model input

        Object[] input=new Object[1];
        input[0]=byteBuffer;

        Map<Integer,Object> output_map=new TreeMap<>();
        // Here we created treemap of three array (boxes,score,classes)
        float[][][]boxes =new float[1][10][4];

        // 10: top 10 object detected
        // 4: there coordinate in image
        float[][] scores=new float[1][10];
        // This stores scores of 10 object
        float[][] classes=new float[1][10];
        // This stores class of object


        // Then we add it to object_map;
        output_map.put(0,boxes);
        output_map.put(1,classes);
        output_map.put(2,scores);


        // This is where we predict
        interpreter.runForMultipleInputsOutputs(input,output_map);

        Object value=output_map.get(0);
        Object Object_class=output_map.get(1);
        Object score=output_map.get(2);

        // Here we loop through each object
        List<String> detectedIngredients = new ArrayList<>();

        for (int i=0;i<10;i++){
            float class_value=(float) Array.get(Array.get(Object_class,0),i);
            float score_value=(float) Array.get(Array.get(score,0),i);

            if(score_value>0.5){
                String ingredient = labelList.get((int) class_value);
                detectedIngredients.add(ingredient);
                Object box1=Array.get(Array.get(value,0),i);
                // Here we are multiplying it with Original height and width of frame


                float top=(float) Array.get(box1,0)*height;
                float left=(float) Array.get(box1,1)*width;
                float bottom=(float) Array.get(box1,2)*height;
                float right=(float) Array.get(box1,3)*width;
                // draw rectangle in Original frame //  starting point    // ending point of box  // color of box         //thickness Size
                Imgproc.rectangle(rotated_mat_image,new Point(left,top),new Point(right,bottom),new Scalar(0, 255, 0, 255),2);
                // This is to write the text on frame
                                                // string of class name of object  // starting point                       // color of text           // size of text
                Imgproc.putText(rotated_mat_image,labelList.get((int) class_value),new Point(left,top),3,1,new Scalar(255, 0, 0, 255),2);
            }

        }

        Mat b = rotated_mat_image.t();
        Core.flip(rotated_mat_image.t(),mat_image,0);
        b.release();
        return mat_image;
    }

    public Pair<Mat, List<String>> recognizePhoto(Mat mat_image){
        // Convert the Mat image to a Bitmap for processing.
        Bitmap bitmap=null;
        bitmap=Bitmap.createBitmap(mat_image.cols(),mat_image.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat_image,bitmap);
        // Get the hight and width
        height=bitmap.getHeight();
        width=bitmap.getWidth();
        Bitmap scaledBitmap=Bitmap.createScaledBitmap(bitmap,INPUT_SIZE,INPUT_SIZE,false);
        ByteBuffer byteBuffer=convertBitmapToByteBuffer(scaledBitmap);

        // Here we are preparing the model's input object.
        Object[] input=new Object[1];
        input[0]=byteBuffer;

        // Here we are prepare the output map to capture the model's output.
        Map<Integer,Object> output_map=new TreeMap<>();

        float[][][]boxes =new float[1][10][4];
        float[][] scores=new float[1][10];
        float[][] classes=new float[1][10];

        output_map.put(0,boxes);
        output_map.put(1,classes);
        output_map.put(2,scores);

        // Run the model.
        interpreter.runForMultipleInputsOutputs(input,output_map);

        // We Extract results from the output map here.
        Object value=output_map.get(0);
        Object Object_class=output_map.get(1);
        Object score=output_map.get(2);

        // Here We loop through each object and collect recognized ingredients based on the output.
        List<String> detectedIngredients = new ArrayList<>();
        for (int i=0;i<10;i++){
            float class_value=(float) Array.get(Array.get(Object_class,0),i);
            float score_value=(float) Array.get(Array.get(score,0),i);
            if(score_value>0.5){
                Object box1=Array.get(Array.get(value,0),i);
                String ingredient = labelList.get((int) class_value);
                detectedIngredients.add(ingredient);

                float top=(float) Array.get(box1,0)*height;
                float left=(float) Array.get(box1,1)*width;
                float bottom=(float) Array.get(box1,2)*height;
                float right=(float) Array.get(box1,3)*width;
                // draw rectangle in Original frame //  starting point    // ending point of box  // color of box       thickness
                Imgproc.rectangle(mat_image,new Point(left,top),new Point(right,bottom),new Scalar(0, 255, 0, 255),2);
                // This is to write the text on frame
                                              // string of class name of object  // starting point                 // color of text           // size of text
                Imgproc.putText(mat_image,labelList.get((int) class_value),new Point(left,top),3,1,new Scalar(255, 0, 0, 255),2);

            }

        }

        // Then we return the modified image and the list of detected ingredients.
        return new Pair<>(mat_image, detectedIngredients);
    }

    // This is a helper function that converts a Bitmap into a ByteBuffer for TensorFlow Lite model input.
    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer;

        int quant=1;
        int size_images=INPUT_SIZE;
        if(quant==0){
            byteBuffer=ByteBuffer.allocateDirect(1*size_images*size_images*3);
        }
        else {
            byteBuffer=ByteBuffer.allocateDirect(4*1*size_images*size_images*3);
        }
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues=new int[size_images*size_images];
        bitmap.getPixels(intValues,0,bitmap.getWidth(),0,0,bitmap.getWidth(),bitmap.getHeight());
        int pixel=0;

        for (int i=0;i<size_images;++i){
            for (int j=0;j<size_images;++j){
                final  int val=intValues[pixel++];
                if(quant==0){
                    byteBuffer.put((byte) ((val>>16)&0xFF));
                    byteBuffer.put((byte) ((val>>8)&0xFF));
                    byteBuffer.put((byte) (val&0xFF));
                }
                else {
                    byteBuffer.putFloat((((val >> 16) & 0xFF))/255.0f);
                    byteBuffer.putFloat((((val >> 8) & 0xFF))/255.0f);
                    byteBuffer.putFloat((((val) & 0xFF))/255.0f);
                }
            }
        }

    return byteBuffer;
    }
}