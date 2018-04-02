package com.fatecerss.tcc.apprendendo.DAO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.fatecerss.tcc.apprendendo.model.Learner;
import com.fatecerss.tcc.apprendendo.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Sandro on 17/03/2018.
 */

public class LearnerDAO extends UserDAO {

    //Variaveis
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference usersReference = databaseReference.child("users");
    private static DatabaseReference learnerReference = databaseReference.child("learners");
    private static DatabaseReference updateReference = databaseReference.child("learners");

    private static int USERALREADYEXISTS = 0;
    private static int USERDOESNOTEXISTS = 1;
    private static int SUCCESS = 1;
    private static int result = -1;
    Learner registerLearner;
    Learner resultLearner;
    Learner updateLearner;

    //Metodos

    public LearnerDAO(){

    }

    public int signUp(Learner learner){

        registerLearner = learner;
        String username = learner.getUsername().trim();
        String email = learner.getEmail().trim();
        String password = learner.getPassword().trim();
        String name = learner.getName().trim();
        String phone = learner.getPhone().trim();
        String birthdate = learner.getBirthdate().trim();
        String bio = learner.getBio().trim();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                                usersReference.setValue(registerLearner);
                                result = SUCCESS;
                        }
                    }
                });
        if (result == SUCCESS){
            return 1;
        }
        return 0;
    }

    //CREATE
    protected void saveUserInDatabase(Learner learner){
        usersReference.child(learner.getUsername()).setValue(learner);
    }

    //UPDATE
    public void updateUserInDatabase(Learner learner){
        saveUserInDatabase(learner);
    }

    //READ
    public Learner readUserInDatabase(String userEmail){
        // resultReference = teacherReference.child(teacher.getUsername());
        Query query = learnerReference.orderByChild("email").equalTo(userEmail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultLearner = (Learner) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return resultLearner;
    }

    //ENABLE
    public void enableUserInDatabase(Object learnerObject){
        Learner learner = (Learner) learnerObject;
        updateReference = learnerReference.child(learner.getUsername());
        updateReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateLearner = (Learner) dataSnapshot.getValue();
                updateLearner.setStatus("ENABLED");
                saveUserInDatabase(updateLearner);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //DISABLE
    public void disableUserInDatabase(Object learnerObject){
        Learner learner = (Learner) learnerObject;
        updateReference = learnerReference.child(learner.getUsername());
        updateReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 updateLearner = (Learner) dataSnapshot.getValue();
                 updateLearner.setStatus("DISABLED");
                 saveUserInDatabase(updateLearner);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //DOES THE USER ALREADY EXIST?
    public int validateUserInDatabase(String learnerUsername) {

        Query queryRef = queryRef = learnerReference.orderByChild("username").equalTo(learnerUsername);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String doesTheNameExist = null;
                doesTheNameExist = dataSnapshot.getValue(String.class);
                if (doesTheNameExist == null) {
                    result = USERDOESNOTEXISTS;
                } else {
                    result = USERALREADYEXISTS;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return result;
    }
}
