package br.edu.ifbaiano.guanambi.aplicacaoshape.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import br.edu.ifbaiano.guanambi.aplicacaoshape.R;
import br.edu.ifbaiano.guanambi.aplicacaoshape.dao.UserDAO;
import br.edu.ifbaiano.guanambi.aplicacaoshape.model.User;

public class ListaUser extends AppCompatActivity {
    ListView lv;

    UserDAO uDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Lista de Usuários");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_user);
        lv = findViewById(R.id.userList);
        uDAO  = new UserDAO(getApplicationContext(), new User());
        Cursor c = uDAO.listarUsers();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.layout_users,
                uDAO.listarUsers(), new String[]{"_id", "nome"},
                new int[]{R.id.tvEmail, R.id.tvNome}, 0);
        lv.setAdapter(adapter);
        }

    }