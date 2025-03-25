package janggi.domain.gameState;

import janggi.domain.board.Position;
import janggi.domain.piece.TeamColor;
import janggi.domain.piece.PieceType;

public class Finished implements State{
    private final TeamColor turnColor;

    public Finished(TeamColor turnColor) {
        this.turnColor = turnColor;
    }

    @Override
    public State movePiece(PieceType pieceType, Position source, Position destination) {
        throw new IllegalArgumentException("게임이 끝난 후에는 움직일 수 없습니다.");
    }

    @Override
    public TeamColor getColor() {
        return turnColor;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
