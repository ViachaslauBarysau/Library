package by.itechart.libmngmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents a BookDto.
 */
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
    private String isbn;
    private String description;
    private int totalAmount;
    private int availableAmount;
    private List<String> authors;
    private List<String> genres;
    private List<String> covers;
    private List<ReaderCardDto> readerCardDtos;
}
