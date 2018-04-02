package com.fatecerss.tcc.apprendendo.DAO;

import android.support.v7.app.AppCompatActivity;

import com.fatecerss.tcc.apprendendo.model.Learner;
import com.fatecerss.tcc.apprendendo.model.Teacher;

/**
 * Created by Sandro on 24/03/2018.
 */

abstract class UserDAO extends AppCompatActivity{


    abstract void saveUserInDatabase(Learner learner);

    abstract Learner readUserInDatabase(String userEmail);

    abstract void enableUserInDatabase(Object obj);

    abstract void disableUserInDatabase(Object obj);

    abstract void updateUserInDatabase(Learner learner);

    abstract int validateUserInDatabase(String username);


}
