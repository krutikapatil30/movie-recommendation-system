import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection 
{

    static final String URL =
            "jdbc:mysql://localhost:3306/movie_system";

    static final String USER = "root";
    static final String PASSWORD = "jayu@309";

    public static Connection getConnection()
    {

        try
        {

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection
            (
                    URL,
                    USER,
                    PASSWORD
            );

        } catch(Exception e)
         
        {
            e.printStackTrace();
        }

        return null;
    }
}