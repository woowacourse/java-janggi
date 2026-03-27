package janggi.dto;

public record PieceDto(
        String name,
        String color
) {

    public String name() {
        return color + name;
    }

}
