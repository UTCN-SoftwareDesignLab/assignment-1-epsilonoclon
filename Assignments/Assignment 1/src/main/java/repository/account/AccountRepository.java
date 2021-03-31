package repository.account;

import model.Account;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository
{
    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;
    List<Account> findAccountsByOwner(Long ownerId);
    boolean update(Long id,Account account);
    boolean remove(Long id);
    void removeAll();
    Notification<Account> save(Account account, Long id);
}
