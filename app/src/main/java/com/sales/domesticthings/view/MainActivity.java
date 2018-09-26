package com.sales.domesticthings.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sales.domesticthings.R;
import com.sales.domesticthings.adapter.LoginDataBaseAdapter;

public class MainActivity extends AppCompatActivity {
    Button botaoEntrar, botaoRegistrar;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.abrir();

        botaoEntrar = (Button) findViewById(R.id.botaoAcessarLogin);
        botaoRegistrar = (Button) findViewById(R.id.botaoRegistrar);

        botaoRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        RegistrarActivity.class);
                startActivity(intent);
            }
        });
    }

    public void entrar(View V) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_login);
        dialog.setTitle("Login");
        final EditText edtNomeUsuario = (EditText) dialog
                .findViewById(R.id.edtUsuarioLogin);
        final EditText edtSenha = (EditText) dialog
                .findViewById(R.id.edtSenhaLogin);

        Button botaoEntrar = (Button) dialog.findViewById(R.id.botaoAcessarLogin);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String usuario = edtNomeUsuario.getText().toString();
                String senha = edtSenha.getText().toString();
                String senhaRegistrada = loginDataBaseAdapter.getEntrada(usuario);
                if (senha.equals(senhaRegistrada)) {
                    Toast.makeText(MainActivity.this,"Login com Successo",
                            Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    Intent intent = new Intent(MainActivity.this, GavetaLateralND.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this,"Usuário ou Senha não confere",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }


}

