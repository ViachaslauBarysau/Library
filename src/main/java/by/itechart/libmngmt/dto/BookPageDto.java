package by.itechart.libmngmt.dto;

import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.entity.ReaderEntity;
import by.itechart.libmngmt.entity.ReaderCardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookPageDto {

    BookDto bookDto;
    List<ReaderCardDto> readerCards;
    Date nearestAvailableDate;
    Date nextNearestAvailableDate;
    int nearestAvailableDateID;

}
