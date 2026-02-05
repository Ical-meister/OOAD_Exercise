import javax.swing.*;
import java.awt.*;

class CreateSessionDialog extends JDialog {

    public CreateSessionDialog(JFrame parent) {
        super(parent, "Create Seminar Session", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JTextField txtDate = new JTextField();
        JTextField txtVenue = new JTextField();
        JComboBox<String> cmbType =
                new JComboBox<>(new String[]{"Oral", "Poster"});

        JButton btnSave = new JButton("Save Session");

        btnSave.addActionListener(e -> {
            Session session = new Session(
                    txtDate.getText(),
                    txtVenue.getText(),
                    cmbType.getSelectedItem().toString()
            );

            SessionManager.sessions.add(session);

            JOptionPane.showMessageDialog(this,
                    "Session created successfully!");
            dispose();
        });

        setLayout(new GridLayout(0, 1, 8, 8));
        add(new JLabel("Date:"));
        add(txtDate);
        add(new JLabel("Venue:"));
        add(txtVenue);
        add(new JLabel("Session Type:"));
        add(cmbType);
        add(btnSave);

        setVisible(true);
    }
}
