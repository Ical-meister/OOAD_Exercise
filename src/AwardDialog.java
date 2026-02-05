import javax.swing.*;

class AwardDialog extends JDialog {

    public AwardDialog(JFrame parent) {
        super(parent, "Awards", true);
        setSize(350, 200);
        setLocationRelativeTo(parent);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        area.setText(
                "Best Oral Presentation: TBD\n" +
                "Best Poster Presentation: TBD\n" +
                "People's Choice Award: TBD\n\n" +
                "(Awards computed by coordinator)"
        );

        add(area);
        setVisible(true);
    }
}
