import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/* ===================== LOGIN FRAME ===================== */

public class LoginFrame extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JLabel lblStatus;

    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Seminar Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 230);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtEmail = new JTextField(20);
        txtPassword = new JPasswordField(20);
        lblStatus = new JLabel(" ");
        lblStatus.setForeground(Color.RED);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> handleLogin());
        getRootPane().setDefaultButton(btnLogin);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Email / Username:"), gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(lblStatus, gbc);

        gbc.gridy = 3;
        panel.add(btnLogin, gbc);

        setContentPane(panel);
    }

    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            lblStatus.setText("Please enter email and password.");
            return;
        }

        Optional<User> userOpt = authService.login(email, password);
        if (userOpt.isEmpty()) {
            lblStatus.setText("Invalid credentials.");
            return;
        }

        dispose();
        User user = userOpt.get();

        switch (user.getRole()) {
            case STUDENT -> new StudentDashboardFrame((Student) user).setVisible(true);
            case EVALUATOR -> new EvaluatorDashboardFrame((Evaluator) user).setVisible(true);
            case COORDINATOR -> new CoordinatorDashboardFrame((Coordinator) user).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

/* ===================== MODELS ===================== */

enum RoleType { STUDENT, EVALUATOR, COORDINATOR }

abstract class User {
    private final String userID, name, email, password;
    private final RoleType role;

    public User(String userID, String name, String email, String password, RoleType role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public RoleType getRole() { return role; }
}

class Evaluator extends User {
    public Evaluator(String id, String name, String email, String password) {
        super(id, name, email, password, RoleType.EVALUATOR);
    }
}

class Coordinator extends User {
    public Coordinator(String id, String name, String email, String password) {
        super(id, name, email, password, RoleType.COORDINATOR);
    }
}

/* ===================== AUTH SERVICE ===================== */

class AuthService {
    private final List<User> users = new ArrayList<>();

    public AuthService() {
        users.add(new Student("S001", "Ali Student", "student", "1234"));
        users.add(new Evaluator("E001", "Dr. Tan", "evaluator", "1234"));
        users.add(new Coordinator("C001", "Ms. Lim", "coordinator", "1234"));
    }

    public Optional<User> login(String email, String password) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .filter(u -> u.getPassword().equals(password))
                .findFirst();
    }
}

/* ===================== STUDENT ===================== */

class StudentDashboardFrame extends JFrame {
    public StudentDashboardFrame(Student student) {
        setTitle("Student Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new StudentPanel(student));
    }
}

class StudentPanel extends JPanel {
    public StudentPanel(Student student) {
        setLayout(new GridLayout(0, 2, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Title:"));
        add(new JTextField());

        add(new JLabel("Abstract:"));
        add(new JTextArea(3, 20));

        add(new JLabel("Supervisor:"));
        add(new JTextField());

        add(new JLabel("Presentation Type:"));
        add(new JComboBox<>(new String[]{"Oral", "Poster"}));

        add(new JLabel("File Path:"));
        add(new JTextField());

        JButton submit = new JButton("Submit");
        submit.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Submission saved successfully!")
        );

        add(new JLabel());
        add(submit);
    }
}

/* ===================== EVALUATOR ===================== */

class EvaluatorDashboardFrame extends JFrame {

    public EvaluatorDashboardFrame(Evaluator evaluator) {

        setTitle("Evaluator Dashboard");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnEvaluate = new JButton("Evaluate Presentation");
        btnEvaluate.addActionListener(e -> {
            EvaluationDialog d = new EvaluationDialog(this);
            d.setVisible(true);
        });

        JPanel panel = new JPanel(); // FlowLayout by default
        panel.add(new JLabel(
                "Welcome, " + evaluator.getName() + " (Evaluator)"
        ));
        panel.add(btnEvaluate);

        add(panel);
    }
}


class EvaluationDialog extends JDialog {
    public EvaluationDialog(JFrame parent) {
        super(parent, "Evaluate Presentation", true);
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 1, 6, 6));

        add(new JLabel("Presenter:"));
        add(new JTextField("Ali Student"));

        add(new JLabel("Problem Clarity (1–5):"));
        add(new JSpinner(new SpinnerNumberModel(3, 1, 5, 1)));

        add(new JLabel("Methodology (1–5):"));
        add(new JSpinner(new SpinnerNumberModel(3, 1, 5, 1)));

        add(new JLabel("Results (1–5):"));
        add(new JSpinner(new SpinnerNumberModel(3, 1, 5, 1)));

        add(new JLabel("Presentation (1–5):"));
        add(new JSpinner(new SpinnerNumberModel(3, 1, 5, 1)));

        add(new JLabel("Comments:"));
        add(new JScrollPane(new JTextArea(3, 20)));

        JButton submit = new JButton("Submit Evaluation");
        submit.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Evaluation submitted successfully!");
            dispose();
        });
        add(submit);
    }
}

/* ===================== COORDINATOR ===================== */

class CoordinatorDashboardFrame extends JFrame {
    public CoordinatorDashboardFrame(Coordinator coordinator) {
        setTitle("Coordinator Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnCreate = new JButton("Create Session");
        JButton btnSchedule = new JButton("View Schedule");
        JButton btnAwards = new JButton("Generate Awards");

        btnCreate.addActionListener(e -> {
            CreateSessionDialog d = new CreateSessionDialog(this);
            d.setVisible(true);
        });

        btnSchedule.addActionListener(e -> {
            ViewScheduleDialog d = new ViewScheduleDialog(this);
            d.setVisible(true);
        });

        btnAwards.addActionListener(e -> {
            AwardDialog d = new AwardDialog(this);
            d.setVisible(true);
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 8, 8));
        panel.add(btnCreate);
        panel.add(btnSchedule);
        panel.add(btnAwards);

        add(panel);
    }
}





