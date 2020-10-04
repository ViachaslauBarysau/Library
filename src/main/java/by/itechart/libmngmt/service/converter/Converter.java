package by.itechart.libmngmt.service.converter;

public interface Converter<A, B> {
    A convertToDto(B object);

    B convertToEntity(A object);
}
