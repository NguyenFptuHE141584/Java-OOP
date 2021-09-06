/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P0021.ManagementStudent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author ADMIN
 */
public class Manager {

    Validation validation = new Validation();

    //Show menu
    public void menu() {
        System.out.println(" 1.	Create");
        System.out.println(" 2.	Find and Sort");
        System.out.println(" 3.	Update/Delete");
        System.out.println(" 4.	Report");
        System.out.println(" 5.	Exit");
        System.out.print(" Enter your choice: ");
    }

    //Allow user create new student
    public void createStudent(int count, ArrayList<Student> ls) {
        //if number of students greater than 10 ask user continue or not
        if (count > 10) {
            System.out.print("Do you want to continue (Y/N): ");
            if (!validation.checkInputYN()) {
                return;
            }
        }
        //loop until user input not duplicate
        while (true) {
            System.out.print("Enter id: ");
            String id = validation.checkInputString();
            String name="";
           if (!Validation.checkIdExist(ls, id)){
               name = getNameById(ls, id);
               System.out.println("Name student: "+name);
           }else{
            System.out.print("Enter name student: ");
             name = validation.checkInputString();
           }
            System.out.print("Enter semester: ");
            String semester = validation.checkInputString();
            System.out.print("Enter name course: ");
            String course = validation.checkInputCourse();
            //check student exist or not
            if (Validation.checkStudentExist(ls, id, name, semester, course)) {
                ls.add(new Student(id, name, semester, course));
                count++;
                System.err.println("Add student success.");
                return;
            }
            System.err.println("Duplicate.");
        }
    }
    
    public String getNameById(ArrayList<Student>ls,String id){
        for (Student o : ls) {
            if (o.getId().equals(id)){
                return o.getStudentName();
            }
        }
        return null;
    }
    //Allow user create find and sort

    public void findAndSort(ArrayList<Student> ls) {
        //check list empty 
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        ArrayList<Student> listStudentFindByName = listStudentFindByName(ls);
        if (listStudentFindByName.isEmpty()) {
            System.err.println("Not exist.");
        } else {
            Collections.sort(listStudentFindByName, new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.getStudentName().compareToIgnoreCase(o2.getStudentName());
                }

            });
            System.out.printf("%-15s%-15s%-15s\n", "Student name", "semester", "Course Name");
            //loop from first to last element of list student
            for (Student student : listStudentFindByName) {
                student.print();
            }
        }
    }

    //List student found by name
    public ArrayList<Student> listStudentFindByName(ArrayList<Student> ls) {
        ArrayList<Student> listStudentFindByName = new ArrayList<>();
        System.out.print("Enter name to search: ");
        String name = validation.checkInputString();
        for (Student student : ls) {
            //check student have name contains input
            if (student.getStudentName().contains(name)) {
                listStudentFindByName.add(student);
            }
        }
        return listStudentFindByName;
    }

    //Allow user update and delete   
    public void updateOrDelete(int count, ArrayList<Student> ls) {
        //if list empty 
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.print("Enter id: ");
        String id = validation.checkInputString();
        ArrayList<Student> listStudentFindByName = getListStudentById(ls, id);
        //check list empty
        if (listStudentFindByName.isEmpty()) {
            System.err.println("Not found student.");
            return;
        } else {
            Student student = getStudentWantToUD(listStudentFindByName);
            System.out.print("Do you want to update (U) or delete (D) student: ");
            //check user want to update or delete
            if (validation.checkInputUD()) {
                System.out.print("Enter id: ");
                String idStudent = validation.checkInputString();
                System.out.print("Enter name student: ");
                String name = validation.checkInputString();
                System.out.print("Enter semester: ");
                String semester = validation.checkInputString();
                System.out.print("Enter name course: ");
                String course = validation.checkInputCourse();    
                //check student exist or not
                if (Validation.checkStudentExist(ls, id, name, semester, course)) {
                    student.setId(idStudent);
                    student.setStudentName(name);
                    student.setSemester(semester);
                    student.setCourseName(course);
                    System.err.println("Update success.");
                }
                return;
            } else {
                ls.remove(student);
                count--;
                System.err.println("Delete success.");
                return;
            }
        }
    }

    //Get student user want to update/delete in list found
    public Student getStudentWantToUD(ArrayList<Student> listStudentFindByName) {
        System.out.println("List student found: ");
        int count = 1;
        System.out.printf("%-10s%-15s%-15s%-15s\n", "Number", "Student name",
                "semester", "Course Name");
        //display list student found
        for (Student student : listStudentFindByName) {
            System.out.printf("%-10d%-15s%-15s%-15s\n", count,
                    student.getStudentName(), student.getSemester(),
                    student.getCourseName());
            count++;
        }
        System.out.print("Enter student: ");
        int choice = validation.checkInputIntLimit(1, listStudentFindByName.size());
        return listStudentFindByName.get(choice - 1);
    }

    //Get list student find by id
    public ArrayList<Student> getListStudentById(ArrayList<Student> ls, String id) {
        ArrayList<Student> getListStudentById = new ArrayList<>();
        for (Student student : ls) {
            if (id.equalsIgnoreCase(student.getId())) {
                getListStudentById.add(student);
            }
        }
        return getListStudentById;
    }
//Print report

    public void report(ArrayList<Student> ls) {
        if (ls.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        ArrayList<Report> lr = new ArrayList<>();
        int size = ls.size();

        for(Student student : ls) {
            int total = 0;
            String id = student.getId();
            String courseName = student.getCourseName();
            String studentName = student.getStudentName();
            for (Student studentCountTotal : ls) {
                if (id.equalsIgnoreCase(studentCountTotal.getId())
                        && courseName.equalsIgnoreCase(studentCountTotal.getCourseName())) {
                    total++;
                }
            }
            if (validation.checkReportExist(lr, studentName,
                    courseName, total)) {
                lr.add(new Report(student.getStudentName(), courseName, total));
            }
        }
        //print report
        for (int i = 0; i < lr.size(); i++) {
            System.out.printf("%-15s|%-10s|%-5d\n", lr.get(i).getStudentName(),
                    lr.get(i).getCourseName(), lr.get(i).getTotalCourse());
        }
    }
}
