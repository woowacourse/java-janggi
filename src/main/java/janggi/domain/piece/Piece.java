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

    public boolean belongsTo(Team team) {
        return this.team == team;
    }

    public boolean isGeneral() {
        return this.name == Name.GENERAL;
    }

    public boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public int score() {
        return name.score();
    }

    protected boolean canMoveStraight(Position from, Position to) {
        return (from.isSameX(to) && !from.isSameY(to))
                || (!from.isSameX(to) && from.isSameY(to));
    }

    protected List<Position> findStraightPath(Position from, Position to) {
        List<Position> path = new java.util.ArrayList<>();
        int stepX = Integer.compare(to.x(), from.x());
        int stepY = Integer.compare(to.y(), from.y());
        Position currentPosition = from;

        while (!currentPosition.equals(to)) {
            currentPosition = currentPosition.moveBy(stepX, stepY);
            path.add(currentPosition);
        }
        return path;
    }

    protected boolean canCaptureDestinationPiece(Map<Position, Piece> positionPieces, Position to) {
        if (!positionPieces.containsKey(to)) {
            return true;
        }
        Piece destinationPiece = positionPieces.get(to);
        return !isSameTeam(destinationPiece);
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
