package by.itechart.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Book {

    private long id;
    private List<String> cover;
    private String title;
    private List<String> authors;
    private String publisher;
    private int publishDate;
    private List<String> genres;
    private int pageCount;
    private String ISBN;
    private String description;
    private int totalAmount;
    private int availableAmount;

}
