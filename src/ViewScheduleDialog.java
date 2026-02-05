import javax.swing.*;

class ViewScheduleDialog extends JDialog {

    public ViewScheduleDialog(JFrame parent) {
        super(parent, "Seminar Schedule", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        if (SessionManager.sessions.isEmpty()) {
            area.setText("No sessions created yet.");
        } else {
            for (Session s : SessionManager.sessions) {
                area.append(
                        "Date: " + s.getDate() +
                        "\nVenue: " + s.getVenue() +
                        "\nType: " + s.getSessionType() +
                        "\n----------------------\n"
                );
            }
        }

        add(new JScrollPane(area));
        setVisible(true);
    }
}
