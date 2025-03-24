package janggi.piece.unlimit;

import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends UnLimitMovable {

    public Cannon(final Side side) {
        super(side);
    }

    @Override
    public List<Position> addValidDestination(final List<Position> positions, final Map<Position, Piece> board) {
        List<Position> reachableDestinations = new ArrayList<>();
        boolean isJumped = false;
        for (Position position : positions) {
            Piece positionPiece = board.get(position);
            if (position.isOutOfRange() || positionPiece.isCannon()) {
                break;
            }
            if (!isJumped && positionPiece.isOccupied()) {
                isJumped = true;
                continue;
            }

            if(isJumped) {
                reachableDestinations.addAll(filterValidDestination(position, positionPiece));
                if(positionPiece.isOccupied()) {
                    break;
                }
            }
        }
        return reachableDestinations;
    }

    private List<Position> filterValidDestination(final Position position, final Piece positionPiece) {
        if(!positionPiece.isOccupied() || !isAlly(positionPiece)) {
            return List.of(position);
        }
        return List.of();
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }
}
