package com.sales.domesticthings.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EmpregadoDBDAO {

	protected SQLiteDatabase database;
	private DataBaseHelper dbHelper;
	private Context mContext;

	public EmpregadoDBDAO(Context mContext) {
		this.mContext = mContext;
		dbHelper = DataBaseHelper.getHelper(this.mContext);
		abrirDB();
		
	}

	public void abrirDB() throws SQLException {
		if(dbHelper == null)
			dbHelper = DataBaseHelper.getHelper(mContext);
		database = dbHelper.getWritableDatabase();
	}
	
	public void fecharDB() {
		dbHelper.close();
		database = null;
	}
}
