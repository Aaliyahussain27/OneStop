# OneStop 

A modern, productivity-focused Android application designed to be your all-in-one companion for habit tracking, study sessions, and spiritual growth. Built with Jetpack Compose and Material 3, OneStop offers a clean, minimal interface that helps you stay focused and organized.

---

## ✨ Features

### 📅 Goal Tracker
- **Task Management**: Set and track your daily goals easily.
- **Progress Tracking**: Visualize your daily progress and completed tasks directly from the home screen.
- **Persistent Data**: Built with Room database for secure, local storage of your goals.

### ⏱️ Study Tracker
- **Focus Timer**: Dedicated study timer to track your deep work sessions.
- **Study History**: Keep track of your session durations and view your total study focus time.
- **Productivity Stats**: Get insights into your study habits directly on the dashboard.

### 🎵 Playlist Manager
- **Session Curations**: Organize and manage study-specific playlists or sessions.
- **Customization**: Create, update, and manage your focus sessions to suit your workflow.

### 📖 Spiritual Growth
- **Ayah of the Moment**: Fetch and display random Ayahs from the Quran using external APIs.
- **Translations**: View English translations alongside the original Arabic text for deeper reflection.
- **One-Tap Refresh**: Get a new verse instantly to keep yourself inspired.

---

## 🛠️ Tech Stack

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for a modern, declarative UI.
- **Programming Language**: [Kotlin](https://kotlinlang.org/) for concise and expressive code.
- **Architecture**: MVVM (Model-ViewModel-Viewmodel) for clean separation of concerns.
- **Database**: [Room Persistence Library](https://developer.android.com/training/data-storage/room) for local data management.
- **Networking**: [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson) for API integrations.
- **State Management**: Kotlin Coroutines and StateFlow for reactive UI updates.
- **Design System**: [Material 3](https://m3.material.io/) for a sleek, modern look.

---

## 📂 Project Structure

```text
com.example.onestop
├── features
│   ├── goalTracker     # Goal-related UI and data logic
│   ├── playlist        # Playlist/Session management
│   └── studytracker    # Focus timer and session history logic
├── home
│   ├── quranQuote      # API integration for daily quotes
│   └── HomeScreen.kt   # Central dashboard UI
├── navigation          # Jetpack Compose Navigation routes
├── ui.theme            # Custom Material 3 branding and color schemes
└── MainActivity.kt     # Main entry point
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- JDK 17
- Android SDK 24+ (Min SDK 24)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/Aaliyahussain27/OneStop.git
   ```
2. Open the project in Android Studio.
3. Wait for Gradle sync to complete.
4. Run the app on an Emulator or a Physical Device.

---

## 📸 Screenshots

*(Add screenshots here)*

---

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

Built with ❤️ by [Aaliya Hussain](https://github.com/Aaliyahussain27)
