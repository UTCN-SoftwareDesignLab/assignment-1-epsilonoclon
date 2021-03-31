package service.account;

import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface AccountService
{
    List<Account> findAll();

    Notification<Account> findById(Long id);

    Notification<Account> save(String type,Long amount,String clientCNP, Date creation);

    Notification<Boolean> update(Long id,String type,Long balance, Date creation);

    Notification<Boolean> transfer(Long receiverId,Long senderId, int sum);

    boolean remove(Long id);

    List<Account> findClientsAccounts(Long clientId);
}
