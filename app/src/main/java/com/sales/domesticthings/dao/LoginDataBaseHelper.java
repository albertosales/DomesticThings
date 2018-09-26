package com.sales.domesticthings.dao;

/**
 * Created by alberto on 11/29/16.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sales.domesticthings.adapter.LoginDataBaseAdapter;

public class LoginDataBaseHelper extends SQLiteOpenHelper {
    public LoginDataBaseHelper(Context context, String nome, CursorFactory factory,
                               int versao) {
        super(context, nome, factory, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LoginDataBaseAdapter.DATABASE_CRIAR);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
        Log.w("TaskDBAdapter", "Atualizando da versão " + versaoAntiga + " para "
                + novaVersao + ", que destruirá todos os dados antigos");
        db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");

        onCreate(db);
    }

}