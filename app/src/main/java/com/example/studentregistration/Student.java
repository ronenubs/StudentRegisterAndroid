package com.example.studentregistration;

public class Student {

    private int studentID;
    private String firstname;
    private String lastname;
    private String middlename;
    private String address;
    private int age;

    //display students
    public Student(int studentID, String firstname, String lastname, String middlename, String address, int age) {
        this.studentID = studentID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.address = address;
        this.age = age;
    }

    public Student(String firstname, String lastname, String middlename, String address, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.address = address;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public int getStudentID() {
        return studentID;
    }
}
