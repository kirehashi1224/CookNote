package jp.ac.titech.itpro.sdl.cooknote

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper.Companion.RECIPE_TABLE
import jp.ac.titech.itpro.sdl.cooknote.model.Recipe

class FolderAdapter(folders: List<String>, recipe: Recipe): RecyclerView.Adapter<FolderViewHolder>(){
    private val folderList = folders
    private val recipe = recipe
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.folder_layout, parent, false)
        return FolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.folderName.text = folderList[position]
        val folderName = folderList[position]
        holder.base.setOnClickListener {
            val db = RecipeOpenHelper(holder.base.context).writableDatabase
            val contentValues = ContentValues().also {
                it.put("title", recipe.title)
                it.put("uri", recipe.uri)
                it.put("content_text", recipe.recipeText)
                it.put("folder", folderName)
            }
            db.insert(RECIPE_TABLE, null, contentValues)
            Toast.makeText(it.context, "${folderName}に登録しました", Toast.LENGTH_SHORT).show()
        }
        holder.base.setOnLongClickListener {
            val items = arrayOf("削除")
            AlertDialog.Builder(it.context)
                    .setTitle("設定")
                    .setItems(items, DialogInterface.OnClickListener { dialog, which ->
                        when(which){
                            0 ->{
                                val db = RecipeOpenHelper(it.context).writableDatabase
                                db.delete(RecipeOpenHelper.FOLDER_TABLE, "folder_name = $folderName", null)
                                this.notifyDataSetChanged()
                            }
                        }
                    }).show()
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}