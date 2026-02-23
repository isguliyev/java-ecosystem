public class Person {
    public boolean checkUser(String username) {
        return username.length() >= 8 && !username.trim().matches(".*\\W.*");
    }

    public boolean checkPassword(String password) {
        return password.matches(".*[A-Z].*")
            && password.matches(".*\\d.*")
            && password.matches(".*\\W.*")
            && password.length() >= 8;
    }
}