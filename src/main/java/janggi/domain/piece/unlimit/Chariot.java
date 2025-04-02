package janggi.domain.piece.unlimit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chariot extends UnLimitMovable {

    public Chariot(final Turn side) {
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
            if (isAlly(targetPiece)) {
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
