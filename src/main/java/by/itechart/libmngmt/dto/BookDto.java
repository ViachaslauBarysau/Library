package by.itechart.libmngmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.text.DateFormat;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookDto {

    private int id;
    private String title;
    private String publisher;
    private int publishDate;
    private int pageCount;
    private String ISBN;
    private String description;
    private int totalAmount;
    private int availableAmount;
    private List<String> authors;
    private List<String> genres;
    private List<String> covers;


}
