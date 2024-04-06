// AudioViewModel.kt
package com.example.lab6_video_audio_mix_3200.viewModels

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player

class AudioViewModel(val savedStateHandle: SavedStateHandle,
                     val player: Player
) : ViewModel() {
    private val audioUris = savedStateHandle.getStateFlow("audioUris", emptyList<Pair<Uri, String>>())

    init {
        player.prepare()
    }

    fun addAudioUri(uri: Uri, name: String) {
        savedStateHandle["audioUris"] = audioUris.value + Pair(uri, name)
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playAudio(uri: Uri) {
        player.setMediaItem(
            MediaItem.fromUri(uri)
        )
        player.play()
    }

    fun pauseAudio() {
        player.pause()
    }

    fun muteAudio() {
        player.volume = 0f
    }

    fun unmuteAudio() {
        player.volume = 1f
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}