package ua.com.webacademy.beginners.lection10;

public class Student {
    public long id;

    public String FirstName;
    public String LastName;
    public long Age;

    public Student() {
    }

    public Student(String firstName, String lastName, int age) {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Age = age;
    }
}
