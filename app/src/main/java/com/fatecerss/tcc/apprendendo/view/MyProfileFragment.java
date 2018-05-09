package com.fatecerss.tcc.apprendendo.view;


import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.fatecerss.tcc.apprendendo.R;
import com.fatecerss.tcc.apprendendo.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends Fragment implements View.OnClickListener {

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
    private String enable_disable = "null";
    private AlertDialog.Builder warning;
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference usersReference;
    private long backPressedTime = 0;    // used by onBackPressed()


    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        bt_update = (Button) view.findViewById(R.id.btUpdate);
        tf_username = (EditText) view.findViewById(R.id.tf_username);
        tf_email = (EditText) view.findViewById(R.id.tf_email);
        tf_password = (EditText) view.findViewById(R.id.tf_password);
        tf_name = (EditText) view.findViewById(R.id.tf_name);
        tf_phone = (EditText) view.findViewById(R.id.tf_phone);
        tf_birthdate = (EditText) view.findViewById(R.id.tf_datebirth);
        tf_bio = (EditText) view.findViewById(R.id.tf_bio);
        sw_active = (Switch) view.findViewById(R.id.sw_account);

        usersReference = databaseReference.child("users");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            uId = firebaseUser.getUid();
        }

        readUserInDatabase(uId);

        bt_update.setOnClickListener(this);

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

    public void update(){

        //CRIA UMA CAIXA DE DIALOGO COM BOTAO DE SIM E DE CANCELAR
        warning = new AlertDialog.Builder(getActivity());
        final String updatedSuccess = this.getString(R.string.updateSuccess);

        warning.setTitle(R.string.updateWarning);
        warning.setMessage(getString(R.string.updateMessage));
        warning.setIcon(android.R.drawable.ic_dialog_alert);
        warning.setCancelable(true);
        warning.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //CASO SEJA APERTADO A OPÇÃO SIM, CRIA UM USUARIO NOVO PARA ATUALIZAR, COM OS CAMPOS DA TELA
                User updateUser =

                        new User (tf_username.getText().toString().trim(),
                                tf_email.getText().toString().trim(),
                                tf_password.getText().toString().trim(),
                                tf_name.getText().toString().trim(),
                                tf_phone.getText().toString().trim(),
                                tf_birthdate.getText().toString().trim(),
                                tf_bio.getText().toString().trim(),
                                enable_disable);

                //CHAMA O FIREBASE USER PARA ATUALIZAR O EMAIL E O PASSWORD DO USUARIO Q ESTA LOGADO
                firebaseUser.updateEmail(tf_email.getText().toString().trim());
                firebaseUser.updatePassword(tf_password.getText().toString().trim());

                //CADASTRA NO BANCO ONDE A CHAVE PRIMARIA == ID DO USUARIO QUE ESTA LOGADO, CADASTRA O NOVO OBJETO COM AS INFORMAÇÕES DA TELA
                usersReference.child(uId).setValue(updateUser);
                Toast.makeText(getActivity(), updatedSuccess, Toast.LENGTH_LONG).show();
            }});
        warning.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }});
        warning.show();

    }

    @Override
    public void onClick(View v) {
        if (v == bt_update) {
            update();
        }
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
