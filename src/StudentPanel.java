import javax.swing.*;
import java.awt.*;

public class StudentPanel extends JPanel {

    private JTextField txtTitle;
    private JTextArea txtAbstract;
    private JTextField txtSupervisor;
    private JComboBox<PresentationType> cmbType;
    private JTextField txtFilePath;

    public StudentPanel(Student student) {

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtTitle = new JTextField(20);
        txtAbstract = new JTextArea(4, 20);
        txtSupervisor = new JTextField(20);
        cmbType = new JComboBox<>(PresentationType.values());
        txtFilePath = new JTextField(20);

        JButton btnSubmit = new JButton("Submit");

        btnSubmit.addActionListener(e -> {
            Submission submission = new Submission(
                    txtTitle.getText(),
                    txtAbstract.getText(),
                    txtSupervisor.getText(),
                    (PresentationType) cmbType.getSelectedItem(),
                    txtFilePath.getText()
            );

            student.setSubmission(submission);

            JOptionPane.showMessageDialog(this,
                    "Seminar registration submitted successfully!");
        });

        int row = 0;

        addRow("Title:", txtTitle, gbc, row++);
        addRow("Abstract:", new JScrollPane(txtAbstract), gbc, row++);
        addRow("Supervisor:", txtSupervisor, gbc, row++);
        addRow("Presentation Type:", cmbType, gbc, row++);
        addRow("File Path:", txtFilePath, gbc, row++);

        gbc.gridx = 1;
        gbc.gridy = row;
        add(btnSubmit, gbc);
    }

    private void addRow(String label, Component field,
                        GridBagConstraints gbc, int row) {

        gbc.gridy = row;

        gbc.gridx = 0;
        gbc.weightx = 0;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        add(field, gbc);
    }
}
