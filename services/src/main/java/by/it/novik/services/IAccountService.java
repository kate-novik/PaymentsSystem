package by.it.novik.services;


import by.it.novik.pojos.Account;
import by.it.novik.util.ServiceException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IAccountService {

    /**
     * Получение списка счетов пользователя
     * @param id_user id пользователя
     * @param orderState порядок сортировки по состоянию счета
     * @return список счетов пользователя
     */
    List<Account> getAccountsByUser(Serializable id_user, String orderState) throws ServiceException;

    /**
     * Блокировка счета
     * @param account Объект Account
     */
    void lockingAccount(Account account) throws ServiceException;

    /**
     * Разблокировка счета
     * @param account Объект Account
     */
    void unlockingAccount(Account account) throws ServiceException;

    /**
     * Получение списка всех счетов
     * @return Список счетов
     */
    List<Account> getAllAccounts () throws ServiceException;

    /**
     * Получение списка заблокированных счетов
     * @return Список заблокированных счетов
     */
    List<Account> getLockedAccounts () throws ServiceException;

    /**
     * Удаление счета
     * @param id_account Номер счета
     */
    void deleteAccount (Serializable id_account) throws ServiceException;
}
