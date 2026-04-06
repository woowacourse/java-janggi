package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.board.Position;
import java.util.List;
import java.util.Map;

public abstract class Piece {
    private final Team team;
    private final Name name;

    public Piece(Team team, Name name) {
        this.team = team;
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public abstract boolean canMoveByBasicMovingRule(Position from, Position to);

    public abstract List<Position> findPath(Position from, Position to);

    public boolean canMove(Position from, Position to, Board board) {
        if (!canMoveByBasicMovingRule(from, to)) {
            return false;
        }
        Map<Position, Piece> piecesOnPath = board.findPiecesOn(findPath(from, to));
        return canMoveBySpecialMovingRule(piecesOnPath, to);
    }

    abstract public boolean canMoveBySpecialMovingRule(Map<Position, Piece> positionPieces, Position to);
}
