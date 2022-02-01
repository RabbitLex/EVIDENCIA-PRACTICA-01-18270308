package com.example.a308_e41registrodeparticipantes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminBDEventos extends SQLiteOpenHelper
{
    //1.a Identificadores para la base de datos y la version
    public static final String NOMBRE_BD = "bdEventos";
    public static final int VERSION_BD = 1;

    //1.b identificadores para la tabla y sus campos
    public static final String NOMBRE_TABLA = "participantes";
    public static final String CAMPO1 = "clave";
    public static final String CAMPO2 = "nombre";
    public static final String CAMPO3 = "institucion";
    public static final String CAMPO4 = "correo";
    public static final String CAMPO5 = "cantidad";

    // 1.c indicador para el contexto
    private Context contexto;

    // 1.d se ajusta el constructor
    public AdminBDEventos(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //1.e query para crear la tabla
        String queryCrearTabla = "CREATE TABLE " + NOMBRE_TABLA + "(" + CAMPO1 + " INTEGER PRIMARY KEY, " + CAMPO2 + " TEXT, " + CAMPO3 + " TEXT, " + CAMPO4 + " TEXT, " + CAMPO5 + " REAL)";
        // CREATE TABLE participantes(clave INTEGER PRIMARY KEY, nombre TEXT, institucion TEXT, ...

        // 1.e.ii se aplica el query
        sqLiteDatabase.execSQL(queryCrearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String queryBorrarTabla = "DROP TABLE IF EXISTS " + NOMBRE_TABLA;
        sqLiteDatabase.execSQL(queryBorrarTabla);
        onCreate(sqLiteDatabase);
    }
}
