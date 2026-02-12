# MealMate 🍲

**MealMate** is a feature-rich Android application designed to help users discover delicious recipes, plan their weekly meals, and manage their favorites. Built with a robust architecture and modern technology stack, MealMate ensures a seamless and responsive experience for food enthusiasts.

Developed by **Abdelrahman Waheed**.

## 📸 App Screenshots

| Splash | Onboarding 1 | Onboarding 2 | Onboarding 3 |
|--------|--------------|--------------|--------------|
| <img src="https://github.com/user-attachments/assets/6a172649-e634-440e-b550-f2e3ed37299f" width="220"/> | <img src="https://github.com/user-attachments/assets/a26c3a8b-bdb1-414b-aa93-cdf0b4ae8e18" width="220"/> | <img src="https://github.com/user-attachments/assets/2a31e57e-7482-4a4e-bdda-21843def2ff4" width="220"/> | <img src="https://github.com/user-attachments/assets/4ab8f2d1-ad7e-4432-9b91-746c3f2ea1e9" width="220"/> |

| Login | Register | Home | Discover |
|-------|----------|------|----------|
| <img src="https://github.com/user-attachments/assets/cc67fa88-04ce-421b-8344-32e2040a12c5" width="220"/> | <img src="https://github.com/user-attachments/assets/4d1be119-e66b-4717-99ee-a8c1187a222b" width="220"/> | <img src="https://github.com/user-attachments/assets/c3c69a75-065c-485c-9b6b-5238dca1a41e" width="220"/> | <img src="https://github.com/user-attachments/assets/3c3244a9-721a-4c9c-bde7-e4cfb82c0906" width="220"/> |

| Filter List | Filter Details | Search Results | Meal Details |
|--------------|------------------|----------------|--------------|
| <img src="https://github.com/user-attachments/assets/20480d4e-4d2e-41b7-b156-270747cec72e" width="220"/> | <img src="https://github.com/user-attachments/assets/9642dade-622c-4e3b-85c9-4bf405aa8ab9" width="220"/> | <img src="https://github.com/user-attachments/assets/26a619d6-ff35-4e23-9eab-7d2901a3c2ce" width="220"/> | <img src="https://github.com/user-attachments/assets/dec69e0f-3052-4127-9e5b-47adb88655d3" width="220"/> |

| Plan | Favorites | Profile | Restricted Access |
|------|-----------|---------|-------|
| <img src="https://github.com/user-attachments/assets/19196b3a-8b5e-4989-bae1-1b4d6f3027d4" width="220"/> | <img src="https://github.com/user-attachments/assets/ae08bb80-e5f4-42a8-b825-e160457fe84d" width="220"/> | <img src="https://github.com/user-attachments/assets/efc48a69-abe2-49ef-b343-4827823d7862" width="220"/> | <img src="https://github.com/user-attachments/assets/f22f58c2-3758-440d-8943-1dacbd1b026c" width="220"/> |


## ✨ Features

### 🔍 Meal Discovery
- **Daily Inspiration**: Get a randomly suggested "Meal of the Day" on your home screen.
- **Browse Categories**: Explore meals by categories (e.g., Seafood, Vegetarian, Dessert) or areas (e.g., Italian, Indian, Egyptian).
- **Advanced Search**: Find specific recipes by name or filter through various criteria like main ingredient, category, or area.
- **Detailed Recipes**: View comprehensive meal details, including ingredients with measurements, step-by-step instructions, and integrated video tutorials.

### ❤️ Personalization
- **Favorites Management**: Save your favorite meals with a single tap for quick access later.
- **Offline Access**: Robust local caching using Room ensures your saved favorites and plans are accessible even without an internet connection.
- **Guest Mode**: Explore the app's features and browse delicious recipes without the need for an account.

### 📅 Weekly Meal Planning
- **Intuitive Scheduler**: Organize your meals for the entire week with a dedicated planning interface.
- **Management**: Easily add, view, and remove meals from specific days of the week.
- **Today's Overview**: Quickly see what's on the menu for today.

### ☁️ Sync & Security
- **Multi-Platform Authentication**: Securely log in using Email/Password, Google, or Facebook.
- **Real-time Cloud Sync**: Synchronize your favorites and weekly plans across multiple devices via Firebase.
- **Profile Customization**: Manage your profile picture with cloud storage integration.

---

## 🏗️ Project Architecture

MealMate is built following the **Clean Architecture** principles, utilizing the **Model-View-Presenter (MVP)** pattern for the UI layer and the **Repository Pattern** for data management.

### 🧩 UI Layer (MVP)
The UI is decoupled from the business logic, making it highly testable and maintainable.
- **View**: Activities and Fragments handle UI rendering and user interactions. They implement specific View interfaces to receive updates from the Presenter.
- **Presenter**: Contains the presentation logic. It reacts to user actions, fetches data from the Model (Repository), and updates the View.
- **Contract**: Defines the interaction between the View and the Presenter for each feature.

### 📂 Data Layer (Repository Pattern)
The repository acts as a single source of truth for data, abstracting the source (Network vs. Local).
- **Remote Data Source**: Fetches data from TheMealDB API using Retrofit and handles Firebase interactions.
- **Local Data Source**: Manages persistent storage using Room Database and SharedPreferences for session management.
- **Mappers**: Transform data between API models and local entities/domain models.

---

## 🛠️ Technology Stack

MealMate leverages a variety of modern Android development tools and libraries:

- **Language**: Java
- **Architecture**: MVP (Model-View-Presenter)
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) for efficient API communication.
- **Reactive Programming**: [RxJava 3](https://github.com/ReactiveX/RxJava) & [RxAndroid](https://github.com/ReactiveX/RxAndroid) for handling asynchronous data streams.
- **Local Persistence**: [Room](https://developer.android.com/training/data-storage/room) for caching and complex data management.
- **Cloud Infrastructure**: 
  - **Firebase Auth**: Social and Email authentication.
  - **Firestore**: Real-time NoSQL database for syncing user data.
  - **Firebase Storage**: Hosting user profile images.
- **UI/UX Components**:
    - **Material Design 3**: For a modern and cohesive look.
    - **Glide**: For high-performance image loading and transformation.
    - **Shimmer**: For engaging loading states and animations.
    - **YouTube Player SDK**: For seamless recipe video integration.
    - **View Binding**: For type-safe view interaction.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio.
- JDK 11 or 17.
- A Firebase project with Google and Facebook login configured.

### Setup Instructions
1. **Clone the project**:
   ```bash
   git clone https://github.com/AbdoWa7eed/MealMate.git
   ```
2. **Open in Android Studio**:
   Import the project and wait for Gradle sync.
3. **Firebase Setup**:
   - Download your `google-services.json` from the Firebase Console.
   - Place it in the `app/` directory.
   - Setup Facebook Login follow the [official guide](https://developers.facebook.com/docs/facebook-login/android/).
4. **API Key**:
   The app uses TheMealDB. Ensure you have network access.

---

## 👤 Author

**Abdelrahman Waheed**
- **LinkedIn**: [Abdelrahman Waheed](https://www.linkedin.com/in/abdelrahmanwa7eed-dev/)
- **GitHub**: [@AbdoWa7eed](https://github.com/AbdoWa7eed)

---

## 📄 License

This project was developed as part of the Android Development Track, Intake 46, at ITI (Information Technology Institute).
