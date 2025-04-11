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
<img src="https://github.com/by15190/rtsp_client/blob/master/app/src/main/res/image1.png" alt="Home Screen" width="300"/>


### 🔴 Streaming RTSP
<img src="https://github.com/by15190/rtsp_client/blob/master/app/src/main/res/image2.png" alt="Home Screen" width="300"/>

➡️ Enter your RTSP URL in the search bar and tap the search icon to start streaming.

### 💾 Start Recording 
<img src="https://github.com/by15190/rtsp_client/blob/master/app/src/main/res/image2.png" alt="Home Screen" width="300"/>
▶️ Hit the Record button after streaming starts.

### 💾 Stop Recording 
<img src="https://github.com/by15190/rtsp_client/blob/master/app/src/main/res/image4.png" alt="Home Screen" width="300"/>
🛑 Tap the Record button again to stop recording. The video is automatically saved.

### 💾 Recording Saved
<img src="https://github.com/by15190/rtsp_client/blob/master/app/src/main/res/image6.png" alt="Home Screen" width="300"/>
✅ Saved in your Movies directory.

### 📺 Picture-in-Picture
<img src="https://github.com/by15190/rtsp_client/blob/master/app/src/main/res/image3.png" alt="Home Screen" width="300"/>
📲 Tap the PiP button to continue watching while multitasking.

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


