package com.example.Joud;

public class Model
{
  String fname,lname,email,phone;
    Model()
    {

    }

    public Model(String fname, String lname, String email, String phone) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
