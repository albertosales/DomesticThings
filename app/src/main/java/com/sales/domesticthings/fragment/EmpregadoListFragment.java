package com.sales.domesticthings.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.sales.domesticthings.R;
import com.sales.domesticthings.adapter.EmpregadoListAdapter;
import com.sales.domesticthings.dao.EmpregadoDAO;
import com.sales.domesticthings.vo.Empregado;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class EmpregadoListFragment extends Fragment implements OnItemClickListener,
		OnItemLongClickListener {

	public static final String ARG_ITEM_ID = "lista_empregado";

	Activity activity;
	ListView employeeListView;
	ArrayList<Empregado> empregados;

	EmpregadoListAdapter empregadoListAdapter;
	EmpregadoDAO empregadoDAO;

	private GetEmpTask task;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();
		empregadoDAO = new EmpregadoDAO(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_emp_list, container,
				false);
		findViewsById(view);

		task = new GetEmpTask(activity);
		task.execute((Void) null);

		employeeListView.setOnItemClickListener(this);
		employeeListView.setOnItemLongClickListener(this);
		// Empregado e = empregadoDAO.getEmployee(1);
		// Log.d("employee e", e.toString());
		return view;
	}

	private void findViewsById(View view) {
		employeeListView = (ListView) view.findViewById(R.id.list_emp);
	}

	@Override
	public void onResume() {
		getActivity().setTitle(R.string.app_name);
		getActivity().getActionBar().setTitle(R.string.app_name);
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> list, View arg1, int position,
			long arg3) {
		Empregado empregado = (Empregado) list.getItemAtPosition(position);

		if (empregado != null) {
			Bundle arguments = new Bundle();
			arguments.putParcelable("empregadoSelecionado", empregado);
			EmpregadoDialogFragment customEmpDialogFragment = new EmpregadoDialogFragment();
			customEmpDialogFragment.setArguments(arguments);
			//customEmpDialogFragment.show(getFragmentManager(),EmpregadoDialogFragment.ARG_ITEM_ID);
			customEmpDialogFragment.show(getFragmentManager(),EmpregadoDialogFragment.ARG_ITEM_ID);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long arg3) {
		Empregado empregado = (Empregado) parent.getItemAtPosition(position);

		// Usa AsyncTask para deletar da base de dados
		empregadoDAO.deletar(empregado);
		empregadoListAdapter.remove(empregado);
		return true;
	}

	public class GetEmpTask extends AsyncTask<Void, Void, ArrayList<Empregado>> {

		private final WeakReference<Activity> activityWeakRef;

		public GetEmpTask(Activity context) {
			this.activityWeakRef = new WeakReference<Activity>(context);
		}

		@Override
		protected ArrayList<Empregado> doInBackground(Void... arg0) {
			ArrayList<Empregado> empregadoList = empregadoDAO.getEmpregados();
			return empregadoList;
		}

		@Override
		protected void onPostExecute(ArrayList<Empregado> empList) {
			if (activityWeakRef.get() != null
					&& !activityWeakRef.get().isFinishing()) {
				Log.d("empregados", empList.toString());
				empregados = empList;
				if (empList != null) {
					if (empList.size() != 0) {
						empregadoListAdapter = new EmpregadoListAdapter(activity,
								empList);
						employeeListView.setAdapter(empregadoListAdapter);
					} else {
						Toast.makeText(activity, "Sem Registro de Empregados",
								Toast.LENGTH_LONG).show();
					}
				}

			}
		}
	}

	/*
	 * This method is invoked from MainActivity onFinishDialog() method. It is
	 * called from EmpregadoDialogFragment when an employee record is updated.
	 * This is used for communicating between fragments.
	 */
	public void updateView() {
		task = new GetEmpTask(activity);
		task.execute((Void) null);
	}
}
