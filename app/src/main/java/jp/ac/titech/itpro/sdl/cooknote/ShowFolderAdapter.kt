package jp.ac.titech.itpro.sdl.cooknote

import android.content.ContentValues
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper
import jp.ac.titech.itpro.sdl.cooknote.model.Recipe

class ShowFolderAdapter(folders: List<String>): RecyclerView.Adapter<ShowFolderViewHolder>(){
    private val folderList = folders
    init {
        Log.d("FolderAdapter", folderList.toString())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowFolderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.folder_layout, parent, false)
        return ShowFolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowFolderViewHolder, position: Int) {
        holder.folderName.text = folderList[position]
        holder.base.setOnClickListener {
            val db = RecipeOpenHelper(holder.base.context).writableDatabase
            val folderName = folderList[position]
            Toast.makeText(it.context, "${folderName}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}