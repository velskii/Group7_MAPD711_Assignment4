package com.example.group7_mapd711_assignment4.Fragments

import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.example.group7_mapd711_assignment4.Model.MusicState
import com.example.group7_mapd711_assignment4.R
import com.example.group7_mapd711_assignment4.Service.MusicService
import com.example.group7_mapd711_assignment4.databinding.FragmentMusicBinding
import com.example.group7_mapd711_assignment4.view_model.MainViewModel
import android.content.SharedPreferences

class MusicFragment : Fragment() {
//    private lateinit var mContext: Context
    lateinit var sharedPreferences: SharedPreferences
    private val binding: FragmentMusicBinding by lazy {
        FragmentMusicBinding.inflate(layoutInflater)
    }
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    // Bound Service
    private var musicService: MusicService? = null

    private val boundServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder: MusicService.MusicBinder = service as MusicService.MusicBinder
            musicService = binder.getService()
            mainViewModel.isMusicServiceBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            musicService?.runAction(MusicState.STOP)
            musicService = null
            mainViewModel.isMusicServiceBound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setContentView(binding.root)
        sharedPreferences = requireActivity().getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)
        setUpUi()
        val musicState = sharedPreferences.getString("MusicState", "")

        if(musicState == "Play") {
            enableButtons(MusicState.PLAY)
        } else if(musicState == "Stop") {
            enableButtons(MusicState.STOP)
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onStart() {
        super.onStart()
        // bind to service if it isn't bound
        if (!mainViewModel.isMusicServiceBound) bindToMusicService()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindMusicService()
    }

    private fun unbindMusicService() {
        if (mainViewModel.isMusicServiceBound) {
            // stop the audio
            musicService?.runAction(MusicState.STOP)
            // disconnect the service and save state
            activity?.unbindService(boundServiceConnection)
            mainViewModel.isMusicServiceBound = false
        }
    }
    // Bound Service Methods
    private fun bindToMusicService() {
        activity?.bindService(Intent(requireContext(), MusicService::class.java), boundServiceConnection, Context.BIND_AUTO_CREATE)
    }

    fun Button.onClick(action: () -> Unit) {
        this.setOnClickListener {
            action()
        }
    }

    private fun setUpUi() {
        with(binding) {
            btnPlayMusic.onClick {
                sendCommandToBoundService(MusicState.PLAY)
                sharedPreferences.edit().putString("MusicState", "Play").apply()
            }
            btnStopMusic.onClick {
                sendCommandToBoundService(MusicState.STOP)
                sharedPreferences.edit().putString("MusicState", "Stop").apply()
            }
            btnShuffleMusic.onClick {
                sendCommandToBoundService(MusicState.SHUFFLE_SONGS)
            }
            btnSongName.onClick {
                getNameOfSong()
            }
        }
    }
    private fun sendCommandToBoundService(state: MusicState) {
        if (mainViewModel.isMusicServiceBound) {
            musicService?.runAction(state)
            informUser(state)
            enableButtons(state)
        } else {
            Toast.makeText(requireContext(), R.string.service_is_not_bound, Toast.LENGTH_SHORT).show()
        }
    }

    private fun informUser(state: MusicState) {
        @StringRes val res = when (state) {
            MusicState.PLAY -> R.string.music_started
            MusicState.STOP -> R.string.music_stopped
            MusicState.SHUFFLE_SONGS -> R.string.songs_shuffled
        }

        Toast.makeText(requireContext(), res, Toast.LENGTH_SHORT).show()
    }

    private fun enableButtons(state: MusicState) {
        val songPlays = state == MusicState.PLAY || state == MusicState.SHUFFLE_SONGS
        with(binding) {
            btnPlayMusic.isEnabled = !songPlays
            btnStopMusic.isEnabled = songPlays
            btnShuffleMusic.isEnabled = songPlays
            btnSongName.apply {
                isEnabled = songPlays
                btnSongName.visibility = if (songPlays) {
                    View.VISIBLE
                } else {
                    View.INVISIBLE
                }
            }
        }
    }

    /**
     * Get name of song
     */
    private fun getNameOfSong() {
        val message = if (mainViewModel.isMusicServiceBound) {
            musicService?.getNameOfSong() ?: getString(R.string.unknown)
        } else {
            getString(R.string.service_is_not_bound)
        }

        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}