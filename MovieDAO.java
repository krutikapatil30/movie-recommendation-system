import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO
{
    public List<Movie> getAllMovies() 
    {

        List<Movie> movies = new ArrayList<>();

        try
        {

            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM movies";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while(rs.next())
            {
                Movie movie =new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("language"),
                rs.getDouble("rating"));

                movies.add(movie);
            }

        } catch(Exception e) 
        {
            e.printStackTrace();
        }
        return movies;
    }
    public List<Movie> searchMovie(String name)
    {
        List<Movie> movies = new ArrayList<>();
        try 
        {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM movies WHERE title LIKE ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) 
            {
                Movie movie = new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("language"),
                rs.getDouble("rating"));

                movies.add(movie);
            }

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return movies;
    }
    public void deleteMovie(int id)
    {
        try 
        {
            Connection con = DBConnection.getConnection();
            String query = "DELETE FROM movies WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) 
            {
                System.out.println("Movie deleted successfully!");
            } 
            else 
            {
                System.out.println("Movie not found!");
            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void updateMovie(int id, String title, String genre, String language, double rating)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE movies SET title=?, genre=?, language=?, rating=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, genre);
            ps.setString(3, language);
            ps.setDouble(4, rating);
            ps.setInt(5, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
            {
                System.out.println("Movie updated successfully!");
            }
            else
            {
                System.out.println("Movie not found!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public List<Movie> getTopRatedMovies()
    {
        List<Movie> movies = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM movies ORDER BY rating DESC LIMIT 5";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                Movie movie = new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("language"),
                rs.getDouble("rating"));
                movies.add(movie);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return movies;
    }
    public Movie getRandomMovie()
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM movies ORDER BY RAND() LIMIT 1";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next())
            {
                return new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("language"),
                rs.getDouble("rating"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public void addToFavorites(int userId, int movieId)
    {
        try
        {
            Connection con = DBConnection.getConnection();
           String query = "INSERT INTO favorites(user_id, movie_id) VALUES(?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, movieId);

            ps.executeUpdate();

            System.out.println("Movie added to favorites!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void addToWatchlist(int userId, int movieId)
    {
        try
        {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO watchlist(user_id, movie_id) VALUES(?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setInt(2, movieId);

            ps.executeUpdate();
            
            System.out.println("Movie added to watchlist!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public List<Movie> viewWatchlist(int userId)
    {
        List<Movie> movies = new ArrayList<>();
        
        try
        {
            Connection con = DBConnection.getConnection();
            String query = "SELECT m.* FROM movies m " +
            "JOIN watchlist w ON m.id = w.movie_id " +
            "WHERE w.user_id = ?";
            
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {
                Movie movie = new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("language"),
                rs.getDouble("rating"));
                
                movies.add(movie);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return movies;
    }
    public List<Movie> viewFavorites(int userId)
    {
        List<Movie> movies = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String query ="SELECT m.* FROM movies m " +
            "JOIN favorites f ON m.id = f.movie_id " +
            "WHERE f.user_id = ?";
            
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {
                Movie movie = new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("language"),
                rs.getDouble("rating"));
                
                movies.add(movie);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return movies;
    }
    public void rateMovie(int id, double rating)
    {
        try
        {
            Connection con = DBConnection.getConnection();
            String query = "UPDATE movies SET rating=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setDouble(1, rating);
            ps.setInt(2, id);
            
            int rows = ps.executeUpdate();
            
            if (rows > 0)
            {
                System.out.println("Movie rating updated successfully!");
            }
            else
            {
                System.out.println("Movie not found!");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void registerUser(String username, String password)
    {
        try
        {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO users(username, password) VALUES(?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            System.out.println("User registered successfully!");
        
        }
        catch (Exception e)
        {
            System.out.println("Username already exists!");
        }
    }
    public boolean loginUser(String username, String password)
    {
        try
        {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public int getUserId(String username)
    {
        try
        {
            Connection con = DBConnection.getConnection();

            String query = "SELECT id FROM users WHERE username=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return rs.getInt("id");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }
    public List<Movie> getMoviesByGenre(String genre) 
    {
        List<Movie> movies = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM movies WHERE genre = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, genre);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Movie movie = new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("language"),
                rs.getDouble("rating"));
                
                movies.add(movie);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return movies;
    }
    public List<Movie> getMoviesByLanguage(String language)
    {
        List<Movie> movies = new ArrayList<>();
        try
        {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM movies WHERE language = ?";
            
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, language);

            ResultSet rs = ps.executeQuery();
            
            while (rs.next())
            {
                Movie movie = new Movie(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("genre"),
                rs.getString("language"),
                rs.getDouble("rating"));

                movies.add(movie);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return movies;
    }
}
