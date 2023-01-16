package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static connection.ConnectionConst.*;

public class DBConnectionUtil
{
    public static Connection getConnection()
    {
        try
        {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch(SQLException e)
        {
            throw new IllegalStateException(e);
        }
    }
}
