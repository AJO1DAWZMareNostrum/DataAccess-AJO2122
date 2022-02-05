package u3.aajaor2122.com;

import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

/**
 *  CONTROLLER class - related to every aspect of the graphical interface: controls and set up
 *  every element of the program and relates it to the Database methods in the program
 *
 * @author Aarón Jamet Orgiles - 2DAMU
 */
public class MainForm {
    private JPanel mainFormPanel;
    private JTabbedPane Enrollment;
    private JPanel Students;
    private JPanel Enrollments;
    private JLabel labelFirstName;
    private JTextField textFirstName;
    private JLabel labelLastName;
    private JTextField textIDCard;
    private JTextField textLastName;
    private JTextField textEmail;
    private JTextField textPhone;
    private JLabel labelIDCard;
    private JLabel labelEmail;
    private JLabel labelPhone;
    private JButton buttonAdd;
    private JComboBox comboBoxStudent;
    private JComboBox comboBoxCourse;
    private JButton buttonEnroll;
    private JComboBox cbStudentReports;
    private JTextArea textScoresResult;
    private JButton buttonPrint;
    private JButton buttonImportXML;
    private JLabel labelResult;
    private JPanel Reports;
    private JPanel Utilities;
    private JTextField txtStudentIDSearch;
    private JButton buttonSearchById;
    private JTextField txtStudentFullName;

    /**
     *   Constructor for the graphical interface or GUI
      */
    public MainForm() {

        setUpStudentsUI();
        setUpEnrollmentsUI();
        setUpStudentReportsUI();

        /**
         *  Listener for the button that allows to enroll new Students from
         *  the Enrollments section
         */
        buttonEnroll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentId = (Integer)comboBoxStudent.getSelectedItem();
                    int courseId = ((Course)comboBoxCourse.getSelectedItem()).getCode();

                    MyVTInstituteDB.insertEnrollment(studentId, courseId);
                    resultSuccess("Student has been enrolled into a new course.");
                } catch (Exception ex) {
                    reportError(ex);
                }
            }
        });

        /**
         *  Listener for the button that allows to print specific data about Students from
         *  the Reports section, using the id of the Student as a reference
         */
        buttonPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentId = (Integer)cbStudentReports.getSelectedItem();
                    String textResult = MyVTInstituteDB.printStudentString(studentId);

                    textScoresResult.setText(textResult);

                    // Llamar a JFILECHOOSER para elegir ruta donde guardar fichero
                    int msg = JOptionPane.showConfirmDialog(null, "Do you want to print this data in a text file?",
                                                    "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (msg == JOptionPane.YES_OPTION){
                        // We save the student data selected into a text file, choosing the path first
                        JFileChooser fs = new JFileChooser(new File("c:\\"));
                        fs.setDialogTitle("Save data in a file");
                        int saveResult = fs.showSaveDialog(null);
                        if (saveResult == JFileChooser.APPROVE_OPTION) {
                            File file = fs.getSelectedFile();
                            // SE IMPRIME correctamente - abrir con Notepad++
                            try {
                                FileWriter fw = new FileWriter(file.getPath());
                                fw.write(textScoresResult.getText());
                                fw.flush();
                                fw.close();
                            }
                            catch (Exception ex) {
                                reportError(ex);
                            }
                        }
                    }

                } catch (Exception ex) {
                    reportError(ex);
                }

            }
        });

        /**
         *  Listener for the button that allows to import and parse an XML file, retrieve from
         *  it some data, and then insert it with transaction mode into the database
         */
        buttonImportXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int msg = JOptionPane.showConfirmDialog(null, "Do you want to import and insert a XML file?",
                        "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (msg == JOptionPane.YES_OPTION){
                    // We save the student data selected into a text file, choosing the path first
                    JFileChooser fs = new JFileChooser(new File("c:\\"));
                    fs.setDialogTitle("Save data in a file");
                    int saveResult = fs.showSaveDialog(null);
                    if (saveResult == JFileChooser.APPROVE_OPTION) {
                        File file = fs.getSelectedFile();
                        try {
                            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
                            XMLparser xmlParser = new XMLparser();
                            saxParser.parse(file, xmlParser);

                            MyVTInstituteDB.importXMLfile(xmlParser.getStudents(), xmlParser.getCourses(),
                                    xmlParser.getSubjects());

                        } catch (Exception ex) {
                            reportError(ex);
                        }
                    }
                }
            }
        });

        /**
         *  Listener for the button that search for the user full name (first + last) given
         *  an student´s id
         */
        buttonSearchById.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int studentId = Integer.valueOf(txtStudentIDSearch.getText());
                    String fullName = MyVTInstituteDB.showStudentName(studentId);

                    txtStudentFullName.setText(fullName);

                } catch (Exception ex) {
                    reportError(ex);
                }

            }
        });
    }

    /**
     *  Method for setting up, configure, and listen to: the students section of the program
     */
    public void setUpStudentsUI() {
        // Add button listener; inserts new students into the database
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!textFirstName.getText().isEmpty() && !textLastName.getText().isEmpty() &&
                            !textIDCard.getText().isEmpty()) {
                        String name = textFirstName.getText();
                        String surname = textLastName.getText();
                        int id = Integer.valueOf(textIDCard.getText());

                        String email = textEmail.getText();
                        try {
                            int containsAt = email.indexOf('@');
                            int emailIndexIsValid = email.indexOf(containsAt, '.');

                            // If there is more than one character bewtween '@' and '.'
                            if (emailIndexIsValid - containsAt <= 1) {
                                labelResult.setText("El número debe contener 9 dígitos");
                            }
                        } catch (Exception ex) {
                            reportError(ex);
                        }


                        String phone = textPhone.getText();
                        try {
                            if (phone.length() != 9 ) {
                                labelResult.setText("Number must contain 9 digits");
                            }
                        } catch (Exception ex) {
                            reportError(ex);
                        }

                        MyVTInstituteDB.insertStudent(name, surname, id, email, phone);

                        setUpEnrollmentsUI();
                        setUpStudentReportsUI();
                        resultSuccess("Student inserted into the DB");
                        // refreshStudentBox();
                    } else {
                        labelResult.setText("Name, surname, and Id are REQUIRED");
                    }
                } catch (Exception ex) {
                    reportError(ex);
                }
            }
        });
    }

    /**
     *  Method for setting up, configure, and listen to: the Enrollments section of the program
     */
    public void setUpEnrollmentsUI() {
        try {
            comboBoxStudent.removeAllItems();
            comboBoxCourse.removeAllItems();

            List<Integer> allStudentsIds = MyVTInstituteDB.getAllStudentIds();
            for (Integer id: allStudentsIds) {
                comboBoxStudent.addItem(id);
            }

            List<Course> allCourses = MyVTInstituteDB.getAllCourses();
            for (Course course: allCourses) {
                comboBoxCourse.addItem(course);
            }

        } catch (Exception ex) {
            reportError(ex);
        }
    }

    /**
     *  Method for setting up, configure, and listen to: the Reports section of the program
     */
    public void setUpStudentReportsUI() {
        try {
            cbStudentReports.removeAllItems();

            List<Integer> allStudentsIds = MyVTInstituteDB.getAllStudentIds();
            for (Integer id: allStudentsIds) {
                cbStudentReports.addItem(id);
            }
        } catch (Exception ex) {
            reportError(ex);
        }
    }

    /**
     *  Method that receives an exception from some part of the program, and allows to
     *  show it into a MessageDialog to the program´s user
     *
     * @param ex  the exception received by the program and showed in a MessageDialog
     */
    public void reportError(Exception ex) {
        JOptionPane.showMessageDialog(null, ex);

        ex.printStackTrace();
    }

    //TODO: activar Hibernate
    //public void reportError(HibernateException hex) {
    //    JOptionPane.showMessageDialog(null, hex);

    //   hex.printStackTrace();
    //}

    /**
     *  Method that receives an success message from some part of the program, and allows to
     *  show it into a MessageDialog to the program´s user
     *
     * @param message  the message received by the program and showed in a MessageDialog
     */
    public void resultSuccess(String message) {

        JOptionPane.showMessageDialog(null, message);
    }

    /**
     *  Starting point of the program, that launches the interface and allows the user to interact with it
     *
     * @param args  default parameter´s array of strings of the program
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("VTInstitute-AJO2122");
        frame.setContentPane(new MainForm().mainFormPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(420, 420);
        frame.setVisible(true);
    }

}
