package catergoo.manager;

import catergoo.model.User;
import java.util.List;
import java.util.ArrayList;

public class SessionManager {
    private static List<User> registeredUsers = new ArrayList<>();
    private static User currentUser = null;

    public static void logoutUser() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isUserRegistered(String username) {
        if (username == null)
            return false;

        return registeredUsers.stream().anyMatch(user -> user.getUsername().equals(username));
    }

}
