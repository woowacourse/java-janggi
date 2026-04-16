package dto.dao;

import domain.game.Game;
import java.util.List;

public final class InitialGamePersistDto {
    private final boolean inProgress;
    private final String turnTeam;
    private final double choScore;
    private final double hanScore;
    private final List<PiecePlacement> placements;

    private InitialGamePersistDto(boolean inProgress, String turnTeam, double choScore, double hanScore,
                                  List<PiecePlacement> placements) {
        this.inProgress = inProgress;
        this.turnTeam = turnTeam;
        this.choScore = choScore;
        this.hanScore = hanScore;
        this.placements = placements;
    }

    public static InitialGamePersistDto from(Game game) {
        List<PiecePlacement> placements = game.getBoardState().getBoardState().stream()
                .filter(PiecePlacement::isOccupied)
                .map(PiecePlacement::from)
                .toList();
        return new InitialGamePersistDto(
                game.isRunning(),
                game.currentTurn().name(),
                game.currentScore().cho(),
                game.currentScore().han(),
                placements
        );
    }

    public boolean inProgress() {
        return inProgress;
    }

    public String turnTeam() {
        return turnTeam;
    }

    public double choScore() {
        return choScore;
    }

    public double hanScore() {
        return hanScore;
    }

    public List<PiecePlacement> placements() {
        return placements;
    }
}
