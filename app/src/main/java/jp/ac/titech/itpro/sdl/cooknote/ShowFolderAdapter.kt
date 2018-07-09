package jp.ac.titech.itpro.sdl.cooknote

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

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
            val folderName = folderList[position]
            // Toast.makeText(it.context, "${folderName}", Toast.LENGTH_SHORT).show()

            val intent = Intent(it.context, RecipeDetailActivity::class.java)
            // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("folder", folderName)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}