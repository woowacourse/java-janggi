package domain.place.piece;

import domain.place.Place;
import domain.place.moveStrategy.MoveStrategy;
import domain.position.Position;
import java.util.Map;
import java.util.Optional;

public abstract class Piece implements Place {

    private final Side side;
    private final MoveStrategy moveStrategy;

    public Piece(Side side, MoveStrategy moveStrategy) {
        this.side = side;
        this.moveStrategy = moveStrategy;
    }

    public abstract PieceSymbol getSymbol();

    @Override
    public String getFormat() {
        return getSymbol().display();

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isSameSide(Place other) {
        return other.getSide()
                .map(this.side::equals)
                .orElse(false);
    }

    @Override
    public boolean isSameSymbol(PieceSymbol pieceSymbol) {
        return getSymbol() == pieceSymbol;
    }

    @Override
    public boolean hasSide(Side side) {
        return this.side.equals(side);
    }

    @Override
    public Optional<Side> getSide() {
        return Optional.of(side);
    }

    @Override
    public boolean canMove(Map<Position, Place> obstacles, Position from, Position to) {
        return moveStrategy.canMove(obstacles, from, to);
    }
}
