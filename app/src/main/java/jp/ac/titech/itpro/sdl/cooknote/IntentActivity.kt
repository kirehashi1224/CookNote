package jp.ac.titech.itpro.sdl.cooknote

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_intent.*

class IntentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        val shareIntent = intent
        intentTest.text = shareIntent.getStringExtra("uri")
    }
}
