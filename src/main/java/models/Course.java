package models;

public class Course {
    private int course_id;
    private String courseName;

    public Course(int course_id, String courseName) {
        setCourse_id(course_id);
        setCourseName(courseName);
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourse_id() {

        return course_id;
    }

    public String getCourseName() {

        return courseName;
    }

    public void displayInfo() {
        System.out.println("Course name: " + courseName + " course id: " + course_id);
    }
}
