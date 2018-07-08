package jp.ac.titech.itpro.sdl.cooknote.model

import android.os.Build
import android.os.Debug
import android.text.Html
import android.text.Html.fromHtml
import android.text.Spanned
import android.util.Log
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.ac.titech.itpro.sdl.cooknote.library.ExtractContent
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

data class Recipe (val uri: String, val title: String, val recipeText: String = ""){
    companion object {
        fun getRecipe(uri: String): Single<Recipe>{
            return Single.create<WebDocument>{
                val document = Jsoup.connect(uri).get()
                it.onSuccess(WebDocument(uri, document))
            }.flatMap {
                val document = it.document
                val imgs = document.getElementsByTag("img")
                for (img in imgs){
                    img.remove()
                }
                val html = ExtractContent.analyse(document.body().html())

                var recipeText = fromHtml(html).toString()
                Log.d("cooknote", recipeText)

                Single.just(Recipe(uri = it.uri, title = document.title(), recipeText = recipeText))
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }

        fun fromHtml(html: String): Spanned{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
            } else {
                return Html.fromHtml(html);
            }
        }
    }
}