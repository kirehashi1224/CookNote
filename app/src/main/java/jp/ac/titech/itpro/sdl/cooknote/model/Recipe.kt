package jp.ac.titech.itpro.sdl.cooknote.model

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

data class Recipe (val html: String = "",
                   val title: String = "",
                   val ingredients: Ingredients = Ingredients(),
                   val process: Process = Process()){
    companion object {
        fun getName(uri: String): Single<Recipe>{
            return Single.create<Document>{
                val document = Jsoup.connect(uri).get()
                it.onSuccess(document)
            }.flatMap {
                /*
                    概要：class entry-content > p
                    材料：class ingredients > ul > ui
                    作り方：class ingredients ... ol > li class process
                    ポイント：class entry-content > h3 class point下のp
                 */
                val html = it.getElementsByClass("entry-content").first()
                val title = it.select(".entry-title").first().text()
                val ingredients = it.select(".entry-content ul li").eachText()
                val process = it.select(".entry-content ol li").eachText()

                Single.just(Recipe(html = html.html(), title = title, ingredients = Ingredients(ingredients),
                        process = Process(process)))
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}