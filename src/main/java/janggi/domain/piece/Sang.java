package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.side.TeamType;
import java.util.List;
import java.util.Optional;

public class Sang implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Sang(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.SANG;
        paths = List.of(
            new MovePath(List.of(Delta.createUp(), Delta.createRightUp(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createUp(), Delta.createLeftUp(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createDown(), Delta.createRightDown(), Delta.createRightDown())),
            new MovePath(List.of(Delta.createDown(), Delta.createLeftDown(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftUp(), Delta.createLeftUp())),
            new MovePath(List.of(Delta.createLeft(), Delta.createLeftDown(), Delta.createLeftDown())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightUp(), Delta.createRightUp())),
            new MovePath(List.of(Delta.createRight(), Delta.createRightDown(), Delta.createRightDown()))
        );
    }

    @Override
    public boolean isValidMovePattern(int startX, int startY, int endX, int endY) {
        return findMovePath(startX, startY, endX, endY).isPresent();
    }

    @Override
    public Optional<MovePath> findMovePath(int startX, int startY, int endX, int endY) {
        int dx = endX - startX;
        int dy = endY - startY;
        return paths.stream()
            .filter(path -> path.matches(dx, dy))
            .findFirst();
    }

    @Override
    public boolean isValidPath(Position start, Position end, Board board) {
        Optional<MovePath> movePath = findMovePath(start.getX(), start.getY(), end.getX(), end.getY());
        if (movePath.isEmpty()) {
            return false;
        }
        return movePath.get().intermediatePositions(start, end).stream()
            .noneMatch(board::hasPiece);
    }

    @Override
    public String nickname() {
        return pieceType.getNickname();
    }

    @Override
    public boolean isSameType(TeamType nowTurn) {
        return nowTurn == teamType;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }
}
