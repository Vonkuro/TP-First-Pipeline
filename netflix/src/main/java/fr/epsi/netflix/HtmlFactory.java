package fr.epsi.netflix;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Factory permettant de générer du contenu html
 */
public class HtmlFactory {

    /**
     * Represente une entete.
     */
    private static final String HEADER_ROW = "<th>%s</th>";


    private static final String HEADER_PAGE = "<!doctype html><head> <meta charset=\"utf-8\"><title>%s</title></head><body>";
    private static final String FOOTER_PAGE = "</body></html>";

    /**
     * retpresente un cellule du tableau
     */
    private static final String ROW = "<td>%s</td>";
    private static final String ROW_WITH_LINK = "<td><a href='movies/%s.html'>detail</td>";

    private static final String ROW_DETAIL = "<div><p>%1$s</p><p>%2$s</p></div>";

    public String generateDetail(Movie movie){
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(HEADER_PAGE, movie.getTitle()));
        builder.append(String.format(ROW_DETAIL, "Titre", movie.getTitle()));
        builder.append(String.format(ROW_DETAIL, "Desccription", movie.getDescription()));
        builder.append(FOOTER_PAGE);
        return builder.toString();
    }

    public String generateHtml(List<Movie> movies){
        // Utilisation de StringBuilder pour les perfs.
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(HEADER_PAGE, "Films/Series"));
        builder.append("<style> table, table tr, table td, table th {" +
                "border:1px solid black;border-collapse:collapse}" +
                "</style>" +
                "<table>");
        builder.append("<tr>");
        builder.append(String.format(HEADER_ROW, "id"));
        builder.append(String.format(HEADER_ROW, "type"));
        builder.append(String.format(HEADER_ROW, "titre"));
        builder.append(String.format(HEADER_ROW, "Réalisateur"));
        builder.append(String.format(HEADER_ROW, "Pays"));
        builder.append(String.format(HEADER_ROW, "Date"));
        builder.append(String.format(HEADER_ROW, "Detail"));
        builder.append("</tr>");

        for(Movie movie: movies){
            builder.append("<tr>");
            builder.append(String.format(ROW, movie.getId()));
            builder.append(String.format(ROW, movie.getType()));
            builder.append(String.format(ROW, movie.getTitle()));
            builder.append(String.format(ROW, movie.getDirector()));
            builder.append(String.format(ROW, movie.getCountry()));

            if( movie.getDateAdded() != null) {
                builder.append(String.format(ROW, movie.getDateAdded().format(DateTimeFormatter.ISO_LOCAL_DATE)));
            }else{
                builder.append(String.format(ROW, ""));
            }
            builder.append(String.format(ROW_WITH_LINK, movie.getId()));
            builder.append("</tr>");
        }

        builder.append("</table>");
        builder.append(FOOTER_PAGE);
        return builder.toString();
    }
}
