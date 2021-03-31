package service.account;
import Database.DBConnectionFactory;
import model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceSQL;

import java.sql.Connection;
import java.sql.Date;
public class AccountServiceSQLTest {

    private static Account testAccount;
    private static AccountService accountService;
    private static ClientService clientService;

    @BeforeClass
    public static void setUp() //This literally killed me psychologically, connection couldn't establish correctly, although it worked on lab examples
    {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        AccountRepositoryMySQL accountRepositoryMySQL=new AccountRepositoryMySQL(connection);
        clientService=new ClientServiceSQL(new ClientRepositoryMySQL(connection));
        accountService=new AccountServiceSQL(accountRepositoryMySQL,clientService);
    }

    @Before
    public void cleanup()
    {
        clientService.removeAll(); //clean everything up make it nice and tidy
        clientService.save("Anon","1973824650","1234567891234567","/v/");
        testAccount=accountService.save("Home",(long) 200,"1234567890",new Date(1000L)).getResult();
    }

    @Test
    public void findAll() {
        Assert.assertTrue(accountService.findAll().size()==1);
    }

    @Test
    public void save() {
        Assert.assertTrue(accountService.save("MyType", (long) 100,"1231567891",new Date(1000L)).hasErrors());
    }

    @Test
    public void update()
    {
        Assert.assertTrue(accountService.update(testAccount.getId(),"SomethingElse",(long)1000,testAccount.getDateCreated()).getResult());
    }

    @Test
    public void transfer() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void findClientsAccounts() {
    }
}