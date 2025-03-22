package dto;

@FunctionalInterface
public interface Converter<T> {
    T convert(T request);
}
