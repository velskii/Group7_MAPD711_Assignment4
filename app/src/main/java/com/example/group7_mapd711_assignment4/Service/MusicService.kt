/*
  MAPD 711 - Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */
package com.example.group7_mapd711_assignment4.Service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.group7_mapd711_assignment4.Model.MusicState
import com.example.group7_mapd711_assignment4.R
import java.util.*

class MusicService : Service() {

  private var musicState = MusicState.STOP
  private var musicMediaPlayer: MediaPlayer? = null

  private val songs: List<Int> = listOf(
    R.raw.anniversary_beethoven,
    R.raw.forelise_beethoven,
    R.raw.lovestory_mozart
  )
  private var randomSongs = mutableListOf<Int>()
  private val binder by lazy { MusicBinder() }

  override fun onBind(intent: Intent?): IBinder = binder

  fun runAction(state: MusicState) {
    musicState = state
    when (state) {
      MusicState.PLAY -> startMusic()
      MusicState.STOP -> stopMusic()
      MusicState.SHUFFLE_SONGS -> shuffleSongs()
    }
  }

  fun getNameOfSong(): String =
    resources.getResourceEntryName(randomSongs.first())
        .replaceFirstChar {
             if (it.isLowerCase()) it.titlecase(Locale.ENGLISH)
             else it.toString()
        }.replace("_", " ")

  private fun initializeMediaPlayer() {
    if (randomSongs.isEmpty()) {
      randomizeSongs()
    }
    musicMediaPlayer = MediaPlayer.create(this, randomSongs.first()).apply {
      isLooping = true
    }
  }

  private fun startMusic() {
    initializeMediaPlayer()
    musicMediaPlayer?.start()
  }

  private fun stopMusic() {
    musicMediaPlayer?.run {
      stop()
      release()
    }
  }

  private fun shuffleSongs() {
    musicMediaPlayer?.run {
      stop()
      release()
    }
    randomizeSongs()
    startMusic()
  }

  private fun randomizeSongs() {
    randomSongs.clear()
    randomSongs.addAll(songs.shuffled())
  }

  inner class MusicBinder : Binder() {
    fun getService(): MusicService = this@MusicService
  }
}