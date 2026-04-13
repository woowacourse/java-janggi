package model.testdouble;

import java.util.List;
import model.Team;
import model.coordinate.Position;
import model.piece.Piece;
import model.piece.PieceType;

public class FakePiece extends Piece {

    private final List<Position> path;
    private final double score;

    FakePiece(Team team, PieceType type, List<Position> path, double score) {
        super(team, type);
        this.path = path;
        this.score = score;
    }

    public static FakePiece createFake(Team team) {
        return new FakePiece(team, PieceType.SOLDIER, List.of(), 0.0);
    }

    public static FakePiece createFakeWithScore(Team team, double score) {
        return new FakePiece(team, PieceType.SOLDIER, List.of(), score);
    }

    @Override
    public List<Position> pathTo(Position current, Position next) {
        return path;
    }

    @Override
    protected void validateMove(Position current, Position next) {

    }

    @Override
    public double score() {
        return score;
    }
}
