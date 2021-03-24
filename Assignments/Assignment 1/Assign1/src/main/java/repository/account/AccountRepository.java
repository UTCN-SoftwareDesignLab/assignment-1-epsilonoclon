package repository.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;
    void transferMoney(Long sourceId, Long destinationId, Long amount);
    void deleteAccount(Long id);
    void deleteAll();
    boolean save(Account account);
}
