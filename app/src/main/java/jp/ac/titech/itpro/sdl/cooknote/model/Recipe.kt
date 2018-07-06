package jp.ac.titech.itpro.sdl.cooknote.model

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

data class Recipe (val name: String){
    companion object {
        fun getName(uri: String): Single<Recipe>{
            return Single.create<Document>{
                val document = Jsoup.connect(uri).get()
                it.onSuccess(document)
            }.flatMap {
                val title = it.select(".entry-title").first().text()
                Single.just(Recipe(title))
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}