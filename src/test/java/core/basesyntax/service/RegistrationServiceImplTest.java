package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static List<User> userList;
    private static User user1;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        userList = new ArrayList<>();
        user1 = new User();
        storage = new Storage();
    }

    @AfterEach
    void tearDown() {
        storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();

        user1.setLogin("john_doe");
        user1.setPassword("password123");
        user1.setAge(30);

        User user2 = new User();
        user2.setLogin("jane_doe");
        user2.setPassword("qwerty456");
        user2.setAge(25);

        User user3 = new User();
        user3.setLogin("adminlogin");
        user3.setPassword("adminpass");
        user3.setAge(40);

        User user4 = new User();
        user4.setLogin("guests");
        user4.setPassword("guest123");
        user4.setAge(18);

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
    }

    @Test
    void register_userExist_notOk() {
        registrationService.register(userList.get(2));
        userList.get(1).setLogin(userList.get(2).getLogin());
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(userList.get(1)),
                "Expected "
                        + InvalidDataException.class.getName()
                        + " because user with same login already added");

    }

    @Test
    void register_Ok() {
        for (User user : userList) {
            assertEquals(user, registrationService.register(user));
        }
    }

    @Test
    void register_ageUnder18_NotOk() {
        user1.setAge(17);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1),
                "User age " + user1.getAge()
                        + " but must be at least 18 expected "
                        + InvalidDataException.class.getName());
    }

    @Test
    void register_loginLengthUnder6_NotOk() {
        user1.setLogin("admin");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1),
                "User login length " + user1.getLogin().length()
                        + " but must be at least 6 expected "
                        + InvalidDataException.class.getName());
    }

    @Test
    void register_PasswordLengthUnder6_NotOk() {
        user1.setPassword("admin");
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1),
                "User login length " + user1.getPassword().length()
                        + " but must be at least 6 expected "
                        + InvalidDataException.class.getName());
    }

    @Test
    void register_PasswordNull_NotOk() {
        user1.setPassword(null);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1),
                "User password equals null expected "
                        + InvalidDataException.class.getName());
    }

    @Test
    void register_loginNull_NotOk() {
        user1.setLogin(null);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1),
                "User login equals null expected "
                        + InvalidDataException.class.getName());
    }
}
