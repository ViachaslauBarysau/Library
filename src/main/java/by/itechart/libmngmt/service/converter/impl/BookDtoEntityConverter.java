package by.itechart.libmngmt.service.converter.impl;

import by.itechart.libmngmt.dto.BookDto;
import by.itechart.libmngmt.entity.BookEntity;
import by.itechart.libmngmt.service.converter.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookDtoEntityConverter implements Converter<BookDto, BookEntity> {
    private static volatile BookDtoEntityConverter instance;

    public static synchronized BookDtoEntityConverter getInstance() {
        BookDtoEntityConverter localInstance = instance;
        if (localInstance == null) {
            synchronized (BookDtoEntityConverter.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BookDtoEntityConverter();
                }
            }
        }
        return localInstance;
    }

    /**
     * Converts BookEntity to BookDto.
     *
     * @param bookEntity BookEntity object
     * @return BookDto object
     */
    @Override
    public BookDto convertToDto(final BookEntity bookEntity) {
        return BookDto.builder()
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
    }

    /**
     * Converts BookDto to BookEntity.
     *
     * @param bookDto BookDto object
     * @return BookEntity object
     */
    @Override
    public BookEntity convertToEntity(final BookDto bookDto) {
        return BookEntity.builder()
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
    }
}
