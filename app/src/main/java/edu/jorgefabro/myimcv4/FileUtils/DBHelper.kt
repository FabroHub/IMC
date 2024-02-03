package edu.jorgefabro.myimcv4.FileUtils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


interface IDBHelper {
    fun addDatos(
        dia: Int,
        mes: Int,
        anyo: Int,
        index: String,
        genre: String,
        peso: String,
        altura: String,
        imc: String
    )

    fun delDato(id: Int): Int
    fun getDatosTodo(): List<DataFiles>
    //fun getDatoUnico(id: Int): DataFiles?
    fun updateDatos(
        id: Int,
        dia: Int,
        mes: Int,
        anyo: Int,
        altura: String,
        peso: String,
        imc: String,
        genre: String,
        index: String
    ): Int
}

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABSE_NAME, factory, DATABASE_VERSION), IDBHelper {

    companion object {
        val DATABASE_VERSION = 1
        val DATABSE_NAME = "persona.db"
        val TABLA_DATOS = "datos"
        val COLUMNA_ID = "id"
        val COLUMNA_ALTURA = "altura"
        val COLUMNA_PESO = "peso"
        val COLUMNA_DIA = "día"
        val COLUMNA_MES = "mes"
        val COLUMNA_ANYO = "año"
        val COLUMNA_INDEX = "índice"
        val COLUMNA_GENRE = "género"
        val COLUMNA_IMC = "imc"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val createTableAmigos = "create table $TABLA_DATOS" +
                    "($COLUMNA_ID integer primary key autoincrement," +
                    "$COLUMNA_ALTURA text," +
                    "$COLUMNA_PESO text," +
                    "$COLUMNA_IMC text," +
                    "$COLUMNA_GENRE text," +
                    "$COLUMNA_INDEX text," +
                    "$COLUMNA_DIA integer, " +
                    "$COLUMNA_MES integer," +
                    "$COLUMNA_ANYO integer)"
            db!!.execSQL(createTableAmigos)
        } catch (_: java.lang.Exception) {
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
    }

    override fun addDatos(
        dia: Int,
        mes: Int,
        anyo: Int,
        index: String,
        genre: String,
        peso: String,
        altura: String,
        imc: String
    ) {
        val data = ContentValues()
        data.put(COLUMNA_ALTURA, altura)
        data.put(COLUMNA_PESO, peso)
        data.put(COLUMNA_DIA, dia)
        data.put(COLUMNA_MES, mes)
        data.put(COLUMNA_ANYO, anyo)
        data.put(COLUMNA_IMC, imc)
        data.put(COLUMNA_GENRE, genre)
        data.put(COLUMNA_INDEX, index)


        val db = this.writableDatabase
        //  Si no funciona bien a la hora de insertar porbar poniendo el "val id = " delante.
        /*val id = */db.insert(TABLA_DATOS, null, data)
        db.close()
    }

    override fun delDato(id: Int): Int {
        val args = arrayOf(id.toString())
        val db = this.writableDatabase
        val result = db.delete(TABLA_DATOS, "$COLUMNA_ID=?", args)
        db.close()
        return result
    }

    //@SuppressLint("Recycle")
    override fun getDatosTodo(): List<DataFiles> {
        val result = ArrayList<DataFiles>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "select * from $TABLA_DATOS",
            null
        )

        if (cursor.moveToFirst()) {
            do {

                val dia = cursor.getInt(6)
                val mes = cursor.getInt(7)
                val anyo = cursor.getInt(8)
                val index = cursor.getString(4)
                val genre = cursor.getString(5)
                val peso = cursor.getString(2)
                val altura = cursor.getString(1)
                val imc = cursor.getString(3)
                val id = cursor.getInt(0)
                val amigo = DataFiles(dia, mes, anyo, index, genre, peso, altura, imc,id)
                result.add(amigo)
            } while (cursor.moveToNext())
        }

        return result
    }

   //@SuppressLint("Recycle")
    /*override fun getDatoUnico(id: Int): DataFiles? {
        var amigo: DataFiles? = null
        val args = arrayOf(id.toString())
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "select $COLUMNA_PESO, $COLUMNA_ALTURA, $COLUMNA_IMC, $COLUMNA_GENRE, $COLUMNA_INDEX, $COLUMNA_DIA, $COLUMNA_MES, $COLUMNA_ANYO from $TABLA_DATOS where $COLUMNA_ID = ?",
            args
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val dia = cursor.getInt(1)
                val mes = cursor.getInt(2)
                val anyo = cursor.getInt(3)
                val index = cursor.getString(8)
                val genre = cursor.getString(7)
                val peso = cursor.getString(4)
                val altura = cursor.getString(5)
                val imc = cursor.getString(6)
                amigo = DataFiles(dia, mes, anyo, index, genre, peso, altura, imc)
            } while (cursor.moveToNext())
        }

        return amigo
    }*/

    override fun updateDatos(
        id: Int,
        dia: Int,
        mes: Int,
        anyo: Int,
        altura: String,
        peso: String,
        imc: String,
        genre: String,
        index: String
    ): Int {
        val args = arrayOf(id.toString())
        val db = this.writableDatabase
        val data = ContentValues()
        data.put(COLUMNA_PESO, peso)
        data.put(COLUMNA_ALTURA, altura)
        data.put(COLUMNA_IMC, imc)
        data.put(COLUMNA_GENRE, genre)
        data.put(COLUMNA_INDEX, index)
        data.put(COLUMNA_DIA, dia)
        data.put(COLUMNA_MES, mes)
        data.put(COLUMNA_ANYO, anyo)
        val result = db.update(TABLA_DATOS, data, "$COLUMNA_ID = ?", args)
        db.close()
        return result
    }

}