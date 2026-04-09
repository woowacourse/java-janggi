package dto;

public record JanggiGameDto(Long id, String currentTurn, String winnerTeam) {

    public static JanggiGameDto of(Long id, String currentTurn, String winnerTeam) {
        return new JanggiGameDto(id, currentTurn, winnerTeam);
    }
}
