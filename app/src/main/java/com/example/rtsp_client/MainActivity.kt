package com.example.rtsp_client

import android.app.PictureInPictureParams
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.Rational
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerView
import com.example.rtsp_client.ui.theme.Rtsp_clientTheme
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import java.io.File

class MainActivity : ComponentActivity() {
    private lateinit var libVLC: LibVLC
    private lateinit var url: String
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var player: ExoPlayer
    private val isInPipMode = mutableStateOf(false)
    private lateinit var recordingPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        // initialize libvlc and media player
        libVLC = LibVLC(this)
        mediaPlayer = MediaPlayer(libVLC)

        super.onCreate(savedInstanceState)
        player = ExoPlayer.Builder(this).build()
        enableEdgeToEdge()

        val session = MediaSession.Builder(this, player).build()
        session.player = player

        setContent {
            val context = LocalContext.current
            val focusManager = LocalFocusManager.current
            var urlstate by rememberSaveable { mutableStateOf("") }
            var recordingState by rememberSaveable { mutableStateOf(false) }


            Rtsp_clientTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { focusManager.clearFocus() }
                            .padding(innerPadding)) {
                        if (!isInPipMode.value) {
                            searchBar(
                                modifier = Modifier,
                                url = urlstate,
                                onValueChange = { urlstate = it },
                                onSearch = {
                                    focusManager.clearFocus()
                                    val streamUrl = urlstate

                                    val mediaItem = MediaItem.Builder().setUri(streamUrl)
                                        .setMimeType(MimeTypes.APPLICATION_RTSP) // Important for RTSP
                                        .build()

                                    player.setMediaItem(mediaItem)
                                    player.prepare()
                                    player.play()

                                },
                                context = context
                            ).also { url = urlstate }
                            Spacer(Modifier.height(30.dp))
                        }

                        videoView(
                            modifier = Modifier, url = urlstate, player = player, context = context
                        )

                        if (!isInPipMode.value) {
                            Spacer(Modifier.height(30.dp))
                            Buttons(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                isRecording = recordingState,
                                onclickRecord = {
                                    if (recordingState) {
                                        stoprecording()
                                        recordingState = !recordingState
                                        Log.d("tag1", "stop rec")
                                    } else {
                                        if(url.isNotEmpty()){
                                            startrecording(url)
                                            recordingState = !recordingState
                                            Log.d("tag1", "start rec")
                                        }else
                                            Toast.makeText(context, "Please Enter a URL", Toast.LENGTH_SHORT).show()

                                    }


                                },
                                onPIPclicked = { enterPipMode() })
                        }
                    }
                }
            }
        }
    }

    private fun stoprecording() {
        mediaPlayer.stop()
        Toast.makeText(this, "stop and saved Recording", Toast.LENGTH_SHORT).show()

    }

    private fun startrecording(url: String) {
        // Ensure the directory exists
        val  dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)

        val file = File(
            dir  //  for the public directory
            , "recording_${System.currentTimeMillis()}.mp4" // give the name of the file
        )
        recordingPath = file.absolutePath // Get the absolute path of the file


        // Set up recording with the RTSP URL
        val media = Media(libVLC, Uri.parse(url)) // Use the RTSP URL for recording
        // Play + Record
        media.addOption(":sout=#file{dst=$recordingPath}")
        media.addOption(":sout-keep")

        mediaPlayer.media = media
        mediaPlayer.play()
        Toast.makeText(this, "Recording the stream", Toast.LENGTH_SHORT).show()
    }


    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        isInPipMode.value = isInPictureInPictureMode
    }

    override fun onPause() {
        super.onPause()
        if (isInPipMode.value == false) {
            player.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
        if (mediaPlayer.isPlaying) mediaPlayer.stop()
        mediaPlayer.release()
        libVLC.release()
    }

    private fun enterPipMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val aspectRatio = Rational(16, 9)
            val pipParams = PictureInPictureParams.Builder().setAspectRatio(aspectRatio).build()
            enterPictureInPictureMode(pipParams)
        }
    }


}

@Composable
fun searchBar(
    modifier: Modifier, url: String, onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    context: Context,
) {


    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = url,
            onValueChange = onValueChange,
            label = { Text("URL") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),

            trailingIcon = {
                IconButton(
                    onClick = {
                        if (url.isNotEmpty()) {
                            onSearch()
                        } else {
                            Toast.makeText(context, "Please Enter a URL", Toast.LENGTH_SHORT).show()
                        }

                    }, modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(50.dp)
                ) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            },
            singleLine = true
        )


    }
}


@Composable
fun Buttons(
    modifier: Modifier,
    isRecording: Boolean,
    onclickRecord: (Boolean) -> Unit,
    onPIPclicked: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Button(
            onClick = {
                onclickRecord(!isRecording)
            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .size(150.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Unspecified)
        ) {
            Text(
                text = if (!isRecording) "Record"
                else "Stop", fontSize = 20.sp, fontWeight = FontWeight.SemiBold
            )
        }
        Button(
            onClick = {
                onPIPclicked()
            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .size(150.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Unspecified)
        ) {
            Text(
                text = "PIP", fontSize = 20.sp, fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun videoView(
    modifier: Modifier,
    url: String,
    player: ExoPlayer,
    context: Context,
) {

    AndroidView(
        factory = { context ->
            PlayerView(context).also {
                it.player = player

            }
        }, modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)

    )
}


