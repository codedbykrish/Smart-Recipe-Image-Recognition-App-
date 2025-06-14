# 🧠 Smart Recipe — AI-Powered Ingredient Scanner & Recipe Suggestion App

**Build Status:** Stable Prototype (Android APK available)

---

## Table of Contents

- What Is This?  
- Key Features  
- How It Works  
- Architecture & Algorithms  
- Tech Stack  
- Getting Started  
- Prerequisites  
- Building & Running  
- Example Use Case  
- Future Improvements  
- License  

---

## What Is This?

**Smart Recipe** is an innovative Android mobile app that helps users decide what to cook by automatically recognizing ingredients in their fridge via the phone camera and suggesting personalized recipes. It leverages advanced **image recognition** powered by a custom-trained convolutional neural network (CNN), integrated seamlessly into a user-friendly mobile interface.

This project demonstrates applied machine learning, mobile app development, and backend integration for real-time, practical problem-solving aimed at reducing food waste and simplifying meal prep.

---

## Key Features

- **Real-time Ingredient Recognition:** Uses phone camera to classify fridge items with 85%+ accuracy.  
- **Custom CNN Model:** Trained on 12 common food classes, optimized for mobile inference via TensorFlow Lite.  
- **Dynamic Recipe Suggestions:** Queries third-party APIs to recommend recipes matching detected ingredients.  
- **User Authentication & Data Storage:** Firebase Authentication and Realtime Database integration for user profiles and preferences.  
- **Intuitive Android UI:** Clean, responsive design built with Android Studio and XML layouts.  
- **User Feedback Mechanism:** In-app feedback for iterative improvements.  

---

## How It Works

### Initialization

- User logs in or creates an account via Firebase Authentication.  
- Opens camera interface to capture fridge contents.  

### Image Recognition

- Captured images are preprocessed (resized, normalized).  
- Passed through a TensorFlow Lite CNN model embedded in the app.  
- Model outputs ingredient classifications with confidence scores.  

### Recipe Suggestion

- Detected ingredients are sent to recipe provider APIs via REST calls.  
- API returns dynamic recipe lists tailored to available items.  
- Recipes displayed in-app with nutritional info and preparation details.  

### Feedback Loop

- Users can rate and comment on suggested recipes and app experience.  
- Feedback stored in Firebase Realtime Database to guide future updates.  

---

## Architecture & Algorithms

**CNN Model:**  
- 5-layer convolutional neural network with ReLU activations.  
- Trained on labelled fridge image dataset covering 12 food categories (e.g., tomato, carrot, cheese).  
- Achieves 85.3% validation accuracy using stratified cross-validation.  

**Mobile Integration:**  
- Model exported and optimized using TensorFlow Lite for on-device inference.  
- Image preprocessing done in-app using OpenCV (resizing, normalization).  

**Backend:**  
- Firebase handles user authentication and stores user data/feedback.  
- Recipe suggestions fetched from external RESTful recipe APIs (e.g., Spoonacular).  

---

## Tech Stack

| Layer           | Tools & Libraries                   |
|-----------------|-----------------------------------|
| Mobile UI       | Android Studio, Java, XML Layouts |
| Machine Learning| Python, TensorFlow, NumPy, OpenCV |
| Backend         | Firebase Authentication, Realtime DB |
| APIs            | RESTful Recipe Providers           |
| Development     | Git, GitHub, Android Emulator      |

