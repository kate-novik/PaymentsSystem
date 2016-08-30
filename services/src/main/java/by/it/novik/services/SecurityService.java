package by.it.novik.services;



import by.it.novik.pojos.User;
import by.it.novik.util.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;

/**
 * Created by Kate Novik.
 */
public class SecurityService implements ISecurityService {

    private UserService userService = Service.getService().getUserService();

    /**
     * Получение хэш-кода в 16-ричной системе для пароля с добавлением "соли"
     * @param password Пароль
     * @param salt "Соль"
     * @return Захэшированный пароль
     */
    private static String getHash (String password, String salt) {
        //Получение хэша пароля с солью
        return DigestUtils.md5Hex(password.concat(salt));
    }

//    private static String getSecurePassword(String passwordToHash)
//    {
//        String generatedPassword = null;
//        try {
//            // Create MessageDigest instance for MD5
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            //Add password bytes to digest
//            md.update(getSalt());
//            //Get the hash's bytes
//            byte[] bytes = md.digest(passwordToHash.getBytes());
//            //This bytes[] has bytes in decimal format;
//            //Convert it to hexadecimal format
//            StringBuilder sb = new StringBuilder();
//            for(int i=0; i< bytes.length ;i++)
//            {
//                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            //Get complete hashed password in hex format
//            generatedPassword = sb.toString();
//        }
//        catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return generatedPassword;
//    }


    /**
     * Получение "соли" для пароля
     * @return Массив байтов
     */
    private static String getSalt() {
        String saltStr = null;
        //Создаем массив для "соли"
        byte[] salt = new byte[16];
        try {
        //Получаем объект SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");

        //Получаем рандом "соли"
        sr.nextBytes(salt);
        saltStr = new String(salt,"windows-1251");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saltStr;
    }

    @Override
    public User findUser(String login, String password) throws ServiceException {
        //Получим соль по логину
        User user = userService.findByLogin(login);
        String salt;
        //Проверка на наличие user в базе данных
        if (user!=null) {
            salt = user.getSalt();
            //Захэшируем пароль для сверки с паролем в БД
            String hashPassword = getHash(password, salt);
            //Получаем user по введенному логину и паролю
            user = userService.findByLoginAndPass(login, hashPassword);

            //Проверка на наличие user в базе данных
            if (user!=null) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void createUser(User user) throws ServiceException {
        //Получим соль для пароля
        String salt = getSalt();
        //Захэшируем пароль
        String hashPassword = getHash(user.getPassword(),salt);
        //Пароль по безопасности нужно "солить" и хэшировать
            user.setSalt(salt);
            user.setPassword(hashPassword);
            user.setRole(Service.getService().getRoleService().get(2));
            userService.saveOrUpdate(user);
    }
}
