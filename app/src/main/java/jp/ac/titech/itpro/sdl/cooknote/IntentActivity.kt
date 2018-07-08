package jp.ac.titech.itpro.sdl.cooknote

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.observers.DisposableSingleObserver
import jp.ac.titech.itpro.sdl.cooknote.model.Recipe
import kotlinx.android.synthetic.main.activity_intent.*

class IntentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        val shareIntent = intent
        val uri = shareIntent.getStringExtra("uri")
        //val webHtml = Jsoup.connect(uri).get()
        //val title = webHtml.select(".entry-title").first().text()
        Recipe.getRecipe(uri).subscribe(object : DisposableSingleObserver<Recipe>() {
            override fun onSuccess(recipe: Recipe) {

                recipe_text.text = recipe.recipeText
                // ingredients.text = recipe.ingredients.toString("\n")
                // process.text = recipe.process.toString("\n")
            }

            override fun onError(e: Throwable) {
                Log.e("error", e.toString())
            }
        })


    }
}
