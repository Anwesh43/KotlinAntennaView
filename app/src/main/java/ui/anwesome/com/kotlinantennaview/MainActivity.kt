package ui.anwesome.com.kotlinantennaview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.antennaview.AntennaView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AntennaView.create(this)
    }
}
