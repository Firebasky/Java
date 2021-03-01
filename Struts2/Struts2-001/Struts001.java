package com.vulhub.struts;

import com.opensymphony.xwork2.ActionSupport;

public class Struts001 extends ActionSupport {
    private  String username;
    private  String password;


    @Override
    public String execute() throws Exception {
        if ((this.username.isEmpty()) || (this.password.isEmpty())) {
            return "error";
        }
        if ((this.username.equalsIgnoreCase("admin"))
                && (this.password.equals("admin"))
                ) {
            return "success";
        }
        return "error";

    }
    public void setPassword(String password) {
        this.password = "%{1+1}";
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
