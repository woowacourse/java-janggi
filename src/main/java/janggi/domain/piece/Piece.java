package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.movestrategy.MoveStrategy;

import java.util.List;

public class Piece {
    private final Team team;
    private final MoveStrategy moveStrategy;
    private final PieceType pieceType;

    public Piece(Team team, MoveStrategy moveStrategy) {
        this.team = team;
        this.moveStrategy = moveStrategy;
        this.pieceType = moveStrategy.getIdentity();
    }

    public String getPieceName() {
        return pieceType.getType();
    }

    public String getTeamName() {
        return team.name();
    }

    public boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public Team getTeam() {
        return team;
    }

    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean canMove(Position from, Position to) {
        return moveStrategy.canMove(from, to);
    }

    public List<Position> findPath(Position from, Position to) {
        return moveStrategy.findPath(from, to);
    }

    public boolean checkPathRule(List<Piece> pathPieces) {
        return moveStrategy.checkPathRule(pathPieces);
    }

    public boolean canCapture(Piece from, Piece to) {
        return moveStrategy.canCapture(from, to);
    }
}
