/*
  MAPD 711 - Samsung Android App Development
  Group 7
  Quoc Phong Ngo - 301148406
  Feiliang Zhou  - 301216989
 */
package com.example.group7_mapd711_assignment4

import android.annotation.SuppressLint
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.group7_mapd711_assignment4.Booking.BookingViewModel
import com.example.group7_mapd711_assignment4.Model.MusicState
import com.example.group7_mapd711_assignment4.Service.MusicService
import com.example.group7_mapd711_assignment4.databinding.ActivityHomeBinding
import com.example.group7_mapd711_assignment4.view_model.MainViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
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
    lateinit var bookingViewModel: BookingViewModel
    lateinit var context: Context
    lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val linkPersonalInfo: Button = findViewById<View>(R.id.btnPersonalInfo) as Button
        linkPersonalInfo.setOnClickListener{
            val i = Intent(this@HomeActivity, UserInformationActivity::class.java)
            startActivity(i)
        }

        val linkBooking: Button = findViewById<View>(R.id.btnBooking) as Button
        linkBooking.setOnClickListener{
            val i = Intent(this@HomeActivity, CruiseTypesActivity::class.java)
            startActivity(i);
        }
        context = this@HomeActivity
        val bookingInformation: Button = findViewById<View>(R.id.btnBookingInfo) as Button
        bookingInformation.setOnClickListener{
//            val i = Intent(this@HomeActivity, BookingInformationActivity::class.java)
            val i = Intent(this@HomeActivity, BookingListActivity::class.java)
            startActivity(i);
        }

        sharedPreferences = this.getSharedPreferences("com.example.Group7_MAPD711_Assignment4", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "no name")

        findViewById<TextView>(R.id.welcome).text = "Welcome $username! Please enjoy your trip."

        setUpUi()
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
            unbindService(boundServiceConnection)
            mainViewModel.isMusicServiceBound = false
        }
    }
    // Bound Service Methods
    private fun bindToMusicService() {
        bindService(Intent(this, MusicService::class.java), boundServiceConnection, Context.BIND_AUTO_CREATE)
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
            }
            btnStopMusic.onClick {
                sendCommandToBoundService(MusicState.STOP)
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
            Toast.makeText(this, R.string.service_is_not_bound, Toast.LENGTH_SHORT).show()
        }
    }

    private fun informUser(state: MusicState) {
        @StringRes val res = when (state) {
            MusicState.PLAY -> R.string.music_started
            MusicState.STOP -> R.string.music_stopped
            MusicState.SHUFFLE_SONGS -> R.string.songs_shuffled
        }

        Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
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

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}