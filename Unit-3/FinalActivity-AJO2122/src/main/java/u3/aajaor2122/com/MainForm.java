package u3.aajaor2122.com;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 *  CONTROLLER class - related to every aspect of the graphical interface
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
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton buttonEnroll;
    private JComboBox cbStudentReports;
    private JTextArea textScoresResult;
    private JButton buttonPrint;
    private JButton buttonImportXML;
    private JLabel labelResult;

    // Constructor for the graphical interface or GUI
    public MainForm() {
        // refreshBoxes();

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
                        String phone = textPhone.getText();

                        new MyVTInstituteDB().insertStudent(name, surname, id, email, phone);
                        labelResult.setText("Student inserted into the DB");
                        // refreshStudentBox();
                    } else {
                        labelResult.setText("Name, surname, and Id are REQUIRED");
                    }
                } catch (SQLException ex) {
                    labelResult.setText("Some SQL error has ocurred");
                    ex.printStackTrace();
                }
            }
        });



    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VTInstitute-AJO2122");
        frame.setContentPane(new MainForm().mainFormPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(380, 420);
        frame.setVisible(true);
    }

}
