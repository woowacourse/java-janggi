package janggi.domain.piece;

import janggi.domain.Location;
import janggi.domain.Side;
import janggi.domain.rule.Movement;
import java.util.List;
import java.util.Optional;

public class ActivePiece extends Piece {

    private final Movement movement;

    public ActivePiece(PieceType pieceType, Side side, Movement movement) {
        super(pieceType, side);
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
}
