package dto;

public record JanggiGameDto(Long id, String currentTurn, String winnerTeam) {

    public static JanggiGameDto of(final Long id, final String currentTurn, final String winnerTeam) {
        return new JanggiGameDto(id, currentTurn, winnerTeam);
    }
}
