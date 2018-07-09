package jp.ac.titech.itpro.sdl.cooknote

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper
import jp.ac.titech.itpro.sdl.cooknote.model.Recipe
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val searchIntent = intent
        val query = searchIntent.getStringExtra("query")

        val db = RecipeOpenHelper(this).writableDatabase
        val cursor = db.query(RecipeOpenHelper.RECIPE_TABLE, arrayOf("title", "uri"),
                "content_text like ?", arrayOf("%${query}%"), null, null, null, null)
        val recipeList = mutableListOf<Recipe>()
        if (cursor.moveToFirst()) {
            do {
                recipeList.add(Recipe(uri = cursor.getString(cursor.getColumnIndex("uri")),
                        title = cursor.getString(cursor.getColumnIndex("title"))))
            } while (cursor.moveToNext())

            val recyclerView = search_recipes_recycler
            val llManager = LinearLayoutManager(applicationContext)
            llManager.orientation = LinearLayoutManager.VERTICAL
            recyclerView.layoutManager = llManager
            val adapter = RecipesAdapter(recipeList)
            recyclerView.adapter = adapter
        }

        search_search_view.setIconifiedByDefault(false)
        search_search_view.setSubmitButtonEnabled(true)

        search_search_view.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("search_query_change", newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("search_query_submit", query)
                if (!TextUtils.isEmpty(query)) {
                    val intent = Intent(applicationContext, SearchResultActivity::class.java)
                    intent.putExtra("query", query)
                    startActivity(intent)
                }
                return true
            }

        })
    }
}
