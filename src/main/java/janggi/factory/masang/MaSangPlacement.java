package janggi.factory.masang;

import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceBehavior;
import janggi.domain.piece.behavior.rotatemove.Elephant;
import janggi.domain.piece.behavior.rotatemove.Horse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class MaSangPlacement {

    public Map<Position, Piece> generate(Side side) {
        Map<Position, Piece> pieceMap = new HashMap<>();

        pieceMap.putAll(placePieces(getHorsePositionByCho(), side, Horse::new));
        pieceMap.putAll(placePieces(getElephantPositionByCho(), side, Elephant::new));

        return pieceMap;
    }

    private Map<Position, Piece> placePieces(Set<Position> positions, Side side, Supplier<PieceBehavior> supplier) {
        Map<Position, Piece> pieceMap = new HashMap<>();

        for (Position position : parsePositionsBySide(positions, side)) {
            Piece piece = new Piece(side, supplier.get());
            pieceMap.put(position, piece);
        }

        return pieceMap;
    }

    private Set<Position> parsePositionsBySide(Set<Position> positions, Side side) {
        if (side == Side.CHO) {
            return positions;
        }

        return positions.stream()
                .map(Position::changeToHan)
                .collect(Collectors.toUnmodifiableSet());
    }

    protected abstract Set<Position> getHorsePositionByCho();

    protected abstract Set<Position> getElephantPositionByCho();
}
