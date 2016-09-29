package by.it.novik.services;


import by.it.novik.entities.Payment;
import by.it.novik.util.ServiceException;
import by.it.novik.valueObjects.PaymentsFilter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IPaymentService {
    /**
     * Получение списка платежей пользователя
     * @param id_user id пользователя
     * @param orderState порядок сортировки по состоянию счета
     * @param pageSize Количество элементов на странице
     * @param firstItem Позиция первого элемента для вывода
     * @return список платежей пользователя
     */
    List<Payment> getPaymentsByUser(Serializable id_user, String orderState, Integer pageSize, Integer firstItem)
            throws ServiceException;
    /**
     * Получение списка платежей пользователя
     * @param id_account id счета
     * @param orderState порядок сортировки по состоянию счета
     * @param pageSize Количество элементов на странице
     * @param firstItem Позиция первого элемента для вывода
     * @param paymentsFilter Object paymentsFilter
     * @return список платежей пользователя
     */
    List<Payment> getPaymentsByAccount (Serializable id_account, String orderState, Integer pageSize, Integer firstItem,
                                        PaymentsFilter paymentsFilter) throws ServiceException;

    /**
     * Сделать платежку
     * @param idAccountFrom Номер счета отправителя
     * @param idAccountTo Номер счета получателя
     * @param pay_amount Сумма платежа
     * @param description Описание платежа
     * @throws Exception
     */
    void makePayment(int idAccountFrom, int idAccountTo, double pay_amount, String description) throws ServiceException;

    /**
     * Получение списка платежей всех пользователей
     * @param orderState порядок сортировки по состоянию счета
     * @param pageSize Количество элементов на странице
     * @param firstItem Позиция первого элемента для вывода
     * @param paymentsFilter Object paymentsFilter
     * @return Список платежей пользователей
     */
    List<Payment> getAllPayments (String orderState, Integer pageSize, Integer firstItem, PaymentsFilter paymentsFilter)
            throws ServiceException;

    /**
     * Создание/обновление записи объекта в БД
     * @param payment Объект для записи/обновления
     * @throws ServiceException
     */
    void saveOrUpdate(Payment payment) throws ServiceException;

    /**
     * Чтение записи (объекта) из БД
     * @param id Номер записи
     * @return Прочтенный объект User
     */
    Payment get(Serializable id) throws ServiceException;

    /**
     * Удаление записи (объекта) из БД по уникальному идентификатору
     * @param id Номер записи
     */
    void delete(Serializable id) throws ServiceException;

    /**
     * Getting total count of payments with filter
     * @param paymentsFilter Object PaymentsFilter with filters
     * @return total count of payments
     */
    Integer getTotalCountOfPayments(PaymentsFilter paymentsFilter);

    /**
     * Getting total count of payments with filter
     * @param paymentsFilter Object PaymentsFilter with filters
     * @param idAccount ID account
     * @return total count of payments
     */
    Integer getTotalCountOfPayments(PaymentsFilter paymentsFilter, Long idAccount) throws ServiceException;
}
