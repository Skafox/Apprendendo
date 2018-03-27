package com.fatecerss.tcc.apprendendo.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.controller.LearnerController;
import com.fatecerss.tcc.apprendendo.controller.TeacherController;
import com.fatecerss.tcc.apprendendo.model.Learner;
import com.fatecerss.tcc.apprendendo.model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_update;
    private EditText tf_username;
    private EditText tf_email;
    private EditText tf_password;
    private EditText tf_name;
    private EditText tf_phone;
    private EditText tf_birthdate;
    private EditText tf_bio;
    private SwitchCompat sw_active;
    private Learner learner = null;
    private Teacher teacher = null;
    private ProgressDialog progressDialog;
    private LearnerController learnerController;
    private TeacherController teacherController;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String currentEmail;
    private int type=0;
    private int TYPELEARNER=1;
    private int TYPETEACHER=2;
    private int result=0;
    private int SUCCESS=1;
    private AlertDialog warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        bt_update = (Button) findViewById(R.id.btUpdate);
        tf_username = (EditText) findViewById(R.id.tf_create_username);
        tf_email = (EditText) findViewById(R.id.tf_create_email);
        tf_password = (EditText) findViewById(R.id.tf_password);
        tf_name = (EditText) findViewById(R.id.tf_create_name);
        tf_phone = (EditText) findViewById(R.id.tf_create_phone);
        tf_birthdate = (EditText) findViewById(R.id.tf_birth_date);
        tf_bio = (EditText) findViewById(R.id.tf_create_bio);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    currentEmail = firebaseUser.getEmail();
                }
            }
        };

        learner = (Learner) learnerController.validateReadUserInDatabase(currentEmail);
        teacher = (Teacher) teacherController.validateReadUserInDatabase(currentEmail);
        if (learner == null){
            type = TYPELEARNER;
        }
        if (teacher == null){
            type = TYPETEACHER;
        }

        if (type == TYPELEARNER) {
            tf_username.setText(learner.getUsername());
            tf_username.setEnabled(false);
            tf_email.setText(learner.getEmail());
            tf_password.setText(learner.getPassword());
            tf_name.setText(learner.getName());
            tf_phone.setText(learner.getPhone());
            tf_birthdate.setText(learner.getBirthdate());
            tf_bio.setText(learner.getBio());
        }

        if (type == TYPETEACHER){
            tf_username.setText(teacher.getUsername());
            tf_username.setEnabled(false);
            tf_email.setText(teacher.getEmail());
            tf_password.setText(teacher.getPassword());
            tf_name.setText(teacher.getName());
            tf_phone.setText(teacher.getPhone());
            tf_birthdate.setText(teacher.getBirthdate());
            tf_bio.setText(teacher.getBio());
        }


    }

    @Override
    public void onClick(View v) {
        if (v == bt_update) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.updateWarning)
                    .setMessage(R.string.updateMessage)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (type == TYPELEARNER) {
                                Learner updateLearner =
                                        new Learner (String.valueOf(learner.getUsername()),
                                                (String.valueOf(tf_email)),
                                                (String.valueOf(tf_password)),
                                                (String.valueOf(tf_name)),
                                                (String.valueOf(tf_phone)),
                                                (String.valueOf(tf_birthdate)),
                                                (String.valueOf(tf_bio)));
                                firebaseUser.updateEmail(String.valueOf(tf_email));
                                firebaseUser.updateEmail(String.valueOf(tf_password));
                                learnerController.validateUpdate(updateLearner);
                                result=SUCCESS;
                            }
                            if (type == TYPETEACHER){
                                Teacher updateTeacher =
                                        new Teacher(String.valueOf(teacher.getUsername()),
                                                (String.valueOf(tf_email)),
                                                (String.valueOf(tf_password)),
                                                (String.valueOf(tf_name)),
                                                (String.valueOf(tf_phone)),
                                                (String.valueOf(tf_birthdate)),
                                                (String.valueOf(tf_bio)));
                                firebaseUser.updateEmail(String.valueOf(tf_email));
                                firebaseUser.updatePassword(String.valueOf(tf_password));
                                teacherController.validateUpdate(updateTeacher);
                                result=SUCCESS;
                            }
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
            if (result != SUCCESS){
                Toast.makeText(this, this.getString(R.string.updatefail), Toast.LENGTH_LONG).show();
            }
        }

    }
}
