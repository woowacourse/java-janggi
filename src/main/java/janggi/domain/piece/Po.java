package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Delta;
import janggi.domain.MovePath;
import janggi.domain.Position;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Optional;

public class Po implements Piece {

    private final TeamType teamType;
    private final PieceType pieceType;
    private final List<MovePath> paths;

    public Po(TeamType teamType) {
        this.teamType = teamType;
        pieceType = PieceType.PO;
        paths = List.of(
            new MovePath(List.of(Delta.createUp())),
            new MovePath(List.of(Delta.createDown())),
            new MovePath(List.of(Delta.createLeft())),
            new MovePath(List.of(Delta.createRight()))
        );
    }

    @Override
    public boolean isValidMovePattern(int startX, int startY, int endX, int endY) {
        return findMovePath(startX, startY, endX, endY).isPresent();
    }

    @Override
    public Optional<MovePath> findMovePath(int startX, int startY, int endX, int endY) {
        if (startX == endX && startY == endY) {
            return Optional.empty();
        }
        int dx = endX - startX;
        int dy = endY - startY;
        return paths.stream()
            .filter(path -> path.matchesDirection(dx, dy))
            .findFirst();
    }

    @Override
    public boolean isObstaclesNotExist(Position start, Position end, Board board) {
        Optional<MovePath> movePath = findMovePath(start.getX(), start.getY(), end.getX(), end.getY());
        if (movePath.isEmpty()) {
            return false;
        }
        List<Position> intermediatePositions = movePath.get().intermediatePositions(start, end);
        List<Piece> obstacles = intermediatePositions.stream()
            .map(board::findPiece)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
        if (obstacles.size() != 1) {
            return false;
        }
        if (obstacles.getFirst().getPieceType() == PieceType.PO) {
            return false;
        }
        Optional<Piece> targetPiece = board.findPiece(end);
        return targetPiece.isEmpty() || !(targetPiece.get().getPieceType() == PieceType.PO);
    }

    @Override
    public String nickname() {
        return pieceType.getNickname();
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public TeamType getTeamType() {
        return teamType;
    }
}
