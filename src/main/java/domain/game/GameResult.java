package domain.game;

public record GameResult(Team winner, double choScore, double hanScore) {
    public boolean isDraw() {
        return winner == Team.NONE;
    }
}
