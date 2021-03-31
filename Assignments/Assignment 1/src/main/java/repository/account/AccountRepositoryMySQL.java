package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Database.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository
{
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean update(Long id, Account account) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE "+ ACCOUNT+" SET type= ?,balance= ?, creation= ? WHERE id= ?");
            updateAccountStatement.setString(1, account.getType());
            updateAccountStatement.setLong(2, account.getAmount());
            updateAccountStatement.setDate(3, (Date) account.getDateCreated());
            updateAccountStatement.setLong(4, id);
            updateAccountStatement.executeUpdate();

            account.setId(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Account> findAll()
    {
        Statement statement;

        List<Account> accounts=null;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACCOUNT ;
            ResultSet rs = statement.executeQuery(fetchRoleSql);

            accounts=new ArrayList<Account>();
            while(rs.next()) {
                Account account = createAccount(rs);
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException
    {
        Statement statement;
        Account account;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACCOUNT + " where `id`=\'" + id + "\'";
            ResultSet rs = statement.executeQuery(fetchRoleSql);

            if(rs.next()) {
                account = createAccount(rs);
            }
            else
            {
                throw new EntityNotFoundException(id,Account.class.getSimpleName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id,Account.class.getSimpleName());
        }
        return account;
    }


    @Override
    public boolean remove(Long id)
    {
        try {
            PreparedStatement removeAccountStatement = connection
                    .prepareStatement("DELETE FROM " + ACCOUNT + " WHERE id= ?");
            removeAccountStatement.setLong(1, id);
            removeAccountStatement.executeUpdate();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll()
    {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from "+ACCOUNT+" where id >= 0";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Notification<Account> save(Account account, Long id)
    {
        Notification<Account> notificationSaveAccount=new Notification<>();
        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO "+ACCOUNT+" values (null, ?, ?, ?, ?)");
            insertAccountStatement.setString(1, account.getType());
            insertAccountStatement.setLong(2, account.getAmount());
            insertAccountStatement.setLong(3, id);
            insertAccountStatement.setDate(4, (java.sql.Date) account.getDateCreated());
            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();
            rs.next();
            long accountId = rs.getLong(1);
            account.setId(accountId);

            notificationSaveAccount.setResult(account);
            return notificationSaveAccount;

        } catch (SQLException e) {
            e.printStackTrace();
            notificationSaveAccount.addError("Saving account failed");
            return notificationSaveAccount;
        }
    }

    @Override
    public List<Account> findAccountsByOwner(Long id)
    {
        Statement statement;

        List<Account> accounts=null;
        try {
            statement = connection.createStatement();
            String fetchAccountSql = "Select * from " + ACCOUNT + " where `client_id`=\'" + id + "\'";
            ResultSet rs = statement.executeQuery(fetchAccountSql);

            accounts=new ArrayList<Account>();
            while(rs.next()) {
                Account account = createAccount(rs);
                accounts.add(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private Account createAccount(ResultSet accountResultSet) throws SQLException
    {
        Account account =new AccountBuilder()
                .setID(accountResultSet.getLong("id"))
                .setDateCreated(accountResultSet.getDate("dateCreated"))
                .setAmount(accountResultSet.getLong("amount"))
                .setType(accountResultSet.getString("type"))
                .build();
        return account;
    }
}
