package br.edu.ifbaiano.guanambi.aplicacaoshape.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifbaiano.guanambi.aplicacaoshape.R;
import br.edu.ifbaiano.guanambi.aplicacaoshape.dao.UserDAO;
import br.edu.ifbaiano.guanambi.aplicacaoshape.model.User;

public class AtualizarActivity extends AppCompatActivity {

    EditText edtNome;
    TextView txtemail;
    EditText edtSenha;
    Button btnAtualizar;
    UserDAO uDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        edtNome = findViewById(R.id.edtNome);
        txtemail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnAtualizar = findViewById(R.id.btnAtualizar);

        SharedPreferences sp = getSharedPreferences("appLogin",
                Context.MODE_PRIVATE);
        String email = sp.getString("email","");
        txtemail.setText(email);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = txtemail.getText().toString();
                String nome = edtNome.getText().toString();
                String senha = edtSenha.getText().toString();
                uDao = new UserDAO(getApplicationContext(), new User("", "", ""));

                User dados = new User(email, nome, senha);
                uDao = new UserDAO(getApplicationContext(), dados);

                uDao.updateUserDetails(email, nome, senha);

                if (uDao.update()) {
                    Toast.makeText(AtualizarActivity.this, "Dados atualizados", Toast.LENGTH_SHORT).show();
                        Intent redirecionar = new Intent(AtualizarActivity.this, ActivityPrincipal.class);
                        startActivity(redirecionar);
                } else {
                    Toast.makeText(AtualizarActivity.this, "Erro ao atualizar", Toast.LENGTH_SHORT).show();

                }

               }



        });
    }
}
