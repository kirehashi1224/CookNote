package jp.ac.titech.itpro.sdl.cooknote

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper.Companion.FOLDER_TABLE


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
        val folderName = folderList[position]
        holder.base.setOnClickListener {
            // Toast.makeText(it.context, "${folderName}", Toast.LENGTH_SHORT).show()

            val intent = Intent(it.context, RecipeDetailActivity::class.java)
            // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("folder", folderName)
            it.context.startActivity(intent)
        }
        holder.base.setOnLongClickListener {
            val items = arrayOf("削除")
            AlertDialog.Builder(it.context)
                    .setTitle("設定")
                    .setItems(items, DialogInterface.OnClickListener { dialog, which ->
                        when(which){
                            0 ->{
                                val db = RecipeOpenHelper(it.context).writableDatabase
                                db.delete(FOLDER_TABLE, "folder_name = $folderName", null)
                                this.notifyDataSetChanged()
                                val intent = Intent(it.context, MainActivity::class.java)
                                it.context.startActivity(intent)
                            }
                        }
                    }).show()
            return@setOnLongClickListener true
        }



        /*{
            val items = arrayOf("削除")
            AlertDialog.Builder(it.context)
                    .setTitle("設定")
                    .setItems(items, DialogInterface.OnClickListener { dialog, which ->
                        fun onclick(dialog: DialogInterface, which: Int){

                        }
                    }).show()
            return true
        }*/
    }

    override fun getItemCount(): Int {
        return folderList.size
    }
}