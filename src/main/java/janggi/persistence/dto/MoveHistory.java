package janggi.persistence.dto;

public record MoveHistory(
    int turnNumber,
    int startX,
    int startY,
    int endX,
    int endY
) {
}
