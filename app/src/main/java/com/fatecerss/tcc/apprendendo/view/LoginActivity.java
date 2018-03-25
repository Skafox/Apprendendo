package com.fatecerss.tcc.apprendendo.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fatecerss.tcc.apprendendo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Variaveis
    private TextView tf_signUp;
    private Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inicializa os componentes da tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //carrega as variaveis de referência para cada componente na tela e desabilita para fazer o efeito do 'tap anywhere...'
        tf_signUp = (TextView) findViewById(R.id.btSignUp);
        bt_login = (Button) findViewById(R.id.btLogin);

        tf_signUp.setOnClickListener(this);
        bt_login.setOnClickListener(this);

    }


    @Override
    public void onClick (View view){

        if (view == tf_signUp){
            finish();
            startActivity(new Intent(this, LoginEmailActivity.class));
        }

        if (view == tf_signUp) {
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }



}
