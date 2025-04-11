# 📹 Android RTSP Player App

Download the [apk](https://github.com/by15190/rtsp_client/releases/tag/v1.0.0)

This is a modern Android app built using **Jetpack Compose**, **Media3 (ExoPlayer)**, and **LibVLC** that lets you stream, search, and record RTSP video streams with support for **Picture-in-Picture** mode.



## 🚀 Features

- ✅ RTSP Streaming via `Media3` (ExoPlayer)
- 📽️ Recording RTSP stream using **LibVLC**
- 🔍 Search Bar for filtering
- 🖼️ Picture-in-Picture (PiP) support
- 💾 Saves recorded videos to `Movies/` public directory
- 📁 Plays from `raw/` resource videos as fallback

---

## 📸 Screenshots

### 🏠 Home Screen
![Home Screen](https://github.com/by15190/rtsp_client/blob/master/app/src/main/res/image1.png)

### 🔴 Streaming RTSP
![RTSP Stream](screenshots/streaming.png)

### 💾 Recording Saved
![Recording](screenshots/recording_saved.png)

### 📺 Picture-in-Picture
![PiP Mode](screenshots/pip_mode.png)

---

## 🛠 Tech Stack

- **Jetpack Compose** – UI
- **androidx.media3** – for ExoPlayer & media session
- **LibVLC** – for recording RTSP
- **Kotlin**

---



## ⚙️ Setup & Run

1. Clone this repo
2. Open in Android Studio
3. Run on a real device or emulator (RTSP needs network)
4. Add your RTSP stream link and hit "Play"

---

## 📥 Output Location

Recorded videos are saved in:  
📁 `Movies/recording_<timestamp>.mp4`

---


