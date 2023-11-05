package br.edu.ifbaiano.guanambi.aplicacaoshape.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifbaiano.guanambi.aplicacaoshape.R;
import br.edu.ifbaiano.guanambi.aplicacaoshape.dao.UserDAO;
import br.edu.ifbaiano.guanambi.aplicacaoshape.model.User;

public class Cadastro extends AppCompatActivity {

    Button btnCad;
    EditText email, senha, nome;

    UserDAO uDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad);

        email = findViewById(R.id.edtEmailCad);
        nome = findViewById(R.id.edtNomeCad);
        senha = findViewById(R.id.edtSenhaCad);
        btnCad = findViewById(R.id.btnCad);


        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_ = email.getText().toString();
                String nome_ = nome.getText().toString();
                String senha_ = senha.getText().toString();

                if (email.getText().toString().isEmpty()) {
                    email.setError("Campo obrigatorio");
                } else {

                    User dados = new User(email_, nome_, senha_);

                    uDao = new UserDAO(getApplicationContext(), dados);

                    if (uDao.inserir()) {

//                    SharedPreferences sp = getSharedPreferences("appLogin",
//                            Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("email",email.getText().toString());
//                    editor.commit();

                        Intent it = new Intent(Cadastro.this, MainActivity.class);
                        startActivity(it);
                    } else {
                        Toast.makeText(Cadastro.this, "Erro ao realizar o cadastro", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
       // intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}