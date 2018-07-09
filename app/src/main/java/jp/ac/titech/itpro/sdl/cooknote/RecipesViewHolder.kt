package jp.ac.titech.itpro.sdl.cooknote

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class RecipesViewHolder(v: View): RecyclerView.ViewHolder(v){
    val base = v
    val RecipeItemTitle: TextView = v.findViewById(R.id.recipe_item_title)
}