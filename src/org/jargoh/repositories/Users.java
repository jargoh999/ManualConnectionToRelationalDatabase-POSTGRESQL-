package org.jargoh.repositories;
import org.jargoh.models.User;

import java.sql.*;


public class Users {

    public static Connection connect(){
         String URL ="jdbc:postgresql://localhost:5432/Jargoh_place";//localhost${port}/${database}?createDatabaseIfNotExists=true"
         String USERNAME="postgres";
         String PASSWORD="password";
        try {
            return DriverManager.getConnection(URL, USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User saveUser (User user ){
         String getIdSqlStatement = "select count(*) from users";
         String SQL="insert into users (id,wallet_id) values (?,?)";
          try(Connection connection = connect()){
              PreparedStatement preparedStatement=connection.prepareStatement(getIdSqlStatement);
              ResultSet resultSet=preparedStatement.executeQuery();
              resultSet.next();
              long currentId = resultSet.getLong(1);
              preparedStatement=connection.prepareStatement(SQL);
              preparedStatement.setLong(1,currentId+1);
              preparedStatement.setObject(2,user.getWalletId());
              preparedStatement.execute();
              return findUserById(currentId+1);
          }catch (SQLException exception){
              System.err.println(exception.getMessage());
              throw new RuntimeException("failed to connect");
          }
    }


    public User findUserById(Long id) {
        String SQL = "select * from users where id=?";
        try (Connection connection = connect()) {
            var preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1,id);
            var resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long userId = resultSet.getLong(1);
            long walletId = resultSet.getLong(2);
            User user = new User();
            user.setId(userId);
            user.setWalletId(walletId);
            return user;
        }catch(SQLException e){
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed To Connect to database");
   }

        }
//        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
//        var resultSet =preparedStatement.executeQuery();
//        resultSet.next();
//        Long userId = resultSet.getLong(1);
//        Long walletId = resultSet.getLong(1);
//        User user = new User();
//        user.setId(userId);
//        user.setWalletId(walletId);
//        return user;
//    }catch (SQLException e){
//        System.err.println("error" + e.getMessage());
//        throw new RuntimeException("failed to connect to database");
//    }

    }


