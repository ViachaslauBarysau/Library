package by.itechart.libmngmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Represents a BookPageDto.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookPageDto {
    private BookDto bookDto;
    private List<ReaderCardDto> readerCards;
    private Date nearestAvailableDate;
}
