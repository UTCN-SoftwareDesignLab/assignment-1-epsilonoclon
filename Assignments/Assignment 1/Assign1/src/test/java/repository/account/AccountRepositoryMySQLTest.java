package repository.account;

import Database.DBConnectionFactory;
import Database.JDBConnectionWrapper;
import model.Account;
import model.builder.AccountBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;


    @BeforeClass
    public static void setupClass()
    {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);

    }

    @Before
    public void CleanUp()
    {
        accountRepository.deleteAll();
    }

    @Test
    public void findAll()
    {
        List<Account> accounts=accountRepository.findAll();
        assertTrue(accounts.isEmpty());
    }
    Long dumpID=0L;

    @Test
    public void findById() throws Exception
    {
        Account account = new AccountBuilder()
                    .setAmount((long) 100)
                    .setDateCreated(new Date())
                    .setType("Financial")
                    .build();
        accountRepository.save(account);

        List<Account> books = accountRepository.findAll();

        for (Account value : books) {
            if (value.getId() > dumpID) {
                dumpID = value.getId();
            }
        }

        Account foundaccount = accountRepository.findById(dumpID);
        Assert.assertNotNull(foundaccount);
    }

    @Test
    public void transferMoney()
    {

    }

    @Test
    public void deleteAccount()
    {

    }
}