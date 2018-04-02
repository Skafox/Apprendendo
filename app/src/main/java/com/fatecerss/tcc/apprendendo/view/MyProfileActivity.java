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
import android.widget.Switch;
import android.widget.Toast;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.controller.LearnerController;
import com.fatecerss.tcc.apprendendo.controller.TeacherController;
import com.fatecerss.tcc.apprendendo.model.Learner;
import com.fatecerss.tcc.apprendendo.model.Teacher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_update;
    private EditText tf_username;
    private EditText tf_email;
    private EditText tf_password;
    private EditText tf_name;
    private EditText tf_phone;
    private EditText tf_birthdate;
    private EditText tf_bio;
    private Switch sw_active;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uId;
    private String enable_disable;
    private AlertDialog.Builder warning;
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference usersReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        bt_update = (Button) findViewById(R.id.btUpdate);
        tf_username = (EditText) findViewById(R.id.tf_username);
        tf_email = (EditText) findViewById(R.id.tf_email);
        tf_password = (EditText) findViewById(R.id.tf_password);
        tf_name = (EditText) findViewById(R.id.tf_name);
        tf_phone = (EditText) findViewById(R.id.tf_phone);
        tf_birthdate = (EditText) findViewById(R.id.tf_datebirth);
        tf_bio = (EditText) findViewById(R.id.tf_bio);
        sw_active = (Switch) findViewById(R.id.sw_account);

        usersReference = databaseReference.child("users");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            uId = firebaseUser.getUid();
        }

        readUserInDatabase(uId);

        bt_update.setOnClickListener(this);
        sw_active.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == bt_update) {

            warning = new AlertDialog.Builder(this);
            final String updatedSuccess = this.getString(R.string.updateSuccess);

            warning.setTitle(R.string.updateWarning);
            warning.setMessage(getString(R.string.updateMessage));
            warning.setIcon(android.R.drawable.ic_dialog_alert);
            warning.setCancelable(true);
            warning.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                                Learner updateLearner =

                                        new Learner (tf_username.getText().toString().trim(),
                                                tf_email.getText().toString().trim(),
                                                tf_password.getText().toString().trim(),
                                                tf_name.getText().toString().trim(),
                                                tf_phone.getText().toString().trim(),
                                                tf_birthdate.getText().toString().trim(),
                                                tf_bio.getText().toString().trim(),
                                                enable_disable);

                                firebaseUser.updateEmail(tf_email.getText().toString().trim());
                                firebaseUser.updatePassword(tf_password.getText().toString().trim());

                                usersReference.child(uId).setValue(updateLearner);
                                Toast.makeText(MyProfileActivity.this, updatedSuccess, Toast.LENGTH_LONG).show();
                        }});
            warning.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                  finish();
                }});
            warning.show();
        }

        if (v == sw_active){
            if (sw_active.isChecked()){
                sw_active.setChecked(false);
                enable_disable = "DISABLED";
            }
            if (!sw_active.isChecked()){
                sw_active.setChecked(true);
                enable_disable = "ENABLED";
            }
        }
    }

    public void readUserInDatabase(String uId){
        usersReference.child(uId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tf_username.setText(dataSnapshot.child("username").getValue(String.class));
                tf_email.setText(dataSnapshot.child("email").getValue(String.class));
                tf_username.setEnabled(false);
                tf_password.setText(dataSnapshot.child("password").getValue(String.class));
                tf_name.setText(dataSnapshot.child("name").getValue(String.class));
                tf_phone.setText(dataSnapshot.child("phone").getValue(String.class));
                tf_birthdate.setText(dataSnapshot.child("birthdate").getValue(String.class));
                tf_bio.setText(dataSnapshot.child("bio").getValue(String.class));
                if (dataSnapshot.child("status").getValue(String.class).equals("ENABLED")){
                    sw_active.setChecked(true);
                }
                else{
                    sw_active.setChecked(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
