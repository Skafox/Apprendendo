package com.fatecerss.tcc.apprendendo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by Sandro on 17/03/2018.
 */

public class UserController {
    //Variaveis
    public UserDAO userDAO = new UserDAO();


    //Metodos
    public UserController(){

    }

    public int validateLogin(EditText tf_email, EditText tf_password){
        String email = tf_email.getText().toString().trim();
        String password = tf_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
        //Toast.makeText(this, this.getString(R.string.emailloginvalidation), Toast.LENGTH_SHORT).show();
        return 0;
        }
        if (TextUtils.isEmpty(password)){
        //Toast.makeText(this, this.getString(R.string.passwordloginvalidation), Toast.LENGTH_SHORT).show();
        return 0;
        }
        //não tem métodos construtores
        if (userDAO.logIn(email,password) == 1){
            return 1;
        }
        return 0;
    }

}
