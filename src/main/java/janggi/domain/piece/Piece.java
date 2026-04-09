package janggi.domain.piece;

import janggi.domain.board.Position;
import janggi.domain.game.Side;
import janggi.domain.route.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Piece {
    private static final Piece EMPTY_INSTANCE = new Piece(Side.NONE, PieceType.EMPTY, "0");

    private final Side side;
    private final PieceType pieceType;
    private final String pieceNumber;

    public Piece(Side side, PieceType pieceType, String pieceNumber) {
        this.pieceType = pieceType;
        this.side = side;
        this.pieceNumber = pieceNumber;
    }

    public Paths calculatePaths(Position current) {
        return pieceType.calculatePaths(current, side);
    }

    public boolean isSameSide(Piece other) {
        return this.side == other.side;
    }

    public boolean isOwnedBy(Side currentTurn) {
        return this.side == currentTurn;
    }

    public boolean isCannon() {
        return this.pieceType == PieceType.CANNON;
    }

    public boolean isGeneral() {
        return this.pieceType == PieceType.GENERAL;
    }

    public boolean isEmpty() {
        return this.pieceType == PieceType.EMPTY;
    }

    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState) {
        return pieceType.determineDestinations(routes, boardState, this);
    }

    public static Piece createEmpty() {
        return EMPTY_INSTANCE;
    }

    public Side getSide() {
        return side;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public String getPieceNumber() {
        return pieceNumber;
    }

    public double addToTotalScore(double currentTotal) {
        return pieceType.addToTotalScore(currentTotal);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return side == piece.side && pieceType == piece.pieceType && Objects.equals(pieceNumber,
                piece.pieceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, pieceType, pieceNumber);
    }
}
