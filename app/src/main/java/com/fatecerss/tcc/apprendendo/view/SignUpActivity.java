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
import com.fatecerss.tcc.apprendendo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_signUp;
    private EditText tf_username;
    private EditText tf_email;
    private EditText tf_password;
    private EditText tf_confirm_password;
    private EditText tf_name;
    private EditText tf_phone;
    private EditText tf_birthdate;
    private EditText tf_bio;
    private User user;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usersReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //INICIALIZA AS VARIAVEIS DE REFERENCIA E CARREGA ELAS COM OS COMPONENTES DA TELA
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bt_signUp = (Button) findViewById(R.id.bt_create);
        tf_username = (EditText) findViewById(R.id.tf_create_username);
        tf_email = (EditText) findViewById(R.id.tf_create_email);
        tf_password = (EditText) findViewById(R.id.tf_create_password);
        tf_confirm_password = (EditText) findViewById(R.id.tf_create_confirm_password);
        tf_name = (EditText) findViewById(R.id.tf_create_name);
        tf_phone = (EditText) findViewById(R.id.tf_create_phone);
        tf_birthdate = (EditText) findViewById(R.id.tf_birth_date);
        tf_bio = (EditText) findViewById(R.id.tf_create_bio);

        usersReference = databaseReference.child("users");

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        //SETA UM LISTENER PARA O BOTAO PARA PODER EXECUTAR ALGO QUANDO ELE FOR PRESSIONADO
        bt_signUp.setOnClickListener(this);
    }



    @Override
    public void onClick (View view) {

        if (view == bt_signUp) {

            //PEGA A INFORMAÇÃO DAS EDIT TEXT
            String username = tf_username.getText().toString().trim();
            String email = tf_email.getText().toString().trim();
            String password = tf_password.getText().toString().trim();
            String confirmPassword = tf_confirm_password.getText().toString().trim();
            String name = tf_name.getText().toString().trim();
            String phone = tf_phone.getText().toString().trim();
            String birthdate = tf_birthdate.getText().toString().trim();
            String bio = tf_bio.getText().toString().trim();

            //CRIA UM OBJETO USUARIO PARA COLOCAR NO BANCO DE DADOS
            user = new User(username,email,password,name,phone,birthdate,bio);

            //VALIDA OS CAMPOS
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) ||
                    TextUtils.isEmpty(birthdate) || TextUtils.isEmpty(bio)) {
                Toast.makeText(this, this.getString(R.string.signupmissing), Toast.LENGTH_LONG).show();
            }
            else if(!password.equals(confirmPassword)){
                Toast.makeText(this, this.getString(R.string.signupfailpassword), Toast.LENGTH_LONG).show();
            }

            //CHAMA O FIREBASE PARA CADASTRAR USUARIO
            else{
                progressDialog.setMessage(this.getString(R.string.pg_loggingin));
                progressDialog.show();

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if(!task.isSuccessful()){
                                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                    Toast.makeText(SignUpActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    return;
                                }
                                if (task.isSuccessful()) {
                                        firebaseUser = firebaseAuth.getCurrentUser();
                                        usersReference.child(firebaseUser.getUid()).setValue(user);
                                        Intent intentMyProfile = new Intent(getApplicationContext(), MyProfileActivity.class);
                                        startActivity(intentMyProfile);
                                        finish();
                                    }
                                }
                        });

                }
            }
    }


}
