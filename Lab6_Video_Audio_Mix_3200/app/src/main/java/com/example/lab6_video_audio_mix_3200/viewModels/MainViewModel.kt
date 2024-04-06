// MainViewModel.kt
package com.example.lab6_video_audio_mix_3200.viewModels

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    val videoPlayer: ExoPlayer,
    val audioPlayer: ExoPlayer
) : ViewModel() {

    private val _isVideoPlaying = MutableStateFlow(false)
    val isVideoPlaying: StateFlow<Boolean> = _isVideoPlaying

    private val _isAudioPlaying = MutableStateFlow(false)
    val isAudioPlaying: StateFlow<Boolean> = _isAudioPlaying

    val videoUris = mutableListOf<Pair<Uri, String>>()
    val audioUris = mutableListOf<Pair<Uri, String>>()

    init {
        videoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isVideoPlaying.value = isPlaying
            }
        })

        audioPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isAudioPlaying.value = isPlaying
            }
        })
    }

    fun addVideoUri(uri: Uri, name: String) {
        videoUris.add(Pair(uri, name))
    }

    fun addAudioUri(uri: Uri, name: String) {
        audioUris.add(Pair(uri, name))
    }

    fun playVideo(uri: Uri) {
        val mediaItem = MediaItem.fromUri(uri)
        videoPlayer.setMediaItem(mediaItem)
        videoPlayer.prepare()
        videoPlayer.playWhenReady = true
    }

    fun playAudio(uri: Uri) {
        val mediaItem = MediaItem.fromUri(uri)
        audioPlayer.setMediaItem(mediaItem)
        audioPlayer.prepare()
        audioPlayer.playWhenReady = true
    }

    fun pauseVideo() {
        if (videoPlayer.isPlaying) {
            videoPlayer.pause()
        }
    }

    fun pauseAudio() {
        if (audioPlayer.isPlaying) {
            audioPlayer.pause()
        }
    }

    override fun onCleared() {
        super.onCleared()
        videoPlayer.release()
        audioPlayer.release()
    }

    val videoUrisValue: List<Pair<Uri, String>>
        get() = videoUris

    val audioUrisValue: List<Pair<Uri, String>>
        get() = audioUris
}