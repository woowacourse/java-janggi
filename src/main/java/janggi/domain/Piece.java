package janggi.domain;

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
        return pieceType.determineDestinations(routes, boardState, toVO());
    }

    private PieceVO toVO() {
        return new PieceVO(side, pieceType, pieceNumber);
    }
}
