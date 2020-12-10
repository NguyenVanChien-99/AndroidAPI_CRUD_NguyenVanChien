package com.example.apiproject;

public class Student {
    private String id;
    private String name;
    private String gender;
    private String className;
    private int age;

    public Student(String id, String name, String gender, String className, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.className = className;
        this.age = age;
    }

    public Student(String name, String gender, String className, int age) {
        this.name = name;
        this.gender = gender;
        this.className = className;
        this.age = age;
    }

    public Student(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", className='" + className + '\'' +
                ", age=" + age +
                '}';
    }
}
