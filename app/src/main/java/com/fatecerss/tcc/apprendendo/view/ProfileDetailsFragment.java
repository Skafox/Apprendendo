package com.fatecerss.tcc.apprendendo.view;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;

import com.fatecerss.tcc.apprendendo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sandro on 02/06/2018.
 */

public class ProfileDetailsFragment extends Fragment {

    private Button bt_update;
    private EditText tf_username;
    private EditText tf_email;
    private EditText tf_password;
    private EditText tf_name;
    private EditText tf_phone;
    private EditText tf_birthdate;
    private EditText tf_bio;
    private Switch sw_active;
    private RatingBar ratingBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String uId;
    private String enable_disable = "null";
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference usersReference;
    private ImageView imageViewProfilePicture;
    public Bitmap profileBitmap;
    Map<String, Bitmap> pictures = new HashMap<String, Bitmap>();


    public ProfileDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        uId = getArguments().getString("uId");

        bt_update = (Button) view.findViewById(R.id.btUpdate);
        tf_username = (EditText) view.findViewById(R.id.tf_username);
        tf_email = (EditText) view.findViewById(R.id.tf_email);
        tf_password = (EditText) view.findViewById(R.id.tf_password);
        tf_name = (EditText) view.findViewById(R.id.tf_name);
        tf_phone = (EditText) view.findViewById(R.id.tf_phone);
        tf_birthdate = (EditText) view.findViewById(R.id.tf_datebirth);
        tf_bio = (EditText) view.findViewById(R.id.tf_bio);
        sw_active = (Switch) view.findViewById(R.id.sw_account);
        imageViewProfilePicture = (ImageView) view.findViewById(R.id.imageViewProfilePicture);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingBar.setEnabled(false);
        bt_update.setVisibility(View.GONE);
        tf_password.setVisibility(View.GONE);
        tf_phone.setVisibility(View.GONE);
        sw_active.setEnabled(false);
        tf_username.setEnabled(false);
        tf_email.setEnabled(false);
        tf_name.setEnabled(false);
        tf_birthdate.setEnabled(false);
        tf_bio.setEnabled(false);

        usersReference = databaseReference.child("users");

        readUserInDatabase(uId);

        //SETA UM LISTENER PARA O SWITCH
        sw_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    enable_disable = "ENABLED";
                }
                if (!isChecked){
                    enable_disable = "DISABLED";
                }
            }
        });

        return view;

    }

    public void readUserInDatabase(String uId){

        //RECEBE O ID DO USUARIO QUE ESTÁ LOGADO COMO PARÂMETRO

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
