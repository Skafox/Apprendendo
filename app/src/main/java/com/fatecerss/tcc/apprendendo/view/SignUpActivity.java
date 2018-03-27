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
    private RadioButton rbAlunoType;
    private RadioButton rbProfessorType;
    private int tipo=0;
    private Learner learner;
    private Teacher teacher;
    private ProgressDialog progressDialog;
    private LearnerController learnerController = new LearnerController();
    private TeacherController teacherController = new TeacherController();

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
        rbAlunoType = (RadioButton) findViewById(R.id.rbAlunoType);
        rbProfessorType = (RadioButton) findViewById(R.id.rbProfessorType);

        progressDialog = new ProgressDialog(this);

        bt_signUp.setOnClickListener(this);
        rbAlunoType.setOnClickListener(this);
        rbProfessorType.setOnClickListener(this);
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rbAlunoType:
                if (checked)
                    this.tipo = 1;
                break;
            case R.id.rbProfessorType:
                if (checked)
                    this.tipo = 2;
                break;
        }
    }

    @Override
    public void onClick (View view) {

        if (view == bt_signUp) {
            if (this.tipo == 1) {

                learner = new Learner((String.valueOf(tf_username)),
                        (String.valueOf(tf_email)),
                        (String.valueOf(tf_password)),
                        (String.valueOf(tf_name)),
                        (String.valueOf(tf_phone)),
                        (String.valueOf(tf_birthdate)),
                        (String.valueOf(tf_bio)));


                if (learnerController.validateSignUp(learner) == -1){
                    Toast.makeText(this, this.getString(R.string.signupmissing), Toast.LENGTH_LONG).show();
                    learner = null;
                    return;
                }

                progressDialog.setMessage(this.getString(R.string.pg_loggingin));
                progressDialog.show();

                if (learnerController.validateSignUp(learner) == 1){
                    progressDialog.dismiss();
                    Intent intentMyProfile = new Intent(getApplicationContext(), MyProfileActivity.class);
                    startActivity(intentMyProfile);
                    finish();
                }

                if (learnerController.validateSignUp(learner) == 0){
                    Toast.makeText(this, this.getString(R.string.signupfail), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }
            if (this.tipo == 2) {

                teacher = new Teacher((String.valueOf(tf_username)),
                        (String.valueOf(tf_email)),
                        (String.valueOf(tf_password)),
                        (String.valueOf(tf_name)),
                        (String.valueOf(tf_phone)),
                        (String.valueOf(tf_birthdate)),
                        (String.valueOf(tf_bio)));


                if (teacherController.validateSignUp(teacher) == -1){
                    Toast.makeText(this, this.getString(R.string.signupmissing), Toast.LENGTH_LONG).show();
                    learner = null;
                    return;
                }

                progressDialog.setMessage(this.getString(R.string.pg_loggingin));
                progressDialog.show();

                if (teacherController.validateSignUp(teacher) == 1){
                    progressDialog.dismiss();
                    Intent intentMyProfile = new Intent(getApplicationContext(), MyProfileActivity.class);
                    startActivity(intentMyProfile);
                    finish();
                }

                if (teacherController.validateSignUp(teacher) == 0){
                    progressDialog.dismiss();
                    Toast.makeText(this, this.getString(R.string.signupfail), Toast.LENGTH_LONG).show();
                }
            }

            else {
                Toast.makeText(this, this.getString(R.string.radioButtonEmpty), Toast.LENGTH_LONG).show();
            }
        }

        if (view == rbAlunoType){
            onRadioButtonClicked(rbAlunoType);
        }

        if (view == rbProfessorType){
            onRadioButtonClicked(rbProfessorType);
        }
    }

}
