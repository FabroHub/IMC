package edu.jorgefabro.myimcv4

import android.app.Application
import edu.jorgefabro.myimcv4.FileUtils.DBHelper
import edu.jorgefabro.myimcv4.FileUtils.IDBHelper

class DbHelpertAplication: Application() {

    companion object{
        lateinit var dataSource : IDBHelper
    }

    override fun onCreate() {
        super.onCreate()
         dataSource = DBHelper(applicationContext, null)
    }
}