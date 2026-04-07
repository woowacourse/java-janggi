package dto;

public record PositionHistory(
        int id,
        int x,
        int y,
        String pieceType,
        String countryType,
        String turn
) {
}
