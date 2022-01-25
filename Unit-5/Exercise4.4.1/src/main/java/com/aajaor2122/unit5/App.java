package com.aajaor2122.unit5;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
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
                    else if (consultOption == 2)
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
                    else if (insertOption == 2)
                        insertEmployee();
                    else
                        System.out.println("Option NOT valid. Incorrect option number.");

                    break;

                // Option to update existing entries in the database
                case 3:
                    int updateOption = 0;
                    System.out.println("1. Update an existing department: ");
                    System.out.println("2. Update an existing employee: ");
                    System.out.print("Select one option: ");
                    updateOption = Integer.parseInt(sc.nextLine());

                    if (updateOption == 1) {
                        System.out.print("Introduce the number of the department you would like to update: ");
                        int deptNum = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter the new name for the department: ");
                        String newDeptName = sc.nextLine();
                        System.out.print("Enter the new location for the department: ");
                        String newDeptLoc = sc.nextLine();
                        updateDepartment(deptNum, newDeptName, newDeptLoc);
                    }

                    if (updateOption == 2) {
                        System.out.print("Introduce the number of the employee you would like to update: ");
                        int empNum = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter the new name for the employee: ");
                        String newEmpName = sc.nextLine();
                        System.out.print("Enter the new job for the employee: ");
                        String newJob = sc.nextLine();
                        System.out.print("Enter the number of an existing department for this employee: ");
                        int deptNum = Integer.parseInt(sc.nextLine());

                        updateEmployee(empNum, newEmpName, newJob, deptNum);
                    }
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
                        department.getDeptno(), department.getDname(), department.getLoc(),
                        printEmployeesList(department.getEmployeesList()));
            }
        }
        catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to print the employee´s list in a String format
    public static String printEmployeesList(Set employeesList) {

        String employeeData = "";
        for(Object employee : employeesList) {
            employeeData += ((EmployeeEntity) employee).getEmpno() + ", ";
            employeeData += ((EmployeeEntity) employee).getEname() + ", ";
            employeeData += ((EmployeeEntity) employee).getJob() + ", ";
            employeeData += ((EmployeeEntity) employee).getDepartment().getDname() + " | ";
        }

        return employeeData;
    }

    // Method to print all the data from Employee entities
    public static void consultEmployeesData() {
        try ( Session session = openSession() ) {
            Query<EmployeeEntity> myQuery =
                    session.createQuery("from com.aajaor2122.unit5.EmployeeEntity");
            List<EmployeeEntity> employees = myQuery.list();

            for (Object emplObject : employees) {
                EmployeeEntity employee = (EmployeeEntity) emplObject;
                // We create an instance of Department class to be able to access its properties
                DeptEntity department = employee.getDepartment();
                System.out.printf("Number: %d   Name: %s   Job: %s   Department: %s%n",
                        employee.getEmpno(), employee.getEname(), employee.getJob(), department.getDname());
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
        System.out.print("Number of the new department? ");
        int dnumber = Integer.parseInt(sc.nextLine());
        System.out.print("Name of department?: ");
        String dname = sc.nextLine();
        System.out.print("Department´s location?: ");
        String dlocation = sc.nextLine();
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            DeptEntity department = new DeptEntity();
            department.setDeptno(dnumber);
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
        System.out.println("Number of the new employee? ");
        int enumber = Integer.parseInt(sc.nextLine());
        System.out.print("Name of the employee?: ");
        String ename = sc.nextLine();
        System.out.print("Employee´s job?: ");
        String ejob = sc.nextLine();
        try (Session session = openSession()) {
            Transaction transaction = session.beginTransaction();
            EmployeeEntity employee = new EmployeeEntity();
            employee.setEmpno(enumber);
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

    public static void updateDepartment(int deptNumber, String newName, String newLoc) {
        try (Session session = openSession()) {
            Query<DeptEntity> deptQuery =
                    session.createQuery("from com.aajaor2122.unit5.DeptEntity where deptno='" +
                            String.valueOf(deptNumber) + "' ");
            List<DeptEntity> departments = deptQuery.list();
            Transaction transaction = session.beginTransaction();
            DeptEntity department = (DeptEntity) departments.get(0);
            department.setDname(newName);
            department.setLoc(newLoc);
            session.update(department);
            transaction.commit();

        } catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateEmployee(int empNumber, String newName, String newJob, int newDepart) {
        try (Session session = openSession()) {
            // We obtain the employee instance to be modified
            Query<EmployeeEntity> employeeQuery =
                    session.createQuery("from com.aajaor2122.unit5.EmployeeEntity where empno='" +
                            String.valueOf(empNumber) + "' ");
            List<EmployeeEntity> employees = employeeQuery.list();
            // We obtain also the department instance, to change the Department field in the employee
            Query<DeptEntity> deptQuery =
                    session.createQuery("from com.aajaor2122.unit5.DeptEntity where deptno='" +
                            String.valueOf(newDepart) + "' ");
            List<DeptEntity> departments = deptQuery.list();
            Transaction transaction = session.beginTransaction();
            EmployeeEntity employee = (EmployeeEntity) employees.get(0);
            DeptEntity newDepartment = (DeptEntity) departments.get(0);
            employee.setEname(newName);
            employee.setJob(newJob);
            employee.setDepartment(newDepartment);
            session.update(employee);
            transaction.commit();

        } catch (HibernateException hiex) {
            System.err.println( hiex.getMessage() );
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
