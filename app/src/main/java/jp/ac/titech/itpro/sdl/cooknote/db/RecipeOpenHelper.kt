package jp.ac.titech.itpro.sdl.cooknote.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class RecipeOpenHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        val DB_NAME = "recipe_db"
        val DB_VERSION = 1
        val RECIPE_TABLE = "recipes"
        val FOLDER_TABLE = "folder"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $RECIPE_TABLE ("+
                    "_id integer primary key autoincrement,"+
                    "title text not null,"+
                    "uri text not null,"+
                    "content_text text not null,"+
                    "folder text not null"+
                    ");")
        db?.execSQL("create table $FOLDER_TABLE ("+
                    "_id integer primary key autoincrement,"+
                    "folder_name text not null"+
                    ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $DB_NAME")
        onCreate(db)
    }
}