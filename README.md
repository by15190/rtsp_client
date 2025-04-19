# 📹 Android RTSP Player App

📥 [Download APK](https://github.com/by15190/rtsp_client/releases/tag/v1.0.0)

A modern Android app built using **Jetpack Compose**, **Media3 (ExoPlayer)**, and **LibVLC** to stream, search, and record RTSP video streams — with support for **Picture-in-Picture** mode.

---

## 🚀 Features

- ✅ RTSP Streaming via `Media3` (ExoPlayer)
- 📽️ Recording RTSP stream using **LibVLC**
- 🔍 Search Bar for filtering
- 🖼️ Picture-in-Picture (PiP) support
- 💾 Saves recorded videos to `Movies/` public directory
- 📁 Plays from `raw/` resource videos as fallback

---

## 🎬 Demo Video

<img src="https://github.com/by15190/rtsp_client/blob/master/app/src/main/res/Demo.gif" width="300" height="600" 
  />

➡️ Enter your RTSP URL in the search bar and tap the search icon to start streaming  
▶️ Hit the Record button to start recording  
🛑 Tap again to stop and auto-save the recording  
📲 Use PiP to keep watching while multitasking

---

## 🛠 Tech Stack

- **Jetpack Compose** – UI
- **androidx.media3** – ExoPlayer & MediaSession
- **LibVLC** – for recording RTSP streams
- **Kotlin**

---

## ⚙️ Setup & Run

1. Clone this repo  
2. Open in Android Studio  
3. Run on a real device or emulator (RTSP needs network access)  
4. Add your RTSP stream link and hit "Play"

---

## 📥 Output Location

Recorded videos are saved in:  
📁 `Movies/recording_<timestamp>.mp4`

---
