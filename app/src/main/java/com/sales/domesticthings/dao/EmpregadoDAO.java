package com.sales.domesticthings.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.sales.domesticthings.vo.Empregado;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class EmpregadoDAO extends EmpregadoDBDAO {

	private static final String WHERE_ID_EQUALS = DataBaseHelper.ID_COLUNA
			+ " =?";
	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	public EmpregadoDAO(Context context) {
		super(context);
	}

	public long salvar(Empregado empregado) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NOME_COLUNA, empregado.getNome());
		Log.d("dob", empregado.getAniversario().getTime() + "");
		values.put(DataBaseHelper.ANIVERSARIO_COLUNA, formatter.format(empregado.getAniversario()));
		values.put(DataBaseHelper.SALARIO_COLUNA, empregado.getSalario());

		return database.insert(DataBaseHelper.EMPREGADO_TABELA, null, values);
	}

	public long atualizar(Empregado empregado) {
		ContentValues values = new ContentValues();
		values.put(DataBaseHelper.NOME_COLUNA, empregado.getNome());
		values.put(DataBaseHelper.ANIVERSARIO_COLUNA, formatter.format(empregado.getAniversario()));
		values.put(DataBaseHelper.SALARIO_COLUNA, empregado.getSalario());

		long resultado = database.update(DataBaseHelper.EMPREGADO_TABELA, values,
				WHERE_ID_EQUALS,
				new String[] { String.valueOf(empregado.getId()) });
		Log.d("Resultado da atualização:", "=" + resultado);
		return resultado;

	}

	public int deletar(Empregado empregado) {
		return database.delete(DataBaseHelper.EMPREGADO_TABELA, WHERE_ID_EQUALS,
				new String[] { empregado.getId() + "" });
	}

	//Usando método query()
	public ArrayList<Empregado> getEmpregados() {
		ArrayList<Empregado> empregados = new ArrayList<Empregado>();

		Cursor cursor = database.query(DataBaseHelper.EMPREGADO_TABELA,
				new String[] { DataBaseHelper.ID_COLUNA,
						DataBaseHelper.NOME_COLUNA,
						DataBaseHelper.ANIVERSARIO_COLUNA,
						DataBaseHelper.SALARIO_COLUNA}, null, null, null,
				null, null);

		while (cursor.moveToNext()) {
			Empregado empregado = new Empregado();
			empregado.setId(cursor.getInt(0));
			empregado.setNome(cursor.getString(1));
			try {
				empregado.setAniversario(formatter.parse(cursor.getString(2)));
			} catch (ParseException e) {
				empregado.setAniversario(null);
			}
			empregado.setSalario(cursor.getDouble(3));

			empregados.add(empregado);
		}
		return empregados;
	}
	
	//USING rawQuery() method
	/*public ArrayList<Empregado> getEmpregados() {
		ArrayList<Empregado> employees = new ArrayList<Empregado>();

		String sql = "SELECT " + DataBaseHelper.ID_COLUNA + ","
				+ DataBaseHelper.NAME_COLUMN + ","
				+ DataBaseHelper.ANIVERSARIO_COLUNA + ","
				+ DataBaseHelper.SALARIO_COLUNA + " FROM "
				+ DataBaseHelper.EMPLOYEE_TABLE;

		Cursor cursor = database.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			Empregado employee = new Empregado();
			employee.setId(cursor.getInt(0));
			employee.setNome(cursor.getString(1));
			try {
				employee.setAniversario(formatter.parse(cursor.getString(2)));
			} catch (ParseException e) {
				employee.setAniversario(null);
			}
			employee.setSalario(cursor.getDouble(3));

			employees.add(employee);
		}
		return employees;
	}*/
	
	//Retorna um único registro de empregado dado um ID
	public Empregado getEmpregado(long id) {
		Empregado empregado = null;

		String sql = "SELECT * FROM " + DataBaseHelper.EMPREGADO_TABELA
				+ " WHERE " + DataBaseHelper.ID_COLUNA + " = ?";

		Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

		if (cursor.moveToNext()) {
			empregado = new Empregado();
			empregado.setId(cursor.getInt(0));
			empregado.setNome(cursor.getString(1));
			try {
				empregado.setAniversario(formatter.parse(cursor.getString(2)));
			} catch (ParseException e) {
				empregado.setAniversario(null);
			}
			empregado.setSalario(cursor.getDouble(3));
		}
		return empregado;
	}
}
