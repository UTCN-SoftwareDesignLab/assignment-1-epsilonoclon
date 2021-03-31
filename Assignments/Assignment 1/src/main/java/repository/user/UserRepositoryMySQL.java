package repository.user;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.EntityNotFoundException;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Database.Constants.Tables.USER;

public class UserRepositoryMySQL implements UserRepository
{
    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }
//******** FIND ALL USERS AS SEEN IN LAB ACTIVITY
    @Override
    public List<User> findAll()
    {
        List<User> users=null;
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from " + USER;
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);

            users=new ArrayList<>();
            while (userResultSet.next()) {
                User user = makeUser(userResultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    //SEARCH AND FIND USER BY ID
    @Override
    public User findByID(Long id) throws EntityNotFoundException
    {
        Statement statement;
        User user=null;

        try
        {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + USER +  " where `id`=\'"  + id + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchRoleSql);

            if (userResultSet.next())
            {
                user = makeUser(userResultSet);
            }
            else
                throw new EntityNotFoundException(id,User.class.getSimpleName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
//STOLEN FROM LAB
    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password)
    {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }
//AGAIN BASICALLY STOLEN FROM LAB
    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
//UPDATE A USER'S ID
    @Override
    public boolean update(Long id,User user)
    {
        try {
            PreparedStatement updateClientStatement = connection
                    .prepareStatement("UPDATE "+ USER+" SET username= ?,password= ? WHERE id= ?");
            updateClientStatement.setString(1, user.getUsername());
            updateClientStatement.setString(2, user.getPassword());
            updateClientStatement.setLong(3, id);
            updateClientStatement.executeUpdate();

            user.setId(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //!!!!TO DO 2MORROW REMOVE STATEMENTS AND REST OF REPOSITORIES!!!!!!!!!!!!!!


//Like delete all, but we search for a specific id as oppossed to finding all ids
    @Override
    public boolean remove(Long id)
    {
        try {
            PreparedStatement removeClientStatement = connection
                    .prepareStatement("DELETE FROM " + USER + " WHERE id= ?");
            removeClientStatement.setLong(1, id);
            removeClientStatement.executeUpdate();
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User makeUser(ResultSet userResultSet) throws SQLException
    {
        User user= new UserBuilder()
                .setId(userResultSet.getLong("id"))
                .setUsername(userResultSet.getString("username"))
                .setPassword(userResultSet.getString("password"))
                .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                .build();
        return user;
    }
}
