package com.fatecerss.tcc.apprendendo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    
    //Variaveis
    private Button bt_signUp;
    private Button bt_login;
    private EditText tf_email;
    private EditText tf_password;
    private TextView lb_start;
    private ProgressDialog progressDialog;
    private ConstraintLayout screenLayout;
    private String teste;

    private FirebaseAuth firebaseAuth;

    //Métodos
    public LoginActivity(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //inicializa os componentes da tela
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //carrega as variaveis de referência para cada componente na tela e desabilita para fazer o efeito do 'tap anywhere...'
        bt_signUp = (Button) findViewById(R.id.btSignUp);
        bt_signUp.setVisibility(View.INVISIBLE);
        bt_signUp.setEnabled(false);
        bt_login = (Button) findViewById(R.id.btLogin);
        bt_login.setVisibility(View.INVISIBLE);
        bt_login.setEnabled(false);
        tf_email = (EditText) findViewById(R.id.editTextEmail);
        tf_email.setVisibility(View.INVISIBLE);
        tf_password = (EditText) findViewById(R.id.editTextPassword);
        tf_password.setVisibility(View.INVISIBLE);
        lb_start = (TextView) findViewById(R.id.textViewStart);
        screenLayout = (ConstraintLayout) findViewById(R.id.layoutScreen);
        //instancia a caixa de progresso
        progressDialog = new ProgressDialog(this);
        //inicializa o objeto que referencia o firebase auth na aplicação
        firebaseAuth = FirebaseAuth.getInstance();

        //cria listener para a função onclick

        bt_signUp.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        screenLayout.setOnClickListener(this);

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
        Toast.makeText(this, this.getString(R.string.loginfail), Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onClick (View view){
        if (view == screenLayout){
            bt_signUp.setVisibility(View.VISIBLE);
            bt_login.setVisibility(View.VISIBLE);
            tf_email.setVisibility(View.VISIBLE);
            tf_password.setVisibility(View.VISIBLE);
            bt_signUp.setEnabled(true);
            bt_login.setEnabled(true);
            lb_start.setVisibility(View.INVISIBLE);
        }

        if (view == bt_login){
            login();
        }

        if (view == bt_signUp) {
            finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }
    }



}
