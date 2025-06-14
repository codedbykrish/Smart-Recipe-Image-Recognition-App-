# 🧠 Smart Recipe Image Recognition App

An AI-powered Android mobile application that allows users to scan ingredients from their fridge using real-time image recognition and receive personalized recipe suggestions based on the detected items.

---

## 🚀 Features

- 📸 Real-time image classification using phone camera
- 🥬 Classifies common food items with >85% accuracy using a custom-trained CNN
- 🍽️ Fetches dynamic recipe suggestions from third-party APIs based on scanned items
- 🔐 Firebase Authentication and Realtime Database integration
- 📱 Intuitive mobile UI built with Android Studio (XML layouts)

---

## 🎯 Motivation

Inspired by the idea of reducing food waste and simplifying home cooking, this app helps users make smarter choices with what's already in their fridge. Built as part of a second-year software engineering project and awarded **Best Project of the Year** by the University of Leicester.

---

## 🛠 Tech Stack

| Layer         | Tools / Libraries |
|---------------|-------------------|
| Mobile UI     | Android Studio, XML, Firebase UI |
| ML Model      | TensorFlow, NumPy, Pandas, Matplotlib |
| Backend/API   | RESTful APIs (recipe providers), Firebase Realtime DB |
| Tools         | Git, GitHub, Android Emulator, Python |
| ML Training   | Custom CNN (image classification, 85%+ accuracy) |

---

## 🧪 Model Details

- **Input:** JPEG images of fridge contents
- **Preprocessing:** Resized + normalized input tensors
- **Architecture:** 5-layer CNN with ReLU activations, trained on 12 classes of food items
- **Performance:** Achieved **85.3% accuracy** on validation set using stratified cross-validation
- **Export:** Model converted and integrated into Android using TensorFlow Lite


---

## 🚧 Future Improvements

- ✅ Add barcode scanning as an alternative to image input  
- 🔄 Improve multi-item recognition (YOLO-style object detection)  
- 🌐 Cache API results for offline access  
- 📊 Add nutritional breakdown per recipe

---

## 👤 Author

**Krish Patel**  
BSc Software Engineering @ University of Leicester  
🔗 [LinkedIn](https://www.linkedin.com/in/grad-krish/) • 📧 krish280703@gmail.com

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
