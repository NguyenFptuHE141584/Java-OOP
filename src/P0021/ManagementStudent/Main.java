/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P0021.ManagementStudent;

import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<Student> ls = new ArrayList<>();
        Validation validation = new Validation();
        Manager manager = new Manager();
        ls.add(new Student("1", "Pham Ngoc Hoa", "Spring2021", "java"));
        ls.add(new Student("2", "Do Quang Hiep", "Summer2021", ".net"));
        ls.add(new Student("3", "Nguyen Xuan Cuong", "Spring2021", "c/c++"));
        int count = 3;
        //loop until user want to exit program
        while (true) {
            //Show menu option
            manager.menu();
            int choice = validation.checkInputIntLimit(1, 5);
            switch (choice) {
                case 1:
                    manager.createStudent(++count, ls);
                    break;
                case 2:
                    manager.findAndSort(ls);
                    break;
                case 3:
                    manager.updateOrDelete(count, ls);
                    break;
                case 4:
                    manager.report(ls);
                    break;
                case 5:
                    return;
            }

        }
    }

}
