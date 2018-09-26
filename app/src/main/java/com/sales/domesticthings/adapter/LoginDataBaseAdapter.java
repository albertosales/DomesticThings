package com.sales.domesticthings.adapter;

/**
 * Created by alberto on 11/29/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sales.domesticthings.dao.LoginDataBaseHelper;

public class LoginDataBaseAdapter {
    static final String DATABASE_NOME = "sistema.db";
    static final int DATABASE_VERSAO = 1;
    public static final int NOME_COLUNA = 1;

    public static final String DATABASE_CRIAR = "create table " + "LOGIN" + "( "
            + "ID" + " integer primary key autoincrement,"
            + "USUARIO  text,SENHA text); ";

    public SQLiteDatabase db;
    private final Context context;
    private LoginDataBaseHelper dbHelper;

    public LoginDataBaseAdapter(Context context) {
        this.context = context;
        dbHelper = new LoginDataBaseHelper(context, DATABASE_NOME, null,
                DATABASE_VERSAO);
    }

    public LoginDataBaseAdapter abrir() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    public void inserirEntrada(String usuario, String senha) {
        ContentValues newValues = new ContentValues();
        newValues.put("USUARIO", usuario);
        newValues.put("SENHA", senha);
        db.insert("LOGIN", null, newValues);

    }

    public int deletaEntrada(String usuario) {

        String cl = "USUARIO=?";
        int numEntradasDeletadas = db.delete("LOGIN", cl,
                new String[] { usuario });
        return numEntradasDeletadas;
    }

    public String getEntrada(String usuario) {
        Cursor cursor = db.query("LOGIN", null, " USUARIO=?",
                new String[] { usuario }, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NÃO EXISTE";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("SENHA"));
        cursor.close();
        return password;
    }

    public void atualizaEntrada(String usuario, String senha) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("USUARIO", usuario);
        updatedValues.put("SENHA", senha);

        String cl = "USUARIO = ?";
        db.update("LOGIN", updatedValues, cl, new String[] { usuario });
    }
}