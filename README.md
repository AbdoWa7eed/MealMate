# MealMate 🍲

**MealMate** is a feature-rich Android application designed to help users discover delicious recipes, plan their weekly meals, and manage their favorites. Built with a robust architecture and modern technology stack, MealMate ensures a seamless and responsive experience for food enthusiasts.

Developed by **Abdelrahman Waheed**.

---

## ✨ Features

### 🔍 Meal Discovery
- **Daily Inspiration**: Get a randomly suggested "Meal of the Day" on your home screen.
- **Browse Categories**: Explore meals by categories (e.g., Seafood, Vegetarian, Dessert) or areas (e.g., Italian, Indian, Egyptian).
- **Advanced Search**: Find specific recipes by name or filter through various criteria.
- **Detailed Recipes**: View comprehensive meal details, including ingredients, step-by-step instructions, and even video tutorials.

### ❤️ Personalization
- **Favorites**: Save your favorite meals for quick access later.
- **Offline Access**: View your saved favorites even when you're not connected to the internet.
- **Guest Mode**: Explore the app's features even without creating an account.

### 📅 Meal Planning
- **Weekly Planner**: Organize your meals for the entire week.
- **Seamless Scheduling**: Easily add meals to specific days of the week.
- **Management**: View and modify your planned meals at any time.

### ☁️ Sync & Security
- **Secure Authentication**: Register and log in using Email, Google, or Facebook.
- **Cloud Sync**: Synchronize your favorites and meal plans across devices using Firebase.
- **Profile Management**: Update your profile information and manage your account settings.

---

## 🛠️ Technology Stack

MealMate leverages a variety of modern Android development tools and libraries:

- **Language**: Java
- **Architecture**: MVP (Model-View-Presenter)
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) with [Gson](https://github.com/google/gson) for API communication.
- **Async Processing**: [RxJava 3](https://github.com/ReactiveX/RxJava) & [RxAndroid](https://github.com/ReactiveX/RxAndroid) for efficient background operations.
- **Local Storage**: [Room Persistence Library](https://developer.android.com/training/data-storage/room) for caching and offline support.
- **Backend/Cloud**: [Firebase](https://firebase.google.com/) (Auth, Firestore, Storage) for real-time synchronization and image hosting.
- **UI/UX**:
    - [Material Design Components](https://material.io/components) for a premium look and feel.
    - [Glide](https://github.com/bumptech/glide) for smooth image loading and caching.
    - [Facebook Login SDK](https://developers.facebook.com/docs/facebook-login/android/) for social authentication.
    - [Android YouTube Player](https://github.com/PierfrancescoSoffritti/android-youtube-player) for integrated video content.
    - [Shimmer/Lottie](https://github.com/facebook/shimmer-android) for beautiful loading states.

---

## 🏗️ Project Architecture

The app follows the **Model-View-Presenter (MVP)** pattern, ensuring a clean separation of concerns and making the codebase easier to test and maintain.

- **Model**: Handles data business logic, interacting with the Network (Retrofit) and Database (Room).
- **View**: Fragments and Activities that handle the UI and user interactions.
- **Presenter**: Acts as the middleman, taking data from the Model and updating the View.

The data layer uses a **Repository Pattern** to manage data sources (Remote vs. Local) and provide a clean API to the presenters.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (or newer).
- JDK 11.
- A Firebase project (for full functionality).

### Setup Instructions
1. **Clone the project**:
   ```bash
   git clone https://github.com/AbdoWa7eed/MealMate.git
   ```
2. **Open in Android Studio**:
   Select the `MealMate` directory and wait for Gradle sync to complete.
3. **Firebase Configuration**:
   - Create a new project on the [Firebase Console](https://console.firebase.google.com/).
   - Enable **Authentication** (Email, Google, Facebook), **Firestore**, and **Storage**.
   - Download the `google-services.json` file and place it in the `app/` directory.
4. **Run the App**:
   Connect an Android device or start an emulator and click the "Run" button in Android Studio.

---

## 👤 Author

**Abdelrahman Waheed**
- Portfolio/LinkedIn: [Abdelrahman Waheed](https://www.linkedin.com/in/abdelrahmanwa7eed-dev/)
- GitHub: [@AbdoWa7eed](https://github.com/AbdoWa7eed)

---

## 📄 License

This project is for educational purposes as part of the ITI Android Development program.
