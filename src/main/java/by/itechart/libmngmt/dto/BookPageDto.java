package by.itechart.libmngmt.dto;

import by.itechart.libmngmt.entity.Book;
import by.itechart.libmngmt.entity.Reader;
import by.itechart.libmngmt.entity.ReaderCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookPageDto {

    Book book;
    Map<Integer, Reader> readers;
    List<ReaderCard> readerCards;

}
