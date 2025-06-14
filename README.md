# 🧠 Smart Recipe Image Recognition App

An AI-powered Android mobile application that allows users to scan ingredients from their fridge using real-time image recognition and receive personalized recipe suggestions based on the detected items.

---

## 🚀 Features

- 📸 Real-time image classification using phone camera  
- 🥬 Classifies common food items with >85% accuracy using a custom-trained CNN  
- 🍽️ Fetches dynamic recipe suggestions from third-party APIs based on scanned items  
- 🔐 Firebase Authentication and Realtime Database integration  
- 📱 Intuitive mobile UI built with Android Studio (Java, XML layouts)  
- 🛠️ Integrated TensorFlow Lite and OpenCV for fast, efficient image processing on-device  

---

## 🎯 Motivation

Inspired by the idea of reducing food waste and simplifying home cooking, this app helps users make smarter choices with what's already in their fridge. Built as part of a second-year software engineering project and awarded **Best Project of the Year** by the University of Leicester.

---

## 📚 Background & Research

Developing this application required deep research into:

### 📷 Image Recognition Technology
Image recognition is central to the app’s functionality, enabling automatic detection of ingredients from photos. To achieve this:
- Explored various ML frameworks and libraries including PyTorch, TensorFlow, and OpenCV.
- Chose **TensorFlow** for its compatibility with Android and **OpenCV** for image pre-processing.
- Converted the trained CNN to **TensorFlow Lite** for mobile deployment.

### 📱 Mobile App Development with Android Studio
- Android Studio was selected due to its native support for Java and wide device compatibility.
- Leveraged prior experience in Java to accelerate UI design, testing, and deployment.
- Implemented XML layouts and Firebase integration for a responsive and dynamic user experience.

---

## 🛠 Tech Stack

| Layer         | Tools / Libraries |
|---------------|-------------------|
| Mobile UI     | Android Studio, Java, XML, Firebase UI |
| ML Model      | TensorFlow, TensorFlow Lite, OpenCV, NumPy, Pandas |
| Backend/API   | RESTful APIs (e.g., Spoonacular), Firebase Realtime DB |
| Tools         | Git, GitHub, Android Emulator, Python |
| ML Training   | Custom CNN (image classification, 85%+ accuracy) |

---

## 🧪 Model Details

- **Input:** JPEG images captured via in-app camera interface  
- **Preprocessing:** Image resizing, normalization (handled via OpenCV)  
- **Architecture:** 5-layer convolutional neural network (CNN) using ReLU activation  
- **Dataset:** 12-class food image dataset curated for fridge ingredients  
- **Training:** Stratified cross-validation used for model validation  
- **Accuracy:** Achieved **85.3%** on validation set  
- **Deployment:** Converted to **TensorFlow Lite** format and integrated directly in the app

---

## 📲 How It Works

1. **User Login:** Authenticates via Firebase  
2. **Scan Ingredients:** Launches camera, captures fridge image  
3. **Recognize Items:** CNN model classifies visible ingredients  
4. **Suggest Recipes:** Fetches recipes from third-party APIs based on identified items  
5. **User Feedback:** Ratings/comments stored in Firebase for future enhancements

---

## 🚧 Future Improvements

- ✅ Add barcode scanning as an alternative to image input  
- 🔄 Implement YOLO-style multi-object detection for more precise classification  
- 🌐 Cache recipe API responses for offline usage  
- 📊 Add per-recipe nutritional breakdown and caloric estimates  

---

## 👤 Author

**Krish Patel**  
BSc Software Engineering @ University of Leicester  
🔗 [LinkedIn](https://www.linkedin.com/in/grad-krish/) • 📧 krish280703@gmail.com

