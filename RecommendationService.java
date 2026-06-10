import java.util.List;

public class RecommendationService
{
    public void recommendMovies(String favoriteGenres, List<Movie> movies)
    {
        System.out.println("\nRecommended Movies\n");

        String[] genres = favoriteGenres.split(",");
        boolean found = false;

        for (Movie movie : movies) 
        {
            for (String genre : genres)
            {
                if (movie.getGenre().equalsIgnoreCase(genre.trim()) && movie.getRating() >= 8.0)
                {
                    System.out.println(movie.getTitle() + " | " + movie.getGenre() + " | " + movie.getRating());
                    found = true;
                    break;
                }
            }
        }
        if (!found)
        s{
            System.out.println("No recommended movies found!");
        }
    }
}