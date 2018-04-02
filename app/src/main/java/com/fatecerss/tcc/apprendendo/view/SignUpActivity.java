package com.fatecerss.tcc.apprendendo.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.controller.LearnerController;
import com.fatecerss.tcc.apprendendo.controller.TeacherController;
import com.fatecerss.tcc.apprendendo.model.Learner;
import com.fatecerss.tcc.apprendendo.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.checked;
import static android.R.attr.password;
import static android.R.id.message;

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
    private Learner learner;
    private ProgressDialog progressDialog;
    private int statusControl=100;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usersReference;
    private int result=0;
    private int ALREADYEXISTS=-2;
    private int FAIL=-1;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        bt_signUp.setOnClickListener(this);
    }



    @Override
    public void onClick (View view) {

        if (view == bt_signUp) {

            String username = tf_username.getText().toString().trim();
            String email = tf_email.getText().toString().trim();
            String password = tf_password.getText().toString().trim();
            String confirmPassword = tf_confirm_password.getText().toString().trim();
            String name = tf_name.getText().toString().trim();
            String phone = tf_phone.getText().toString().trim();
            String birthdate = tf_birthdate.getText().toString().trim();
            String bio = tf_bio.getText().toString().trim();

            learner = new Learner(username,email,password,name,phone,birthdate,bio);

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) ||
                    TextUtils.isEmpty(birthdate) || TextUtils.isEmpty(bio)) {
                Toast.makeText(this, this.getString(R.string.signupmissing), Toast.LENGTH_LONG).show();
            }
            else if(!password.equals(confirmPassword)){
                Toast.makeText(this, this.getString(R.string.signupfailpassword), Toast.LENGTH_LONG).show();
            }

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
                                    return;
                                }
                                if (task.isSuccessful()) {
                                        firebaseUser = firebaseAuth.getCurrentUser();
                                        usersReference.child(firebaseUser.getUid()).setValue(learner);
                                        Intent intentMyProfile = new Intent(getApplicationContext(), MyProfileActivity.class);
                                        startActivity(intentMyProfile);
                                        finish();
                                    }
                                }
                        });

                if (result == FAIL){
                    Toast.makeText(this, this.getString(R.string.signupfail), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    result = -1;
                }
                if (result == ALREADYEXISTS){
                    Toast.makeText(this, this.getString(R.string.validationfail), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    result = -1;
                }
            }
        }

            /*
                progressDialog.setMessage(this.getString(R.string.pg_loggingin));
                progressDialog.show();

                statusControl = learnerController.validateSignUp(learner);
                Toast.makeText(this, String.valueOf(statusControl), Toast.LENGTH_LONG).show();

                /*if (statusControl == -1) {
                    Toast.makeText(this, this.getString(R.string.signupmissing), Toast.LENGTH_LONG).show();
                    learner = null;
                    return;
                }

                if (statusControl == 1) {
                    progressDialog.dismiss();
                    Intent intentMyProfile = new Intent(getApplicationContext(), MyProfileActivity.class);
                    startActivity(intentMyProfile);
                    finish();
                }

                if (statusControl == 0) {
                    Toast.makeText(this, this.getString(R.string.signupfail), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

                if (statusControl == 20) {
                    Toast.makeText(this, ("ENTREI NO CONTROLLER PORRA MAS SEPA O USUARIO JA EXISTE"), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

                if (statusControl == 30) {
                    Toast.makeText(this, ("ENTREI NO CONTROLLER PORRA ENTREI NO DAO CARALHO MAS DEU BOSTA NO DAO"), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(this, ("Não consegui ir para a classe learnerController"), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }*/

            }
                /*if (rbProfessorType.isChecked()) {

                    teacher = new Teacher((String.valueOf(tf_username)),
                            (String.valueOf(tf_email)),
                            (String.valueOf(tf_password)),
                            (String.valueOf(tf_name)),
                            (String.valueOf(tf_phone)),
                            (String.valueOf(tf_birthdate)),
                            (String.valueOf(tf_bio)));

                    progressDialog.setMessage(this.getString(R.string.pg_loggingin));
                    progressDialog.show();

                    statusControl = teacherController.validateSignUp(teacher);

                    if (statusControl == -1) {
                        Toast.makeText(this, this.getString(R.string.signupmissing), Toast.LENGTH_LONG).show();
                        learner = null;
                        return;
                    }

                    if (statusControl == 1) {
                        progressDialog.dismiss();
                        Intent intentMyProfile = new Intent(getApplicationContext(), MyProfileActivity.class);
                        startActivity(intentMyProfile);
                        finish();
                    }

                    if (statusControl == 0) {
                        progressDialog.dismiss();
                        Toast.makeText(this, this.getString(R.string.signupfail), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, (this.getString(R.string.radioButtonEmpty)) + this.tipo, Toast.LENGTH_LONG).show();
                }*/
}
