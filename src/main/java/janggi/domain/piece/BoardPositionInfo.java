package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;

public record BoardPositionInfo(Board board, Position position, Team team) {

    public BoardPositionInfo movePosition(Position position) {
        return new BoardPositionInfo(board, position, team);
    }

    public boolean hasPiece() {
        return board.hasPiece(position);
    }

    public boolean isCannon() {
        return board.isCannon(position);
    }

    public boolean isNotSameSide() {
        return !board.isSameSide(team, position);
    }

    public boolean canNotMove(Vector vector) {
        return position.canNotMove(vector);
    }
}
