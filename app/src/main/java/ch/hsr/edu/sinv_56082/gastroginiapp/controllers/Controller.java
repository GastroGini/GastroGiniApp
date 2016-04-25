package ch.hsr.edu.sinv_56082.gastroginiapp.controllers;

import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;

public class Controller {
    protected App app;

    protected Controller(){
        app = App.getApp();
    }


}