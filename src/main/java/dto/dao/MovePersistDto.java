package dto.dao;

import domain.game.Game;
import domain.game.MoveCommand;

public record MovePersistDto(
        long gameId,
        boolean inProgress,
        String turnTeam,
        double choScore,
        double hanScore,
        String winnerTeamOrNull,
        long movedPieceId,
        int toX,
        int toY,
        Long capturedPieceIdOrNull
) {
    public static MovePersistDto afterTurn(
            Game gameAfterMove,
            long gameId,
            MoveCommand move,
            long movedPieceId,
            Long capturedPieceIdOrNull
    ) {
        String winnerOrNull = winnerTeamOrNullAfterMove(gameAfterMove);
        return new MovePersistDto(
                gameId,
                gameAfterMove.isRunning(),
                gameAfterMove.currentTurn().name(),
                gameAfterMove.currentScore().cho(),
                gameAfterMove.currentScore().han(),
                winnerOrNull,
                movedPieceId,
                move.getTo().x(),
                move.getTo().y(),
                capturedPieceIdOrNull
        );
    }

    private static String winnerTeamOrNullAfterMove(Game gameAfterMove) {
        if (gameAfterMove.isRunning()) {
            return null;
        }
        return gameAfterMove.currentTurn().nextTurn().name();
    }
}
