public class Person {
    public static boolean isEmailValid(String email) {
        if(!email.contains("@") || email.length() > 50) {
            return false;
        }

        return true;
    }
}