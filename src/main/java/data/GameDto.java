package data;

public record GameDto(
        Long id,
        String playerCho,
        String playerHan,
        String currentTurn,
        boolean status
) {
}
