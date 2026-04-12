package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.Movement;
import java.util.List;
import java.util.Optional;

public class ActivePiece implements Piece {

    private final PieceType pieceType;
    private final Side side;
    private final Movement movement;

    public ActivePiece(PieceType pieceType, Side side, Movement movement) {
        this.pieceType = pieceType;
        this.side = side;
        this.movement = movement;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        Optional<List<Location>> calculatedRoute = movement.calculateRoute(from, to);
        if (calculatedRoute.isEmpty()) {
            throw new IllegalArgumentException(
                    String.format("%s 기물 이동 규칙 위반: 해당 위치%s에 도달할 수 없습니다.",
                            pieceType.getNameFormat(), to.getFormattedLocation())
            );
        }

        return calculatedRoute.get();
    }

    @Override
    public void detectCollision(List<Piece> piecesOnPath) {
        movement.detectCollision(side, piecesOnPath);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isPo() {
        return this.pieceType == PieceType.PO;
    }

    @Override
    public boolean isSameSide(Side side) {
        return this.side.equals(side);
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Side getSide() {
        return side;
    }
}
