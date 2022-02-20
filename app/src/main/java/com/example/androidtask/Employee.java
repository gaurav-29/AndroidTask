package com.example.androidtask;

public class Employee {

    private int id;
    private String employeeemail;

    public Employee(int id, String employeeemail) {
        this.id = id;
        this.employeeemail = employeeemail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeemail() {
        return employeeemail;
    }

    public void setEmployeeemail(String employeeemail) {
        this.employeeemail = employeeemail;
    }
}
