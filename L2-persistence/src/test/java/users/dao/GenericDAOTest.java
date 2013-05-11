package users.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import users.IUser;
import users.IUserDAO;
import users.dao.dataobject.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class GenericDAOTest {

    private static boolean initialized = false;

    private IUserDAO userDAO;

    public GenericDAOTest() {
        super();
    }

    protected void setDAO(IUserDAO dao) {
        userDAO = dao;
    }

    @BeforeClass
    public static void setupTable() {
        if (initialized == false) {

            String connectionUrl = "jdbc:hsqldb:mem:.";
            String userName = "";
            String password = "";

            Connection con;
            try {
                con = DriverManager.getConnection(connectionUrl, userName, password);
                // Step 3:  Create a statement where the SQL statement will be provided
                Statement stmt = con.createStatement();
//                stmt.executeUpdate("create table USERS(id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 100) PRIMARY KEY, name varchar(50))");
                stmt.executeUpdate("INSERT INTO users (name) values('Kid')");
                stmt.executeUpdate("INSERT INTO users (name) values('Rob')");
                stmt.executeUpdate("INSERT INTO users (name) values('Dave')");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            initialized = true;
        }

    }

    @Test
    public void testGetUsers() throws Exception {
        List<IUser> users = userDAO.getUsers();
        assertTrue(users.size() > 0);
    }

    @Test
    public void testGetUsersString() throws Exception {
        int size = userDAO.getUsers("Dave").size();
        assertTrue(size > 0);
    }
    private static int newlyCreatedUser;

    @Test
    public void testSaveUserWithNewUser() throws Exception {
        User user = new User();
        user.setName("test me");
        newlyCreatedUser = userDAO.saveUser(user);
        assertTrue(newlyCreatedUser > 0);
    }

    @Test
    public void testSaveUserWithExistingUser() throws Exception {
        User user = new User();
        user.setId(newlyCreatedUser);
        user.setName("test me update");
        int newId = userDAO.saveUser(user);
        assertEquals(newlyCreatedUser, newId);
    }

    @Test
    public void testDeleteUser() throws Exception {
        int b4 = userDAO.getUsers().size();
        User user = new User();
        user.setId(newlyCreatedUser);
        userDAO.deleteUser(user);
        assertEquals(b4 - 1, userDAO.getUsers().size());
    }
}