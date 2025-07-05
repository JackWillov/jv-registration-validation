package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int ACCEPTED_AGE = 18;
    private static final int ACCEPTED_PASSWORD_LENGTH = 6;
    private static final int ACCEPTED_LOGIN_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null
                || user.getLogin().length() < ACCEPTED_LOGIN_LENGTH) {
            throw new InvalidDataException("Login can't be null or less than "
                    + ACCEPTED_LOGIN_LENGTH + " symbols");
        }
        if (user.getPassword() == null
                || user.getPassword().length() < ACCEPTED_PASSWORD_LENGTH) {
            throw new InvalidDataException("Password can't be null or less than "
                    + ACCEPTED_PASSWORD_LENGTH + " symbols");
        }
        if (user.getAge() < ACCEPTED_AGE) {
            throw new InvalidDataException("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + ACCEPTED_AGE);
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidDataException("User with login : " + user.getLogin()
                    + " already exist");
        }
        return storageDao.add(user);
    }
}
