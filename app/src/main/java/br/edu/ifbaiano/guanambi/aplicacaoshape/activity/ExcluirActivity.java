package br.edu.ifbaiano.guanambi.aplicacaoshape.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.edu.ifbaiano.guanambi.aplicacaoshape.R;
import br.edu.ifbaiano.guanambi.aplicacaoshape.dao.UserDAO;
import br.edu.ifbaiano.guanambi.aplicacaoshape.model.User;

public class ExcluirActivity extends AppCompatActivity {
    Button btnSim;
    Button btnNao;
    UserDAO uDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir);

        btnSim = findViewById(R.id.btnSim);
        btnNao = findViewById(R.id.btnNao);

        SharedPreferences sp = getSharedPreferences("appLogin",
                Context.MODE_PRIVATE);
        String email = sp.getString("email","");

        User userDeletar = new User();
        userDeletar.setMail(email);
        Log.d("email", "Email a ser deletado agora: " + userDeletar.getMail());

        uDao = new UserDAO(getApplicationContext(), userDeletar);

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uDao.delete()) {
                    Toast.makeText(ExcluirActivity.this, "Deletado", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getSharedPreferences("appLogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.remove("email");
                    editor.apply();
                    Intent intent = new Intent(ExcluirActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ExcluirActivity.this, "Erro ao excluir", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirecionar = new Intent(ExcluirActivity.this, ActivityPrincipal.class);
                startActivity(redirecionar);
            }
        });
    }
}