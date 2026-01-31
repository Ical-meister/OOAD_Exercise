class StudentDashboardFrame extends JFrame {

    public StudentDashboardFrame(Student student) {

        setTitle("Student Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new StudentPanel(student));
    }
}
