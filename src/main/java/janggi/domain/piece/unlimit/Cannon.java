package janggi.domain.piece.unlimit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends UnLimitMovable {

    public Cannon(final Turn side) {
        super(side);
    }

    @Override
    public List<Position> addValidDestination(final List<Position> positions, final Map<Position, Piece> board) {
        List<Position> reachableDestinations = new ArrayList<>();
        boolean isJumped = false;
        for (Position position : positions) {
            Piece positionPiece = board.get(position);
            if (positionPiece.isCannon()) {
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
