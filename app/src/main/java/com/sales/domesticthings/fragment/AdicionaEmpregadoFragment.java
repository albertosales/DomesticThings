package com.sales.domesticthings.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.sales.domesticthings.R;
import com.sales.domesticthings.dao.EmpregadoDAO;
import com.sales.domesticthings.vo.Empregado;
import com.sales.domesticthings.dao.EmpregadoDAO;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AdicionaEmpregadoFragment extends Fragment implements OnClickListener {

	// UI references
	private EditText empNomeEtxt;
	private EditText empSalarioEtxt;
	private EditText empAniversarioEtxt;
	private Button botaoAdicionar;
	private Button botaoReset;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	DatePickerDialog datePickerDialog;
	Calendar dateCalendar;

	Empregado empregado = null;
	private EmpregadoDAO empregadoDAO;
	private AddEmpTask task;

	public static final String ARG_ITEM_ID = "adiciona_empregado_fragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		empregadoDAO = new EmpregadoDAO(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_adiciona_emp, container,
				false);

		findViewsById(rootView);

		setListeners();

		//para orientação de mudanças
		if (savedInstanceState != null) {
			dateCalendar = Calendar.getInstance();
			if (savedInstanceState.getLong("dateCalendar") != 0)
				dateCalendar.setTime(new Date(savedInstanceState
						.getLong("dateCalendar")));
		}

		return rootView;
	}

	private void setListeners() {
		empAniversarioEtxt.setOnClickListener(this);
		Calendar newCalendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(getActivity(),
				new OnDateSetListener() {

					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						dateCalendar = Calendar.getInstance();
						dateCalendar.set(year, monthOfYear, dayOfMonth);
						empAniversarioEtxt.setText(formatter.format(dateCalendar
								.getTime()));
					}

				}, newCalendar.get(Calendar.YEAR),
				newCalendar.get(Calendar.MONTH),
				newCalendar.get(Calendar.DAY_OF_MONTH));

		botaoAdicionar.setOnClickListener(this);
		botaoReset.setOnClickListener(this);
	}

	protected void resetAllFields() {
		empNomeEtxt.setText("");
		empSalarioEtxt.setText("");
		empAniversarioEtxt.setText("");
	}

	private void setEmployee() {
		empregado = new Empregado();
		empregado.setNome(empNomeEtxt.getText().toString());
		empregado.setSalario(Double.parseDouble(empSalarioEtxt.getText()
				.toString()));
		if (dateCalendar != null)
			empregado.setAniversario(dateCalendar.getTime());
	}

	@Override
	public void onResume() {
		getActivity().setTitle(R.string.adiciona_emp);
		getActivity().getActionBar().setTitle(R.string.adiciona_emp);
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (dateCalendar != null)
			outState.putLong("dateCalendar", dateCalendar.getTime().getTime());
	}

	private void findViewsById(View rootView) {
		empNomeEtxt = (EditText) rootView.findViewById(R.id.etxt_nome);
		empSalarioEtxt = (EditText) rootView.findViewById(R.id.etxt_salario);
		empAniversarioEtxt = (EditText) rootView.findViewById(R.id.etxt_aniversario);
		empAniversarioEtxt.setInputType(InputType.TYPE_NULL);

		botaoAdicionar = (Button) rootView.findViewById(R.id.botao_adicionar);
		botaoReset = (Button) rootView.findViewById(R.id.botao_reset);
	}

	@Override
	public void onClick(View view) {
		if (view == empAniversarioEtxt) {
			datePickerDialog.show();
		} else if (view == botaoAdicionar) {
			setEmployee();

			task = new AddEmpTask(getActivity());
			task.execute((Void) null);
		} else if (view == botaoReset) {
			resetAllFields();
		}
	}

	public class AddEmpTask extends AsyncTask<Void, Void, Long> {

		private final WeakReference<Activity> activityWeakRef;

		public AddEmpTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected Long doInBackground(Void... arg0) {
			long result = empregadoDAO.salvar(empregado);
			return result;
		}

		@Override
		protected void onPostExecute(Long result) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				if (result != -1)
					Toast.makeText(activityWeakRef.get(), "Empregado Gravado",
							Toast.LENGTH_LONG).show();
			}
		}
	}
}
