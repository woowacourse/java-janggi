package model.game;

import model.board.Country;
import model.pieces.Piece;
import model.pieces.PieceType;

public class GameStatus {
    private final boolean finished;
    private final Country winner;

    private GameStatus(boolean finished, Country winner) {
        this.finished = finished;
        this.winner = winner;
    }

    public static GameStatus playing() {
        return new GameStatus(false, null);
    }

    public static GameStatus restore(boolean finished, Country winner) {
        return new GameStatus(finished, winner);
    }

    public GameStatus update(Piece capturedPiece, Country currentTurn) {
        if (capturedPiece == null) {
            return this;
        }
        if (capturedPiece.pieceType() == PieceType.GENERAL) {
            return new GameStatus(true, currentTurn);
        }
        return this;
    }

    public void validateNotFinished() {
        if (finished) {
            throw new IllegalArgumentException("[ERROR] 이미 종료된 게임입니다.");
        }
    }

    public Country winner() {
        return winner;
    }

    public boolean isFinished() {
        return finished;
    }
}
