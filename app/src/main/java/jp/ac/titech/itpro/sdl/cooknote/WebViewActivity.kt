package jp.ac.titech.itpro.sdl.cooknote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val webViewIntent = intent
        val uri = webViewIntent.getStringExtra("uri")
        recipe_web_view.webViewClient = WebViewClient()
        recipe_web_view.loadUrl(uri)
    }
}
