package br.edu.ifbaiano.guanambi.aplicacaoshape.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.edu.ifbaiano.guanambi.aplicacaoshape.helper.DBHelper;
import br.edu.ifbaiano.guanambi.aplicacaoshape.model.User;

public class UserDAO {

    private User user;
    private DBHelper db;

    public UserDAO(Context ctx, User user) {
        this.db = new DBHelper(ctx);
        this.user = user;
    }

    public boolean verificarEmailESenha() {

        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String sql = "SELECT * FROM user where email = ? AND senha = ?";
        Cursor cursor = dbLite.rawQuery(sql,
                new String[]{this.user.getMail(), this.user.getPassword()});

        if (cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }

    }


//Metodo de inserir dados no banco de dados
    public boolean inserir (){
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //nos parametros do contentValues devemos colocar nome das colunas no banco os mesmos nos parametros
        cv.put("nome",this.user.getName());
        cv.put("senha", this.user.getPassword());
        cv.put("email", this.user.getMail());

        long ret = dbLite.insert("user",null, cv);

        if (ret > 0){
            return true;
        }
        return false;
    }



//Metodo de atualizar os dados no banco de dados
    public boolean update(){

        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(!user.getName().isEmpty())
            cv.put("nome", user.getName());
        if(!user.getPassword().isEmpty())
            cv.put("senha", user.getPassword());
        if (cv.size() == 0)
            return false;

        // Importante lembrar de colocar a cláusula WHERE para não atualizar para todos
        // Esta (String[]{user.getMail()}) serve para alterar o que está na cláusula WHERE "?"
        long ret = dbLite.update("user", cv, "email = ?", new String[]{user.getMail()});
        dbLite.close(); // Feche a conexão após a atualização

        return ret > 0;
    }

    public void updateUserDetails(String email, String name, String password) {
        this.user.setMail(email);
        this.user.setName(name);
        this.user.setPassword(password);
    }



    public boolean delete(){
        Log.d("Email", "Email a ser deletado: " + this.user.getMail());


        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        //Da mesma forma que o update, o delete funcionando, lembrando de filtrar quem queremos chamar para excluir
        long ret = dbLite.delete("user","email = ?", new String[]{this.user.getMail()} );

        if (ret > 0){
            return true;
        }
        return false;
    }


    public User obterUserByEmail(){

        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String sql = "Select * From user where email = ?; ";
        Cursor c = dbLite.rawQuery(sql,new String[]{this.user.getMail()});
        if(c != null){
            c.moveToFirst();
        }
        //Sobre essa forma de passar os dados pode-se usar em vez de decorar os indices
        // , pode se fazer automaticamente usando o getColumnIndexOrthrow e capitura o indice
//        this.user.setMail(c.getString(c.getColumnIndexOrThrow("email")));

        // E para fazer manualmente, segue-se assim,   this.user.setMail(c.getString(0)); decorando incide de cada coluna
        this.user.setMail(c.getString(c.getColumnIndexOrThrow("email")));
        this.user.setPassword(c.getString(c.getColumnIndexOrThrow("senha")));
        this.user.setName(c.getString(c.getColumnIndexOrThrow("nome")));

        return this.user;

    }

//PROXIMA AULA
//PROXIMA AULA
//    private Cursor listarUsers(){
//
//        SQLiteDatabase dbLite = this.db.getReadableDatabase();
//
//        String sql = "SELECT id as _id, nome From user;";
//        Cursor c = dbLite.rawQuery(sql,null);
//
//        if(c != null){
//            c.moveToFirst();
//        }
//
//        return c;
//    }
//PROXIMA AULA
//PROXIMA AULA
//    public ArrayList<User> listarUsersArray(){
//
//        ArrayList<User> list = new ArrayList<>();
//
//        Cursor c = this.listarUsers();
//
//        while (!c.isAfterLast()){
//            User u = new User(
//                    c.getString(0),
//                    c.getString(2),
//                    c.getString(1)
//            );
//            list.add(u);
//
//        }
//
//        return list;
//    }

}
