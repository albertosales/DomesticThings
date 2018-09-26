package com.sales.domesticthings.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sales.domesticthings.R;
import com.sales.domesticthings.adapter.LoginDataBaseAdapter;

public class RegistrarActivity extends AppCompatActivity {

    EditText edtNomeUsuario, edtSenha, edtConfirmaSenha;
    Button botaoCriarConta;
    Context context = this;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.abrir();
        edtNomeUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        edtConfirmaSenha = (EditText) findViewById(R.id.edtConfirmaSenha);

        botaoCriarConta = (Button) findViewById(R.id.botaoCriarConta);
        botaoCriarConta.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String usuario = edtNomeUsuario.getText().toString();
                String senha = edtSenha.getText().toString();
                String confirmarSenha = edtConfirmaSenha.getText()
                        .toString();
                if (usuario.equals("") || senha.equals("")
                        || confirmarSenha.equals("")) {

                    Toast.makeText(getApplicationContext(), "Campo Vazio!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!senha.equals(confirmarSenha)) {
                    Toast.makeText(getApplicationContext(),
                            "Senhão não confere ", Toast.LENGTH_LONG)
                            .show();
                    return;
                } else {

                    loginDataBaseAdapter.inserirEntrada(usuario, senha);
                    Toast.makeText(getApplicationContext(),
                            "Conta criada com sucesso!!! ", Toast.LENGTH_LONG)
                            .show();
                    Intent i = new Intent(RegistrarActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();

                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
