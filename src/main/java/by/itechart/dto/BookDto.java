package by.itechart.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class BookDto {

    private long id;
    private String tittle;
    private List<String> authors;
    private Date publishDate;
    private int totalAmount;

}
