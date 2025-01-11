package db.interfaces;

import models.Student;
import models.Course;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUniversityDatabase {
    void connect(String connectionUrl, String user, String pass) throws SQLException, ClassNotFoundException;

    void disconnect() throws SQLException;

    void addStudent(Student student) throws SQLException;

    void deleteStudentById(int id) throws SQLException;

    void addCourse(Course course) throws SQLException;

    void deleteCourseById(int id) throws SQLException;

    Student getStudentById(int student_id) throws SQLException;

    Course getCourseById(int course_id) throws SQLException;

    void enrollStudentInCourse(int student_id, int course_id) throws SQLException;

    ArrayList<Student> getAllStudents() throws SQLException;

    ArrayList<Course> getAllCourses() throws SQLException;
}
