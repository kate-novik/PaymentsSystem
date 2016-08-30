package by.it.novik.services;


import by.it.novik.pojos.Payment;
import by.it.novik.util.ServiceException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kate Novik.
 */
public interface IPaymentService {
    /**
     * Получение списка платежей пользователя
     * @param id_user id пользователя
     * @return список платежей пользователя
     */
    List<Payment> getPaymentsByUser(Serializable id_user) throws ServiceException;
    /**
     * Получение списка платежей пользователя
     * @param id_account id счета
     * @return список платежей пользователя
     */
    List<Payment> getPaymentsByAccount (Serializable id_account) throws ServiceException;

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
     * @return Список платежей пользователей
     */
    List<Payment> getAllPayments () throws ServiceException;
}
