package org.binaracademy.challenge_4.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.annotation.PostConstruct;

@Controller
public class BinarFudController {
    private boolean ACTIVE = true;

    @PostConstruct
    public void init(){
        authencticate();
    }

    @Autowired
    private UserController userController;
    @Autowired
    private MerchantController merchantController;


    private void authencticate(){
        while (ACTIVE){
            if (userController.sessionUser != null){
                merchantController.menuAllMerchantView(userController.sessionUser);
            }else {
                userController.authenticateView();
            }
        }
    }
}
