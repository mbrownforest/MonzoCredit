package com.example.tescotomonzo;

import java.io.Serializable;

public class AccessToken implements Serializable {

    private String accessToken;
    private boolean isAuthorised;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;

    public AccessToken(){
    }

    public Boolean getAuthorised() {
        return isAuthorised;
    }

    public void setAuthorised(Boolean authorised) {
        isAuthorised = authorised;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAccessToken(){
        return this.accessToken;
    }


}
