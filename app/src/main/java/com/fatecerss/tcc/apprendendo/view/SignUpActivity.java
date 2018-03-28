package com.fatecerss.tcc.apprendendo.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import static android.R.attr.checked;

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
    private int tipo=0;
    private Learner learner;
    //private Teacher teacher;
    private ProgressDialog progressDialog;
    private LearnerController learnerController;
    private TeacherController teacherController = new TeacherController();
    private int statusControl=100;

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
        learnerController = new LearnerController();

        progressDialog = new ProgressDialog(this);

        bt_signUp.setOnClickListener(this);
    }




    @Override
    public void onClick (View view) {

        if (view == bt_signUp) {
            String username = String.valueOf(tf_username.getText());
            String email = String.valueOf(tf_email);
            String password = String.valueOf(tf_password);
            String name = String.valueOf(tf_name);
            String phone = String.valueOf(tf_phone);
            String birthdate = String.valueOf(tf_birthdate);
            String bio = String.valueOf(tf_bio);

            learner = new Learner(username,email,password,name,phone,birthdate,bio);

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
}
