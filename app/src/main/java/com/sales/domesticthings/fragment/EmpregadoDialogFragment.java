package com.sales.domesticthings.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sales.domesticthings.R;
import com.sales.domesticthings.dao.EmpregadoDAO;
import com.sales.domesticthings.view.MainActivity;
import com.sales.domesticthings.vo.Empregado;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class EmpregadoDialogFragment extends DialogFragment {

	// UI references
	private EditText empNomeEtxt;
	private EditText empSalarioEtxt;
	private EditText empAniversarioEtxt;
	private LinearLayout enviarLayout;

	private Empregado empregado;

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);
	
	EmpregadoDAO empregadoDAO;
	
	public static final String ARG_ITEM_ID = "emp_dialog_fragment";

	public interface EmpDialogFragmentListener {
		void onFinishDialog();
	}

	public EmpregadoDialogFragment() {

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		empregadoDAO = new EmpregadoDAO(getActivity());

		Bundle bundle = this.getArguments();
		empregado = bundle.getParcelable("empregadoSelecionado");

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();

		View customDialogView = inflater.inflate(R.layout.fragment_adiciona_emp,
				null);
		builder.setView(customDialogView);

		empNomeEtxt = (EditText) customDialogView.findViewById(R.id.etxt_nome);
		empSalarioEtxt = (EditText) customDialogView
				.findViewById(R.id.etxt_salario);
		empAniversarioEtxt = (EditText) customDialogView.findViewById(R.id.etxt_aniversario);
		enviarLayout = (LinearLayout) customDialogView
				.findViewById(R.id.enviar_layout);
		enviarLayout.setVisibility(View.GONE);

		setValue();

		builder.setTitle(R.string.atualiza_emp);
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.atualizar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						try {
							empregado.setAniversario(formatter.parse(empAniversarioEtxt.getText().toString()));
						} catch (ParseException e) {
							Toast.makeText(getActivity(),
									"formato de data inválido!",
									Toast.LENGTH_SHORT).show();
							return;
						}
						empregado.setNome(empNomeEtxt.getText().toString());
						empregado.setSalario(Double.parseDouble(empSalarioEtxt
								.getText().toString()));
						long result = empregadoDAO.atualizar(empregado);
						if (result > 0) {
							MainActivity activity = (MainActivity) getActivity();
							activity.finish();
						} else {
							Toast.makeText(getActivity(),
									"Desabilitado para atualizar o empregado",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		builder.setNegativeButton(R.string.cancelar,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = builder.create();

		return alertDialog;
	}

	private void setValue() {
		if (empregado != null) {
			empNomeEtxt.setText(empregado.getNome());
			empSalarioEtxt.setText(empregado.getSalario() + "");
			empAniversarioEtxt.setText(formatter.format(empregado.getAniversario()));
		}
	}
}
