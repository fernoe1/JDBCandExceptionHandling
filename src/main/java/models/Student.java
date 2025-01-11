package models;

import java.util.ArrayList;

public class Student extends Person {
    private int student_id;
    private ArrayList<Course> enrolledCourses;

    public Student(String name, int age, int student_id) {
        super(name, age);
        this.student_id = student_id;
        this.enrolledCourses = new ArrayList<>();
    };

    public int getStudent_id() {

        return student_id;
    }

    public ArrayList<Course> getEnrolledCourses() {

        return enrolledCourses;
    }

    public void enrollCourse(Course course) {

        enrolledCourses.add(course);
    }

    @Override
    public void displayInfo() {
        System.out.println("Student with name " + super.name
                + " age " + super.age
                + " and a ID of " +  student_id);
    }
}
