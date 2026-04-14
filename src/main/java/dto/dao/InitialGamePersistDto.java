package dto.dao;

import domain.game.Game;
import java.util.List;

public record InitialGamePersistDto(
        boolean inProgress,
        String turnTeam,
        double choScore,
        double hanScore,
        List<PiecePlacement> placements
) {
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
}
