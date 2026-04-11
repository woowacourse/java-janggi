package team.janggi.domain.piece.strategy;

import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;
import team.janggi.domain.piece.PieceType;
import team.janggi.domain.board.BoardStateReader;

public class ElephantMoveStrategy implements MoveStrategy {

    public static final ElephantMoveStrategy instance = new ElephantMoveStrategy();

    @Override
    public boolean calculateMove(Position from, Position to, BoardStateReader stateReader) {
        return validateDirection(from, to) && isPathBlock(from, to, stateReader) && canKill(from, to, stateReader);
    }

    private boolean validateDirection(Position from, Position to) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        return (dx == 3 && dy == 2) || (dx == 2 && dy == 3);
    }

    private boolean isPathBlock(Position from, Position to, BoardStateReader stateReader) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        int fromX = from.x();
        int fromY = from.y();
        if (dx > dy) {
            fromX += (to.x() - from.x()) / 3;
        }
        if (dy > dx) {
            fromY += (to.y() - from.y()) / 3;
        }

        Position obstaclePosition = new Position(fromX, fromY);
        Piece obstacle = stateReader.getPiece(obstaclePosition);
        if (!obstacle.isPieceType(PieceType.EMPTY)){
            return false;
        }

        int x2 = (to.x() - from.x()) / Math.abs(to.x() - from.x());
        int y2 = (to.y() - from.y()) / Math.abs(to.y() - from.y());

        Position obstaclePosition2 = new Position(fromX+x2, fromY+y2);
        Piece obstacle2 = stateReader.getPiece(obstaclePosition2);
        if (!obstacle2.isPieceType(PieceType.EMPTY)) {
            return false;
        }
        return true;
    }

    private boolean canKill(Position from, Position to, BoardStateReader stateReader) {
        Piece currentPiece = stateReader.getPiece(from);
        Piece destinationPiece = stateReader.getPiece(to);
        return !currentPiece.isSameTeam(destinationPiece);
    }

}
