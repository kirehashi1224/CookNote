package jp.ac.titech.itpro.sdl.cooknote

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Intent.ACTION_SEND == intent.action){
            val uri = intent.extras.getCharSequence(Intent.EXTRA_TEXT).toString()
            val intent = Intent(this, IntentActivity::class.java)
            intent.putExtra("uri", uri)
            startActivity(intent)
        }
    }
}
