package jp.ac.titech.itpro.sdl.cooknote

import android.content.ContentValues
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import io.reactivex.observers.DisposableSingleObserver
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper.Companion.RECIPE_TABLE
import jp.ac.titech.itpro.sdl.cooknote.model.Recipe
import kotlinx.android.synthetic.main.activity_intent.*
import android.content.DialogInterface
import android.widget.Toast
import android.widget.EditText
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper.Companion.FOLDER_TABLE


class IntentActivity : AppCompatActivity() {
    lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        val shareIntent = intent
        val uri = shareIntent.getStringExtra("uri")
        //val webHtml = Jsoup.connect(uri).get()
        //val title = webHtml.select(".entry-title").first().text()
        Recipe.getRecipe(uri).subscribe(object : DisposableSingleObserver<Recipe>() {
            override fun onSuccess(getRecipe: Recipe) {
                recipe = getRecipe.copy()

                recipe_uri.text = getRecipe.uri
                recipe_title.text = getRecipe.title
                // recipe_text.text = recipe.recipeText
                // ingredients.text = recipe.ingredients.toString("\n")
                // process.text = recipe.process.toString("\n")
                val db = RecipeOpenHelper(applicationContext).writableDatabase
                val cursor = db.query(FOLDER_TABLE, arrayOf("folder_name"), null, null, null, null, null, null)
                val folderList = mutableListOf<String>()
                if(cursor.moveToFirst()){
                    do {
                        folderList.add(cursor.getString(cursor.getColumnIndex("folder_name")))
                    }while (cursor.moveToNext())
                }

                val recyclerView = folder_recycler
                val llManager = LinearLayoutManager(applicationContext)
                llManager.orientation = LinearLayoutManager.VERTICAL
                recyclerView.layoutManager = llManager
                val adapter = FolderAdapter(folderList, recipe)
                recyclerView.adapter = adapter

                add_folder.setOnClickListener {
                    val editView = EditText(this@IntentActivity)
                    AlertDialog.Builder(this@IntentActivity)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setTitle("フォルダ名を入力してください")
                            .setView(editView)
                            .setPositiveButton("OK") { dialog, whichButton ->
                                val newFolder = ContentValues().also {
                                    it.put("folder_name", editView.text.toString())
                                }
                                db.insert(FOLDER_TABLE, null, newFolder)
                                folderList.add(editView.text.toString())
                                adapter.notifyItemInserted(folderList.size-1)
                            }
                            .setNegativeButton("キャンセル") { dialog, whichButton -> }
                            .show()
                }
            }

            override fun onError(e: Throwable) {
                Log.e("error", e.toString())
            }
        })

        /*save_button.setOnClickListener {
            val db = RecipeOpenHelper(this).writableDatabase
            val contentValues = ContentValues().also {
                it.put("title", recipe.title)
                it.put("uri", recipe.uri)
                it.put("content_text", recipe.recipeText)
            }
            db.insert(RECIPE_TABLE, null, contentValues)
        }*/
    }
}
