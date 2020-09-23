package by.itechart.libmngmt.util.converter;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;

public class BookConverter {
    public static BookDto convertBookEntityToBookDto(BookEntity bookEntity) {
        BookDto bookDto = BookDto.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .authors(bookEntity.getAuthors())
                .genres(bookEntity.getGenres())
                .covers(bookEntity.getCovers())
                .publisher(bookEntity.getPublisher())
                .publishDate(bookEntity.getPublishDate())
                .pageCount(bookEntity.getPageCount())
                .isbn(bookEntity.getIsbn())
                .description(bookEntity.getDescription())
                .totalAmount(bookEntity.getTotalAmount())
                .availableAmount(bookEntity.getAvailableAmount())
                .build();
        return bookDto;
    }

    public static BookEntity convertBookDtoToBookEntity(BookDto bookDto) {
        BookEntity bookEntity = BookEntity.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .authors(bookDto.getAuthors())
                .genres(bookDto.getGenres())
                .covers(bookDto.getCovers())
                .publisher(bookDto.getPublisher())
                .publishDate(bookDto.getPublishDate())
                .pageCount(bookDto.getPageCount())
                .isbn(bookDto.getIsbn())
                .description(bookDto.getDescription())
                .totalAmount(bookDto.getTotalAmount())
                .availableAmount(bookDto.getAvailableAmount())
                .build();
        return bookEntity;
    }
}
