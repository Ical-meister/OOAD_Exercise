public class Student extends User {

    public Student(String userID, String name, String email, String password) {
        super(userID, name, email, password, RoleType.STUDENT);
    }
}
