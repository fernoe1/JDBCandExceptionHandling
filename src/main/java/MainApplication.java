import db.UniversityDatabase;
import models.Course;
import models.Student;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        UniversityDatabase db = new UniversityDatabase();
        Scanner sc = new Scanner(System.in);
        db.connect("jdbc:postgresql://localhost:1987/localproject1", "postgres", "130924");
        System.out.println("Welcome!");
        Boolean flag = true;
        while(flag) {
            try {
                System.out.println("[1] View all students");
                System.out.println("[2] View all courses");
                System.out.println("[3] Add student");
                System.out.println("[4] Add course");
                System.out.println("[5] Delete student by ID");
                System.out.println("[6] Delete course by ID");
                System.out.println("[7] Get student by ID");
                System.out.println("[8] Get course by ID");
                System.out.println("[9] Enroll student to course by ID");
                System.out.println("[0] Exit");

                int choice = sc.nextInt();
                switch(choice) {
                    case 1:
                        ArrayList<Student> students = db.getAllStudents();
                        for (Student student : students) {
                            student.displayInfo();
                        }
                        break;
                    case 2:
                        ArrayList<Course> courses = db.getAllCourses();
                        for (Course course : courses) {
                            course.displayInfo();
                        }
                        break;
                    case 3:
                        System.out.println("Enter student name.");
                        String studentName = sc.next();
                        System.out.println("Enter student age.");
                        int age = sc.nextInt();
                        System.out.println("Enter student ID.");
                        int student_id = sc.nextInt();
                        Student student = new Student(studentName, age, student_id);
                        db.addStudent(student);
                        break;
                    case 4:
                        System.out.println("Enter course name.");
                        sc.nextLine();
                        String courseName = sc.nextLine();
                        System.out.println("Enter course ID.");
                        int course_id = sc.nextInt();
                        Course course = new Course(course_id, courseName);
                        db.addCourse(course);
                        break;
                    case 5:
                        System.out.println("Enter ID.");
                        db.deleteStudentById(sc.nextInt());
                        break;
                    case 6:
                        System.out.println("Enter ID.");
                        db.deleteCourseById(sc.nextInt());
                        break;
                    case 7:
                        System.out.println("Enter ID.");
                        db.getStudentById(sc.nextInt()).displayInfo();
                        break;
                    case 8:
                        System.out.println("Enter ID.");
                        db.getCourseById(sc.nextInt()).displayInfo();
                        break;
                    case 9:
                        System.out.println("Enter Student ID then Course ID.");
                        db.enrollStudentInCourse(sc.nextInt(), sc.nextInt());
                        break;
                    case 0:
                        flag = false;
                        break;
                    default:
                        System.out.println("Please select option between 0-9.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input must be integer " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception " + e.getMessage());
            }
        }
    }
}
