package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;

import java.util.ArrayList;
import java.util.List;

public class Chariot extends MoveablePiece {
    private static final String PIECE_NAME = "차";

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof Chariot;
    }

    @Override
    public String getDisplayName() {
        return PIECE_NAME;
    }

    @Override
    public List<Position> getPath(Position from, Position to) {
        validateMove(from, to);
        return findPath(from, to);
    }

    @Override
    public boolean canMove(List<Piece> piecesOnPath, Piece endPiece) {
        validateAllPieceEmpty(piecesOnPath);
        validateSameTeam(endPiece);
        return true;
    }

    private List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Position target = from.moveStraight(to);
        while (target.hasOnlyStraightMove(to)) {
            path.add(target);
            target = target.moveStraight(to);
        }
        return path;
    }

    private void validateMove(Position from, Position to) {
        if (!from.hasOnlyStraightMove(to)) {
            throw new IllegalArgumentException("[ERROR] 차는 직선으로만 이동할 수 있습니다.");
        }
    }

    private void validateAllPieceEmpty(List<Piece> piecesOnPath) {
        if (!piecesOnPath.stream().allMatch(Piece::isEmptyPiece)) {
            throw new IllegalArgumentException("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
