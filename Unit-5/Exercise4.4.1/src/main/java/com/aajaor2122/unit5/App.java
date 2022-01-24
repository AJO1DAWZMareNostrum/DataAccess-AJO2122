package com.aajaor2122.unit5;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

public class App 
{
    static Scanner sc = new Scanner(System.in);

    public static void main( String[] args )
    {
        //Scanner sc = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            System.out.println("1. Consult entries.");
            System.out.println("2. Create/insert entry.");
            System.out.println("3. Update entry.");
            System.out.println("4. Delete entry");
            System.out.print("Choose one option, or 0 to leave program: ");
            option = Integer.parseInt(sc.nextLine());

            switch (option) {
                // Option to consult the entries in the database
                case 1:
                    int consultOption = 0;
                    System.out.println("1. Consult departments data.");
                    System.out.println("2. Consult employees data.");
                    System.out.print("Select one option: ");
                    consultOption = Integer.parseInt(sc.nextLine());

                    if (consultOption == 1)
                        consultDeptData();
                    if (consultOption == 2)
                        consultEmployeesData();
                    else
                        System.out.println("Option NOT valid. Incorrect option number.");

                    break;

                // Option to create/insert new entries in the database
                case 2:
                    int insertOption = 0;
                    System.out.println("1. Insert a new department.");
                    System.out.println("2. Insert a new employee.");
                    System.out.print("Select one option: ");
                    insertOption = Integer.parseInt(sc.nextLine());

                    if (insertOption == 1)
                        insertDepartment();
                    if (insertOption == 2)
                        insertEmployee();

                    else
                        System.out.println("Option NOT valid. Incorrect option number.");

                    break;

                // Option to update existing entries in the database
                case 3:
                    int updateOption = 0;
                    System.out.print("1. Update an existing department: ");
                    System.out.println("2. Update an existing employee: ");
            }
        }

    }

    // Creating and opening the session
    public static Session openSession() throws HibernateException {
        // Code to avoid the warnings
        @SuppressWarnings("unused")
        org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
        java.util.logging.Logger.getLogger("org.hibernate") .setLevel(Level.SEVERE);

        SessionFactory sessionFactory = new
                Configuration().configure().buildSessionFactory();
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch (Throwable t) {
            System.err.println("Exception while opening session...");
            t.printStackTrace();
        }
        if (session == null) {
            System.err.println("Session was found to be null.");
        }

        return session;
    }

    // Method to print all the data from Department entities
    public static void consultDeptData() {
        try ( Session session = openSession() ) {
            Query<DeptEntity> myQuery =
                    session.createQuery("from com.aajaor2122.unit5.DeptEntity");
            List<DeptEntity> departments = myQuery.list();

            for (Object deptObject : departments) {
                DeptEntity department = (DeptEntity) deptObject;
                System.out.printf("Number: %d   Name: %s   Location: %s%nEmployees: %s%n%n",
                        department.getDeptno(), department.getDname(), department.getLoc(), department.getEmployeesList());
            }
        }
        catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to print all the data from Employee entities
    public static void consultEmployeesData() {
        try ( Session session = openSession() ) {
            Query<EmployeeEntity> myQuery =
                    session.createQuery("from com.aajaor2122.unit5.EmployeeEntity");
            List<EmployeeEntity> employees = myQuery.list();

            for (Object emplObject : employees) {
                EmployeeEntity employee = (EmployeeEntity) emplObject;
                System.out.printf("Number: %d   Name: %s   Job: %s   Department: %s%n",
                        employee.getEmpno(), employee.getEname(), employee.getJob(), employee.getDepartment());
            }
        }
        catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertDepartment() {
        System.out.print("Name of department?: ");
        String dname = sc.nextLine();
        System.out.print("Department´s location?: ");
        String dlocation = sc.nextLine();
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            DeptEntity department = new DeptEntity();
            department.setDname(dname);
            department.setLoc(dlocation);
            session.save(department);
            transaction.commit();
        }
        catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertEmployee() {
        System.out.print("Name of the employee?: ");
        String ename = sc.nextLine();
        System.out.print("Employee´s job?: ");
        String ejob = sc.nextLine();
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            EmployeeEntity employee = new EmployeeEntity();
            employee.setEname(ename);
            employee.setJob(ejob);
            session.save(employee);
            transaction.commit();
        }
        catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
