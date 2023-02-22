package fr.epsi.netflix;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class MovieGenerator {

    public static void generateNeNIO(String file){
        try {
            List<String> datas = Files.readAllLines(Paths.get(file))
                    .stream()
                    .skip(1)
                    .collect(Collectors.toList());

            List<Movie> movies = MovieFactory.getInstance().createMovies(datas);
            
            HtmlFactory htmlFactory = new HtmlFactory();
            String html = htmlFactory.generateHtml(movies);

            Path folderOut = Path.of("out");
            if(!Files.exists(folderOut)){
                Files.createDirectory(folderOut);
            }

            Path target = Paths.get("out/netflix.html");
            Files.write(target, html.getBytes());

            Path folder = Paths.get("out/movies");
            folder.toFile().mkdirs();

            for(Movie movie:movies){
                Path fileMovie = Paths.get("out/movies", movie.getId()+".html");
                Files.write(fileMovie, htmlFactory.generateDetail(movie).getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if(args.length == 0){
            System.err.println("Utilisation : java -jar <lib> <fichier_csv>");
            System.exit(-1);
            return;
        }
        generateNeNIO(args[0]);
    }

}
