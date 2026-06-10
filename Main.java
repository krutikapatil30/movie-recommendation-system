import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main 
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        MovieDAO dao = new MovieDAO();
        RecommendationService rs = new RecommendationService();

        int currentUserId = -1;

        while (true) 
        {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.print("Enter choice: ");
            
            int loginChoice = sc.nextInt();
            sc.nextLine();
            
            if (loginChoice == 1)
            {
                System.out.print("Enter Username: ");
                String username = sc.nextLine();

                System.out.print("Enter Password: ");
                String password = sc.nextLine();

                dao.registerUser(username, password);
            }
            else if (loginChoice == 2)
            {
                System.out.print("Enter Username: ");
                String username = sc.nextLine();
                
                System.out.print("Enter Password: ");
                String password = sc.nextLine();
                
                if (dao.loginUser(username, password)) 
                {
                    System.out.println("Login Successful!");
                    currentUserId = dao.getUserId(username);
                    break;
                }
                else
                {
                    System.out.println("Invalid Username or Password!");
                }
            }
        }

        while (true) 
        {
            System.out.println("\n----- Movie Recommendation System -----");
            System.out.println("1. Recommend by Genre");
            System.out.println("2. Search Movie");
            System.out.println("3. Delete Movie");
            System.out.println("4. Update Movie");
            System.out.println("5. Top Rated Movies");
            System.out.println("6. Random Movie Recommendation");
            System.out.println("7. Add to Favorites");
            System.out.println("8. View Favorites");
            System.out.println("9. Rate Movie");
            System.out.println("10. Display Movies by Genre");
            System.out.println("11. Recommend by Language");
            System.out.println("12. Display All Movies");
            System.out.println("13.Sort Movies by Rating");
            System.out.println("14. Add to Watchlist");
            System.out.println("15. View Watchlist");
            System.out.println("16. Exit");

            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) 
            {
                case 1:
                    System.out.print("Enter Favorite Genre: ");
                    String genre = sc.nextLine();

                    List<Movie> movies = dao.getAllMovies();
                    rs.recommendMovies(genre, movies);
                    break;

                case 2:
                    System.out.print("Enter movie name to search: ");
                    String name = sc.nextLine();

                    List<Movie> searchResult = dao.searchMovie(name);

                    if (searchResult.isEmpty()) 
                    {
                        System.out.println("Movie not found!");
                    }
                    else 
                    {
                        System.out.println("\n----- Search Result -----");
                        for (Movie m : searchResult) 
                        {
                            m.displayMovie();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Movie ID to delete: ");
                    int id = sc.nextInt();
                    dao.deleteMovie(id);
                    break;
                
                case 4:
                    System.out.print("Enter Movie ID: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter New Title: ");
                    String newTitle = sc.nextLine();

                    System.out.print("Enter New Genre: ");
                    String newGenre = sc.nextLine();

                    System.out.print("Enter New Language: ");
                    String newLanguage = sc.nextLine();

                    System.out.print("Enter New Rating: ");
                    double newRating = sc.nextDouble();

                    dao.updateMovie(updateId, newTitle, newGenre, newLanguage, newRating);
                    break;

                case 5:
                    List<Movie> topMovies = dao.getTopRatedMovies();
                    System.out.println("\n----- Top Rated Movies -----");
                    for (Movie m : topMovies) 
                    {
                        m.displayMovie();
                    }
                    break;
                
                case 6:
                    Movie randomMovie = dao.getRandomMovie();
                    System.out.println("\n----- Random Movie -----");
                    if (randomMovie != null) 
                    {
                        randomMovie.displayMovie();
                    }
                    else
                    {
                        System.out.println("No movie found!");
                    }
                    break;
                
                case 7:
                    System.out.print("Enter Movie ID to add favorite: ");
                    int favId = sc.nextInt();
                    dao.addToFavorites(currentUserId, favId);
                    break;
                    
                case 8:
                    List<Movie> favMovies = dao.viewFavorites(currentUserId);
                    System.out.println("\n----- Favorite Movies -----");
                    if (favMovies.isEmpty())
                    {
                        System.out.println("No favorite movies found!");
                    }
                    else
                    {
                        for (Movie m : favMovies)
                        {
                            m.displayMovie();
                        }
                    }
                    break;
                
                case 9:
                    System.out.print("Enter Movie ID: ");
                    int rateId = sc.nextInt();

                    System.out.print("Enter New Rating: ");
                    double rating = sc.nextDouble();

                    dao.rateMovie(rateId, rating);
                    break;
                
                case 10:
                    System.out.print("Enter Genre: ");
                    String displayGenre = sc.nextLine();
                    
                    List<Movie> genreMovies = dao.getMoviesByGenre(displayGenre);
                    
                    if (genreMovies.isEmpty())
                    {
                        System.out.println("No movies found!");
                    }
                    else
                    {
                        System.out.println("\n----- Movies by Genre -----");
                        for (Movie m : genreMovies)
                        {
                            m.displayMovie();
                        }
                    }
                    break;
                
                case 11:
                    System.out.print("Enter Language: ");
                    String language = sc.nextLine();
                    List<Movie> languageMovies = dao.getMoviesByLanguage(language);
                    if (languageMovies.isEmpty())
                    {
                        System.out.println("No movies found!");
                    }
                    else
                    {
                        System.out.println("\n----- Movies by Language -----");
                        for (Movie m : languageMovies)
                        {
                            m.displayMovie();
                        }
                    }
                    break;
                
                case 12:
                    List<Movie> allMovies = dao.getAllMovies();
                    System.out.println("\n----- All Movies -----");
                    
                    for (Movie m : allMovies)
                    {
                        m.displayMovie();
                    }
                    break;

                case 13:
                    List<Movie> sortedMovies = dao.getTopRatedMovies();
                    System.out.println("\n----- Movies Sorted By Rating -----");
                    for (Movie m : sortedMovies)
                    {
                        m.displayMovie();
                    }
                    break;

                case 14:
                    System.out.print("Enter Movie ID to add watchlist: ");
                    int watchId = sc.nextInt();
                    
                    dao.addToWatchlist(currentUserId, watchId);
                    break;

                case 15:
                    List<Movie> watchMovies = dao.viewWatchlist(currentUserId);
                    System.out.println("\n----- Watchlist -----");
                    if (watchMovies.isEmpty())
                    {
                        System.out.println("No movies in watchlist!");
                    }
                    else
                    {
                        for (Movie m : watchMovies)
                        {
                            m.displayMovie();
                        }
                    }
                    break;

                case 16:
                    System.out.println("Thank you!");
                    return;
            }
        }
    }
}