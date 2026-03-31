package janggi.view.dto;

public record PieceDto(
        String name,
        String color
) {

    public String name() {
        return color + name;
    }

}
