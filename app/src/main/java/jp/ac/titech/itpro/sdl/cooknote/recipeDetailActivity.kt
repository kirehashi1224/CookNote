package jp.ac.titech.itpro.sdl.cooknote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper
import jp.ac.titech.itpro.sdl.cooknote.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_detail.*

class recipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipeDetailIntent = intent
        val folderName = recipeDetailIntent.getStringExtra("folder")

        val db = RecipeOpenHelper(this).writableDatabase
        val cursor = db.query(RecipeOpenHelper.RECIPE_TABLE, arrayOf("title", "uri"), "folder == ?", arrayOf(folderName), null, null, null, null)
        val recipeList = mutableListOf<Recipe>()
        if(cursor.moveToFirst()){
            do {
                recipeList.add(Recipe(uri = cursor.getString(cursor.getColumnIndex("uri")),
                        title = cursor.getString(cursor.getColumnIndex("title"))))
            }while (cursor.moveToNext())

            val recyclerView = recipe_item_recycler
            val llManager = LinearLayoutManager(applicationContext)
            llManager.orientation = LinearLayoutManager.VERTICAL
            recyclerView.layoutManager = llManager
            val adapter = RecipesAdapter(recipeList)
            recyclerView.adapter = adapter
        }
    }
}
