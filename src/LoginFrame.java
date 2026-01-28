import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

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

        JLabel lblEmail = new JLabel("Email / Username:");
        JLabel lblPassword = new JLabel("Password:");


        txtEmail = new JTextField(20);
        txtPassword = new JPasswordField(20);

        lblStatus = new JLabel(" ");
        lblStatus.setForeground(Color.RED);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(e -> handleLogin());
        getRootPane().setDefaultButton(btnLogin);

        //Row 0: Email
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(lblEmail, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(txtEmail, gbc);

        //Row 1: Password
        gbc.gridy = 1;

        gbc.gridx = 0;
        gbc.weightx = 0;
        panel.add(lblPassword, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        panel.add(txtPassword, gbc);

        //Row 2: Status
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(lblStatus, gbc);

        //Row 3: Button
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        panel.add(btnLogin, gbc);

        setContentPane(panel);


        SwingUtilities.invokeLater(() -> txtEmail.requestFocusInWindow());
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

        User user = userOpt.get();
        dispose(); // close login window

        // Route user automatically by their role/class
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



enum RoleType { STUDENT, EVALUATOR, COORDINATOR }

abstract class User {
    private final String userID;
    private final String name;
    private final String email;
    private final String password;
    private final RoleType role;

    public User(String userID, String name, String email, String password, RoleType role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public RoleType getRole() { return role; }
}

class Student extends User {
    public Student(String userID, String name, String email, String password) {
        super(userID, name, email, password, RoleType.STUDENT);
    }
}

class Evaluator extends User {
    public Evaluator(String userID, String name, String email, String password) {
        super(userID, name, email, password, RoleType.EVALUATOR);
    }
}

class Coordinator extends User {
    public Coordinator(String userID, String name, String email, String password) {
        super(userID, name, email, password, RoleType.COORDINATOR);
    }
}

class AuthService {
    private final List<User> users = new ArrayList<>();

    public AuthService() {
        // Hardcoded demo users (role is implied by the object type)
        users.add(new Student("S001", "Ali Student", "student", "1234"));
        users.add(new Evaluator("E001", "Dr. Tan Evaluator", "evaluator", "1234"));
        users.add(new Coordinator("C001", "Ms. Lim Coordinator", "coordinator", "1234"));
    }

    public Optional<User> login(String emailOrUsername, String password) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(emailOrUsername))
                .filter(u -> u.getPassword().equals(password))
                .findFirst();
    }
}

//Dashboards (placeholder) 

class StudentDashboardFrame extends JFrame {
    public StudentDashboardFrame(Student student) {
        setTitle("Student Dashboard");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new JLabel("Welcome, " + student.getName() + " (Student)", SwingConstants.CENTER));
    }
}

class EvaluatorDashboardFrame extends JFrame {
    public EvaluatorDashboardFrame(Evaluator evaluator) {
        setTitle("Evaluator Dashboard");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new JLabel("Welcome, " + evaluator.getName() + " (Evaluator)", SwingConstants.CENTER));
    }
}

class CoordinatorDashboardFrame extends JFrame {
    public CoordinatorDashboardFrame(Coordinator coordinator) {
        setTitle("Coordinator Dashboard");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new JLabel("Welcome, " + coordinator.getName() + " (Coordinator)", SwingConstants.CENTER));
    }
}

