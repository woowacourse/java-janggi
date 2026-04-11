package team.janggi.domain.piece.strategy;

import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;
import team.janggi.domain.board.BoardStateReader;

public class HorseMoveStrategy implements MoveStrategy {
    public static final HorseMoveStrategy instance = new HorseMoveStrategy();

    @Override
    public boolean calculateMove(Position from, Position to, BoardStateReader stateReader) {
        return validateDirection(from, to) && isPathBlock(from, to, stateReader) && canKill(from, to, stateReader);
    }

    private boolean validateDirection(Position from, Position to) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
    }

    private boolean isPathBlock(Position from, Position to, BoardStateReader stateReader) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        int fromX = from.x();
        int fromY = from.y();
        if (dx > dy) {
            fromX += (to.x() - from.x()) / 2;
        }
        if (dy > dx) {
            fromY += (to.y() - from.y()) / 2;
        }

        Position obstaclePosition = new Position(fromX, fromY);
        Piece obstacle = stateReader.getPiece(obstaclePosition);
        return obstacle.isPieceType(PieceType.EMPTY);
    }

    private boolean canKill(Position from, Position to, BoardStateReader stateReader) {
        Piece currentPiece = stateReader.getPiece(from);
        Piece destinationPiece = stateReader.getPiece(to);
        return !currentPiece.isSameTeam(destinationPiece);
    }
}
