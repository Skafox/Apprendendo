package com.fatecerss.tcc.apprendendo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Sandro on 17/03/2018.
 */

public class UserDAO extends AppCompatActivity {

    //Variaveis
    private FirebaseAuth firebaseAuth;
    public int loginFlag = 0;

    //Metodos

    public UserDAO(){

    }

    public int logIn(String email, String password){

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            loginFlag = 1;
                        }
                    }
                });
        if (loginFlag == 1){
            return 1;
        }
        return 0;
    }

}
