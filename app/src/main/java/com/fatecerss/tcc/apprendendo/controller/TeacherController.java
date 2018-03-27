package com.fatecerss.tcc.apprendendo.controller;

import android.text.TextUtils;

import com.fatecerss.tcc.apprendendo.DAO.LearnerDAO;
import com.fatecerss.tcc.apprendendo.DAO.TeacherDAO;
import com.fatecerss.tcc.apprendendo.model.Learner;
import com.fatecerss.tcc.apprendendo.model.Teacher;

/**
 * Created by Sandro on 24/03/2018.
 */

public class TeacherController extends UserController {

    private TeacherDAO teacherDAO = new TeacherDAO();

    public int validateSignUp(Object teacherObject){

        Teacher teacher = (Teacher) teacherObject;

        String username = teacher.getUsername().trim();
        String email = teacher.getEmail().trim();
        String password = teacher.getPassword().trim();
        String name = teacher.getName().trim();
        String phone = teacher.getPhone().trim();
        String birthdate = teacher.getBirthdate().trim();
        String bio = teacher.getBio().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(birthdate) || TextUtils.isEmpty(bio)) {
            return -1;
        }
        //Chamar DAO para validar se email ja existe
        if (teacherDAO.validateUserInDatabase(teacher) == 0){
            return 0;
        }

        //Chamar DAO para cadastrar um novo
        if (teacherDAO.signUp(teacher) == 1){
            return 1;
        }
        return 0;
    }

    public Object validateReadUserInDatabase(String email){
        Teacher teacher;
        teacher = (Teacher) teacherDAO.readUserInDatabase(email);
        return teacher;
    }

    public void validateUpdate(Object teacherObject){
        Teacher teacher;
        teacher = (Teacher) teacherObject;
        teacherDAO.updateUserInDatabase(teacher);
    }
}
