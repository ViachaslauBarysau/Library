package by.itechart.libmngmt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookEntity {

    private int id;
    private List<String> covers;
    private String title;
    private List<String> authors;
    private String publisher;
    private int publishDate;
    private List<String> genres;
    private int pageCount;
    private String isbn;
    private String description;
    private int totalAmount;
    private int availableAmount;

}
