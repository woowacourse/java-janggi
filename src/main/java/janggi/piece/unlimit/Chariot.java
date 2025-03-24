package janggi.piece.unlimit;

import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Side;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chariot extends UnLimitMovable {

    public Chariot(final Side side) {
        super(side);
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }

    @Override
    public List<Position> addValidDestination(final List<Position> positions, final Map<Position, Piece> board) {
        List<Position> reachableDestinations = new ArrayList<>();
        for (Position position : positions) {
            Piece targetPiece = board.get(position);
            if (position.isOutOfRange() || isAlly(targetPiece)) {
                break;
            }
            reachableDestinations.add(position);
            if (targetPiece.isOccupied() && !isAlly(targetPiece)) {
                break;
            }
        }
        return reachableDestinations;
    }
}
