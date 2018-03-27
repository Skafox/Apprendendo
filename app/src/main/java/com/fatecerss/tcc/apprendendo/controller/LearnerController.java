package com.fatecerss.tcc.apprendendo.controller;

import android.text.TextUtils;

import com.fatecerss.tcc.apprendendo.DAO.LearnerDAO;
import com.fatecerss.tcc.apprendendo.model.Learner;
import com.fatecerss.tcc.apprendendo.model.Teacher;
import com.google.firebase.database.Query;

/**
 * Created by Sandro on 17/03/2018.
 */

public class LearnerController extends UserController {

    private LearnerDAO learnerDAO = new LearnerDAO();

    public int validateSignUp(Object learnerObject){

        Learner learner = (Learner) learnerObject;

        String username = learner.getUsername().trim();
        String email = learner.getEmail().trim();
        String password = learner.getPassword().trim();
        String name = learner.getName().trim();
        String phone = learner.getPhone().trim();
        String birthdate = learner.getBirthdate().trim();
        String bio = learner.getBio().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(birthdate) || TextUtils.isEmpty(bio)) {
        return -1;
        }
        //Chamar DAO para validar se email ja existe
        if (learnerDAO.validateUserInDatabase(learner) == 0){
            return 0;
        }
        //Chamar DAO para cadastrar um novo
        if (learnerDAO.signUp(learner) == 1){
            return 1;
        }
        return 0;
    }

    public Object validateReadUserInDatabase(String email){
        Learner learner;
        learner = (Learner) learnerDAO.readUserInDatabase(email);
        return learner;
    }

    public void validateUpdate(Object learnerObject){
        Learner learner;
        learner = (Learner) learnerObject;
        learnerDAO.updateUserInDatabase(learner);
    }

}
