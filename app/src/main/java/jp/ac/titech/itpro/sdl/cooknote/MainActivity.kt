package jp.ac.titech.itpro.sdl.cooknote

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import jp.ac.titech.itpro.sdl.cooknote.db.RecipeOpenHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Intent.ACTION_SEND == intent.action){
            val uri = intent.extras.getCharSequence(Intent.EXTRA_TEXT).toString()
            val intent = Intent(this, IntentActivity::class.java)
            intent.putExtra("uri", uri)
            startActivity(intent)
        }

        val db = RecipeOpenHelper(this).writableDatabase
        val cursor = db.query(RecipeOpenHelper.FOLDER_TABLE, arrayOf("folder_name"), null, null, null, null, null, null)
        val folderList = mutableListOf<String>()
        if(cursor.moveToFirst()){
            do {
                folderList.add(cursor.getString(cursor.getColumnIndex("folder_name")))
            }while (cursor.moveToNext())
        }
        Log.d("folder", folderList.toString())

        val recyclerView = show_folder_recycler
        val llManager = LinearLayoutManager(applicationContext)
        llManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = llManager
        val adapter = ShowFolderAdapter(folderList)
        recyclerView.adapter = adapter

        main_search_view.setIconifiedByDefault(false)
        main_search_view.setSubmitButtonEnabled(true)

        main_search_view.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("main_query_change", newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("main_query_submit", query)
                if(!TextUtils.isEmpty(query)){
                    val intent = Intent(applicationContext, SearchResultActivity::class.java)
                    intent.putExtra("query", query)
                    startActivity(intent)
                }
                return true
            }

        })
    }


}
