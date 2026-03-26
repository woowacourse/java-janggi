package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.route.Paths;
import java.util.List;
import java.util.Map;

public class Piece {

    private final Side side;
    private final PieceType pieceType;
    private final String pieceNumber;

    public Piece(Side side, PieceType pieceType, String pieceNumber) {
        this.pieceType = pieceType;
        this.side = side;
        this.pieceNumber = pieceNumber;
    }

    public Paths calculatePaths(Position current) {
        return pieceType.calculatePaths(current);
    }

    public List<Position> determineDestinations(Paths routes, Map<Position, PieceVO> boardState) {
        return pieceType.determineDestinations(routes, boardState, mapToVO());
    }

    public PieceVO mapToVO() {
        return new PieceVO(side, pieceType, pieceNumber);
    }
}
