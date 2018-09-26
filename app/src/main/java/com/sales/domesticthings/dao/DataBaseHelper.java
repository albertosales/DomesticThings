package com.sales.domesticthings.dao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EMPREGADODB";
    private static final int DATABASE_VERSION = 1;

    public static final String EMPREGADO_TABELA = "EMPREGADO";

    public static final String ID_COLUNA = "id";
    public static final String NOME_COLUNA = "nome";
    public static final String ANIVERSARIO_COLUNA = "aniversario";
    public static final String SALARIO_COLUNA = "salario";

    public static final String CRIAR_EMPREGADO_TABELA = "CREATE TABLE "
            + EMPREGADO_TABELA + "(" + ID_COLUNA + " INTEGER PRIMARY KEY, "
            + NOME_COLUNA + " TEXT, " + SALARIO_COLUNA + " DOUBLE, "
            + ANIVERSARIO_COLUNA + " DATE" + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // PRAGMA É UTILIZADO PARA ALTERAR OPERAÇÕES NO BANDO DE DADOS - AQUI HABILITA A RESTRIÇÃO foreign key
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_EMPREGADO_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
