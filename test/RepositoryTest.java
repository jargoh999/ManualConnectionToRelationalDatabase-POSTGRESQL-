import org.jargoh.models.User;
import org.jargoh.repositories.Users;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RepositoryTest {

    private final Users users = new Users();
    @Test
    public void testDatabaseConnection()  {
        try (Connection connection = Users.connect()){
            assertNotNull(connection);
            System.out.println();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSaveUser(){
        User user = new User();
        user.setWalletId(1L);
        User savedUser =users.saveUser(user);
        assertNotNull(savedUser);

    }

}
