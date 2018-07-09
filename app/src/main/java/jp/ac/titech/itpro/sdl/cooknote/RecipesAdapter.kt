package jp.ac.titech.itpro.sdl.cooknote

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import jp.ac.titech.itpro.sdl.cooknote.model.Recipe

class RecipesAdapter(recipes: List<Recipe>): RecyclerView.Adapter<RecipesViewHolder>(){
    private val recipeList = recipes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipes_layout, parent, false)
        return RecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.RecipeItemTitle.text = recipeList[position].title
        holder.base.setOnClickListener {
            // val recipeTitle = recipeList[position].title
            val uri = recipeList[position].uri
            // Toast.makeText(it.context, "${recipeTitle}", Toast.LENGTH_SHORT).show()
            val intent = Intent(it.context, WebViewActivity::class.java)
            intent.putExtra("uri", uri)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
}