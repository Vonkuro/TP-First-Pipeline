package fr.epsi.netflix;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MovieFactory {

    /**
     * Formateur pour réaliser la convertion des dates.
     */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.US);

    /**
     * Instance unique de la classe
     */
    private static final MovieFactory INSTANCE = new MovieFactory();

    /**
     * Contrusteur privée afin d'éviter que d'autres instances soient créés.
     */
    private MovieFactory(){

    }


    /**
     * permet de créer une instance de <tt>{@link Movie}</tt>.
     *
     * @param items tableau contenant les données d'une serie
     * @return
     */
    public Movie createMovie(String[] items){
        int index =0;
        Movie movie = new Movie();
        movie.setId(items[index++]);
        movie.setType(items[index++]);
        movie.setTitle(items[index++]);
        movie.setDirector(items[index++]);
        movie.setCast(items[index++]);
        movie.setCountry(items[index++]);

        String dateAdded = items[index++];
        if(dateAdded != null && dateAdded.trim().length()>0) {
            movie.setDateAdded(formatter.parse(dateAdded.trim(), LocalDate::from));
        }
        movie.setReleaseYear(Integer.valueOf(items[index++]));
        movie.setRating(items[index++]);
        movie.setDuration(items[index++]);
        movie.setListedIn(items[index++]);
        movie.setDescription(items[index]);
        return movie;
    }

    /**
     * Permet de transformer une liste de chaines de caractères en liste de <tt>{@link Movie}</tt>
     *
     * @param datas liste contenant des representations de series.
     * @return
     */
    public List<Movie> createMovies(List<String> datas){
        return  datas.stream()
                .map(it -> it.split("\\|"))
                .map(it -> this.createMovie(it))
                .sorted()
                .collect(Collectors.toList());
    }

    public static MovieFactory getInstance(){
        return INSTANCE;
    }
}
