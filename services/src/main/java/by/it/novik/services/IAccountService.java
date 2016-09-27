package by.it.novik.services;


import by.it.novik.entities.Account;
import by.it.novik.entities.User;
import by.it.novik.util.ServiceException;
import by.it.novik.valueObjects.AccountsFilter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IAccountService {

    Account create(User user) throws ServiceException;

    /**
     * Получение списка счетов пользователя
     * @param id_user id пользователя
     * @param orderState порядок сортировки по состоянию счета
     * @param pageSize Количество элементов на странице
     * @param firstItem Позиция первого элемента для вывода
     * @return список счетов пользователя
     */
    List<Account> getAccountsByUser(Serializable id_user, String orderState, Integer pageSize, Integer firstItem) throws ServiceException;

    /**
     * Блокировка счета
     * @param account Объект Account
     */
    void lockingAccount(Account account) throws ServiceException;


    void lockingAccount(Long account) throws ServiceException;

    Account refill(Long account, Double amount)  throws ServiceException;

    void moneyTransfer(Long source, Long destination, Double amount) throws ServiceException;

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

    /**
     * Создание/обновление записи объекта в БД
     * @param account Объект для записи/обновления
     * @throws ServiceException
     */
    void saveOrUpdate(Account account) throws ServiceException;

    /**
     * Чтение записи (объекта) из БД
     * @param id Номер записи
     * @return Прочтенный объект User
     */
    Account get(Serializable id) throws ServiceException;

    /**
     * Удаление записи (объекта) из БД по уникальному идентификатору
     * @param id Номер записи
     */
    void delete(Serializable id) throws ServiceException;

    /**
     * Getting total count of accounts with filter
     * @param accountsFilter Object AccountsFilter
     * @return total count of accounts
     */
    Integer getTotalCountOfAccounts (AccountsFilter accountsFilter);
}
