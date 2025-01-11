package db;

import db.interfaces.IUniversityDatabase;
import exceptions.CourseNotFoundException;
import exceptions.StudentNotFoundException;
import models.Course;
import models.Student;

import java.sql.*;
import java.util.ArrayList;

public class UniversityDatabase implements IUniversityDatabase {
    private Connection con = null;

    @Override
    public void connect(String connectionUrl, String user, String pass) {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionUrl, user, pass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    @Override
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Database disconnection error: " + e.getMessage());
            }
        }
    }

    @Override
    public void addStudent(Student student) {
        try {
            String sql = "INSERT INTO students(name, age, student_id) VALUES(?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, student.getName());
            st.setInt(2, student.getAge());
            st.setInt(3, student.getStudent_id());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    @Override
    public void deleteStudentById(int id) {
        try {
            String sql = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    @Override
    public void addCourse(Course course) {
        try {
            String sql = "INSERT INTO courses(course_id, courseName) VALUES(?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, course.getCourse_id());
            st.setString(2, course.getCourseName());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    @Override
    public void deleteCourseById(int id) {
        try {
            String sql = "DELETE FROM courses WHERE course_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    @Override
    public Student getStudentById(int student_id) {
        try {
            String sql = "SELECT name, age, student_id FROM students WHERE student_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, student_id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");

                return new Student(name, age, student_id);
            } else {
                throw new StudentNotFoundException("Student with ID " + student_id + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Course getCourseById(int course_id) {
        try {
            String sql = "SELECT course_id, courseName FROM courses WHERE course_id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String courseName = rs.getString("courseName");

                return new Course(course_id, courseName);
            } else {
                throw new CourseNotFoundException("Course with ID " + course_id + " not found.");
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public void enrollStudentInCourse(int student_id, int course_id) {
        Student student = getStudentById(student_id);
        Course course = getCourseById(course_id);

        if (student != null && course != null) {
            student.enrollCourse(course);
            try {
                String sql = "INSERT INTO enrollments(student_id, course_id) VALUES (?, ?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setInt(1, student_id);
                st.setInt(2, course_id);
                st.executeUpdate();
                st.close();
            } catch (SQLException e) {
                System.out.println("SQL error: " + e.getMessage());
            }
        } else {
            System.out.println("Student or course not found.");
        }
    }

    @Override
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        try {
            String sql = "SELECT name, age, student_id FROM students";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int student_id = rs.getInt("student_id");
                Student student = new Student(name, age, student_id);
                students.add(student);
            }
            st.close();
            rs.close();

            return students;
        } catch(SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            String sql = "SELECT course_id, courseName FROM courses";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int course_id = rs.getInt("course_id");
                String courseName = rs.getString("courseName");
                Course course = new Course(course_id, courseName);
                courses.add(course);
            }
            st.close();
            rs.close();

            return courses;
        } catch(SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }

        return null;
    }
}
