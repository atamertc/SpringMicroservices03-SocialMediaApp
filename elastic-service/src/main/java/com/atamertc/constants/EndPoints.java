package com.atamertc.constants;

public class EndPoints {
    public static final String VERSION = "/api/v1";
    public static final String ELASTIC = "/elastic";
    public static final String USER = VERSION + ELASTIC + "/user";


    //Genel
    public static final String FINDALL = "/findall";
    public static final String DELETEBYID = "/deletebyid/{id}";
    public static final String SAVE = "/save";
    public static final String UPDATE = "/update";

    // Auth
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String ACTIVATION = "/activation";


}
