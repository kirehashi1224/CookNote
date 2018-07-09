package jp.ac.titech.itpro.sdl.cooknote

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper
import jp.ac.titech.itpro.sdl.cooknote.model.Recipe

class RecipesAdapter(recipes: List<Recipe>): RecyclerView.Adapter<RecipesViewHolder>(){
    private val recipeList = recipes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipes_layout, parent, false)
        return RecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.RecipeItemTitle.text = recipeList[position].title
        val id = recipeList[position].id
        holder.base.setOnClickListener {
            // val recipeTitle = recipeList[position].title
            val uri = recipeList[position].uri
            // Toast.makeText(it.context, "${recipeTitle}", Toast.LENGTH_SHORT).show()
            val intent = Intent(it.context, WebViewActivity::class.java)
            intent.putExtra("uri", uri)
            it.context.startActivity(intent)
        }
        holder.base.setOnLongClickListener {
            val items = arrayOf("削除")
            AlertDialog.Builder(it.context)
                    .setTitle("設定")
                    .setItems(items, DialogInterface.OnClickListener { dialog, which ->
                        when(which){
                            0 ->{
                                Log.d("aaaaaaaaaa", position.toString())
                                val db = RecipeOpenHelper(it.context).writableDatabase
                                db.delete(RecipeOpenHelper.RECIPE_TABLE, "_id = $id", null)
                                this.notifyDataSetChanged()
                                val intent = Intent(it.context, MainActivity::class.java)
                                it.context.startActivity(intent)
                            }
                        }
                    }).show()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
}