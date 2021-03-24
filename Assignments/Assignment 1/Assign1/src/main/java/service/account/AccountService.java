package service.account;

import model.Account;
import repository.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface AccountService
{
    List<Account> findAll();

    Account findById(Long id) throws EntityNotFoundException;
}
