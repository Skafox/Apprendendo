package com.fatecerss.tcc.apprendendo.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fatecerss.tcc.apprendendo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {
    //Variaveis
    private Button bt_signUp;
    private Button bt_login;
    private EditText tf_email;
    private EditText tf_password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    //Métodos


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //inicializa os componentes da tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //carrega as variaveis de referência para cada componente na tela e desabilita para fazer o efeito do 'tap anywhere...'
        bt_signUp = (Button) findViewById(R.id.btSignUp);
        bt_login = (Button) findViewById(R.id.btLogin);
        tf_email = (EditText) findViewById(R.id.editTextEmail);
        tf_password = (EditText) findViewById(R.id.editTextPassword);
        //instancia a caixa de progresso
        progressDialog = new ProgressDialog(this);
        //inicializa o objeto que referencia o firebase auth na aplicação
        firebaseAuth = FirebaseAuth.getInstance();

        //cria listener para a função onclick

        bt_signUp.setOnClickListener(this);
        bt_login.setOnClickListener(this);

    }


    public void login() {

        String email = tf_email.getText().toString().trim();
        String password = tf_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, this.getString(R.string.emailloginvalidation), Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, this.getString(R.string.passwordloginvalidation), Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage(this.getString(R.string.pg_loggingin));
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            finish();
                            Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intentHome);
                        }
                    }
                });

        progressDialog.dismiss();
        Toast.makeText(this, this.getString(R.string.loginfail), Toast.LENGTH_LONG).show();


    }


    @Override
    public void onClick (View view){

        if (view == bt_login){
            login();
        }

        if (view == bt_signUp) {
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }



}
