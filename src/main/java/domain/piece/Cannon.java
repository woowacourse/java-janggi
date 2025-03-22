package domain.piece;

import domain.position.Column;
import domain.position.Direction;
import domain.Path;
import domain.position.Position;
import domain.position.Row;
import domain.TeamType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cannon extends Piece {
    private static final List<Path> PATHS;

    static {
        PATHS = List.of(
                new Path(new ArrayList<>(Collections.nCopies(Row.MAX_ROW, Direction.DOWN))),
                new Path(new ArrayList<>(Collections.nCopies(Row.MAX_ROW, Direction.UP))),
                new Path(new ArrayList<>(Collections.nCopies(Column.MAX_COLUMN, Direction.LEFT))),
                new Path(new ArrayList<>(Collections.nCopies(Column.MAX_COLUMN, Direction.RIGHT)))
        );
    }

    public Cannon(Position position, TeamType teamType) {
        super(position, teamType);
    }

    private Cannon(Cannon cannon) {
        super(cannon);
    }

    @Override
    protected void validateMovePath(List<Position> pathPositions, List<Piece> alivePieces) {
        if(hasCannonOnPath(pathPositions, alivePieces)){
            throw new IllegalArgumentException("포는 뛰어넘을 수 없습니다.");
        }

        int jumpPieceCount = countBlockedPiece(pathPositions, alivePieces);
        if(jumpPieceCount != 1){
            throw new IllegalArgumentException("포는 하나의 기물을 넘어야 합니다.");
        }
    }

    private boolean hasCannonOnPath(List<Position> pathPositions, List<Piece> alivePieces) {
        return pathPositions.stream()
                .anyMatch(position -> hasCannonPieceTo(position, alivePieces));
    }

    private boolean hasCannonPieceTo(Position position, List<Piece> alivePieces) {
        return alivePieces.stream()
                .anyMatch(piece -> piece.hasSamePosition(position));
    }

    private boolean hasPieceTo(Position position, List<Piece> alivePieces) {
        return alivePieces.stream()
                .anyMatch(piece -> piece.hasSamePosition(position));
    }

    private int countBlockedPiece(List<Position> intermediatePositions, List<Piece> alivePieces) {
        return (int) intermediatePositions.stream()
                .filter(position -> hasPieceTo(position, alivePieces))
                .count();
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    @Override
    public Piece newInstance() {
        return new Cannon(this);
    }

    @Override
    protected List<Path> getPaths() {
        return PATHS;
    }
}
