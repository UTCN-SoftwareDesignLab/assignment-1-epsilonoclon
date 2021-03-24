package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import repository.EntityNotFoundException;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository
{
    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll()
    {
        Date dateEvent = new Date();
        List<Account> accounts =new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next())
            {
                logActivityAccount("Acount " + rs.getInt("id") + "has been found", dateEvent);
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException
    {
        Date dateEvent = new Date();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next())
            {
                logActivityAccount("Acount " + id + "has been found", dateEvent);
                return getAccountFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
    }

    @Override
    public void transferMoney(Long sourceId, Long destinationId, Long amount)
    {
        Date dateEvent = new Date();
        try {
            Statement statement = connection.createStatement();
            String sqlSource = "Select * from account where id=" + sourceId;
            ResultSet rsSource = statement.executeQuery(sqlSource);

            String sqlDestination = "Select * from account where id=" + destinationId;
            ResultSet rsDestination = statement.executeQuery(sqlDestination);

            if (rsSource.next() && rsDestination.next())
            {
                logActivityAccount("Acount " + sourceId + "has been transfered " + amount + " to account " + destinationId, dateEvent);
                setAccountTransation(rsSource, amount*(-1));
                setAccountTransation(rsDestination, amount);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(Long id)
    {
        Date dateEvent = new Date();
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id= " + id;
            statement.executeUpdate(sql);
            logActivityAccount("Account " + id + " has been deleted ", dateEvent);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll()
    {
        Date dateEvent = new Date();
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id>=0";
            statement.executeUpdate(sql);
            logActivityAccount("all accounts have been deleted ", dateEvent);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public boolean save(Account account)
    {
         try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?)");
            insertStatement.setString(1, account.getType());
            insertStatement.setLong(2, account.getAmount());
            insertStatement.setDate(3, new java.sql.Date(account.getDateCreated().getTime()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setID(rs.getLong("id"))
                .setAmount(rs.getLong("amount"))
                .setType(rs.getString("type"))
                .setDateCreated(new Date(rs.getDate("dateCreated").getTime()))
                .build();
    }

    private Account setAccountTransation(ResultSet rs, Long amount) throws SQLException
    {
        return new AccountBuilder()
                .setID(rs.getLong("id"))
                .setAmount(rs.getLong("id")+amount)
                .setType(rs.getString("type"))
                .setDateCreated(new Date(rs.getDate("dateCreated").getTime()))
                .build();
    }

    public String logActivityAccount(String event, Date date)
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateEvent = new Date();
        return df.format(dateEvent) + ": " + event;
    }
}
