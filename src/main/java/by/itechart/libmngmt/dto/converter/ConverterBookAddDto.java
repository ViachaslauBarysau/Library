package by.itechart.libmngmt.dto.converter;


import by.itechart.libmngmt.dto.BookAddDto;
import by.itechart.libmngmt.entity.Book;

public class ConverterBookAddDto {
    public static BookAddDto bookToBookAddDtoConverter (Book book){
        BookAddDto bookAddDto = new BookAddDto();
        bookAddDto.builder().id(book.getId())
                .authors(book.getAuthors())
                .covers(book.getCovers())
                .genres(book.getGenres())
                .build();
        return bookAddDto;
    }
}
