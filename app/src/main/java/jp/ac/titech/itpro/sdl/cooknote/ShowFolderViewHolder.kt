package jp.ac.titech.itpro.sdl.cooknote

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ShowFolderViewHolder(v: View): RecyclerView.ViewHolder(v){
    val base = v
    val folderName: TextView = v.findViewById(R.id.folder_name)
}