package dto.dao;

import domain.game.Game;
import domain.game.MoveCommand;

public final class MovePersistDto {
    private final long gameId;
    private final boolean inProgress;
    private final String turnTeam;
    private final double choScore;
    private final double hanScore;
    private final String winnerTeamOrNull;
    private final long movedPieceId;
    private final int toX;
    private final int toY;
    private final Long capturedPieceIdOrNull;

    private MovePersistDto(long gameId, boolean inProgress, String turnTeam, double choScore, double hanScore,
                           String winnerTeamOrNull, long movedPieceId, int toX, int toY,
                           Long capturedPieceIdOrNull) {
        this.gameId = gameId;
        this.inProgress = inProgress;
        this.turnTeam = turnTeam;
        this.choScore = choScore;
        this.hanScore = hanScore;
        this.winnerTeamOrNull = winnerTeamOrNull;
        this.movedPieceId = movedPieceId;
        this.toX = toX;
        this.toY = toY;
        this.capturedPieceIdOrNull = capturedPieceIdOrNull;
    }

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

    public long gameId() {
        return gameId;
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

    public String winnerTeamOrNull() {
        return winnerTeamOrNull;
    }

    public long movedPieceId() {
        return movedPieceId;
    }

    public int toX() {
        return toX;
    }

    public int toY() {
        return toY;
    }

    public Long capturedPieceIdOrNull() {
        return capturedPieceIdOrNull;
    }
}
