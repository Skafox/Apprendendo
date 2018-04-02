package com.fatecerss.tcc.apprendendo.DAO;

import android.support.annotation.NonNull;

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
 * Created by Sandro on 24/03/2018.
 */

public class TeacherDAO extends UserDAO {

    //Variaveis
    private static FirebaseAuth firebaseAuth;
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference teacherReference = databaseReference.child("teachers");
    private static DatabaseReference resultReference = databaseReference.child("teachers");
    private static DatabaseReference updateReference = databaseReference.child("teachers");

    private static int USERALREADYEXISTS = 0;
    private static int USERDOESNOTEXISTS = 1;
    private static int SUCCESS = 1;
    private static int result = 0;
    Learner resultTeacher = null;
    Teacher updateTeacher;

    //Metodos

    public TeacherDAO(){

    }

    public int signUp(final Learner teacher){

        String email = teacher.getEmail().trim();
        String password = teacher.getPassword().trim();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (validateUserInDatabase(teacher.getUsername()) == 1) {
                                saveUserInDatabase(teacher);
                                result = SUCCESS;
                            }
                        }
                    }
                });
        if (result == SUCCESS){
            return 1;
        }
        return 0;
    }

    //CREATE
    protected void saveUserInDatabase(Learner teacherObject){
        teacherReference.child(teacherObject.getUsername()).setValue(teacherObject);
    }

    //UPDATE
    public void updateUserInDatabase(Learner teacherObject){
        saveUserInDatabase(teacherObject);
    }

    //READ
    public Learner readUserInDatabase(String userEmail){
        // resultReference = teacherReference.child(teacher.getUsername());
        Query query = teacherReference.orderByChild("email").equalTo(userEmail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                resultTeacher = (Learner) dataSnapshot.getValue();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return resultTeacher;
    }

    //ENABLE
    public void enableUserInDatabase(Object teacherObject){
        Teacher teacher = (Teacher) teacherObject;
        updateReference = teacherReference.child(teacher.getUsername());
        updateReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateTeacher = (Teacher) dataSnapshot.getValue();
                updateTeacher.setStatus("ENABLED");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //DISABLE
    public void disableUserInDatabase(Object teacherObject){
        Teacher teacher = (Teacher) teacherObject;
        updateReference = teacherReference.child(teacher.getUsername());
        updateReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateTeacher = (Teacher) dataSnapshot.getValue();
                updateTeacher.setStatus("DISABLED");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //VALIDATE
    public int validateUserInDatabase(String username) {
        Query queryRef = null;
        queryRef = teacherReference.orderByChild("username").equalTo(username);
        if (queryRef == null) {
            return USERDOESNOTEXISTS;
        } else {
            return USERALREADYEXISTS;
        }
    }


}
