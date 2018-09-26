package com.sales.domesticthings.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sales.domesticthings.R;
import com.sales.domesticthings.vo.Empregado;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EmpregadoListAdapter extends ArrayAdapter<Empregado> {

	private Context context;
	List<Empregado> empregados;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	public EmpregadoListAdapter(Context context, List<Empregado> empregados) {
		super(context, R.layout.list_item, empregados);
		this.context = context;
		this.empregados = empregados;
	}

	private class ViewHolder {
		TextView empIdTxt;
		TextView empNomeTxt;
		TextView empAniversarioTxt;
		TextView empSalarioTxt;
	}

	@Override
	public int getCount() {
		return empregados.size();
	}

	@Override
	public Empregado getItem(int position) {
		return empregados.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();

			holder.empIdTxt = (TextView) convertView.findViewById(R.id.txt_emp_id);
			holder.empNomeTxt = (TextView) convertView
					.findViewById(R.id.txt_emp_nome);
			holder.empAniversarioTxt = (TextView) convertView
					.findViewById(R.id.txt_emp_aniversario);
			holder.empSalarioTxt = (TextView) convertView
					.findViewById(R.id.txt_emp_salario);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Empregado empregado = (Empregado) getItem(position);
		holder.empIdTxt.setText(empregado.getId() + "");
		holder.empNomeTxt.setText(empregado.getNome());
		holder.empSalarioTxt.setText(empregado.getSalario() + "");

		holder.empAniversarioTxt.setText(formatter.format(empregado.getAniversario()));

		return convertView;
	}

	@Override
	public void add(Empregado empregado) {
		empregados.add(empregado);
		notifyDataSetChanged();
		super.add(empregado);
	}

	@Override
	public void remove(Empregado empregado) {
		empregados.remove(empregado);
		notifyDataSetChanged();
		super.remove(empregado);
	}
}
