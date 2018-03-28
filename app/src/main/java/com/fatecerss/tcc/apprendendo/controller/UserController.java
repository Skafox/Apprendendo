package com.fatecerss.tcc.apprendendo.controller;

import com.fatecerss.tcc.apprendendo.model.Learner;

/**
 * Created by Sandro on 24/03/2018.
 */

abstract class UserController {

    abstract int validateSignUp(Learner learner);

    abstract Object validateReadUserInDatabase(String email);

    abstract void validateUpdate(Object obj);
}
