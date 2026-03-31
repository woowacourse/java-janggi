package domain.place.piece;

import domain.place.Place;
import domain.place.moveStrategy.MoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Piece implements Place {

    private final Side side;
    private final MoveStrategy moveStrategy;
    private final PalaceMoveStrategy palaceMoveStrategy;

    public Piece(Side side, MoveStrategy moveStrategy, PalaceMoveStrategy palaceMoveStrategy) {
        this.side = side;
        this.moveStrategy = moveStrategy;
        this.palaceMoveStrategy = palaceMoveStrategy;
    }

    public abstract PieceSymbol getSymbol();

    @Override
    public String getFormat() {
        return getSymbol().display();
    }

    @Override
    public int getScore() {
        return getSymbol().getScore();
    }

    @Override
    public boolean isEmpty() {
        return false;
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
    public List<Position> getNormalPath(Position from) {
        return moveStrategy.getPath(from);
    }

    @Override
    public boolean canNormalMove(Map<Position, Place> obstacles, Position from, Position to) {
        return moveStrategy.canMove(obstacles, from, to, side);
    }

    @Override
    public List<Position> getPalacePath(Position from) {
        return palaceMoveStrategy.getPath(from);
    }

    @Override
    public boolean canPalaceMove(Map<Position, Place> obstacles, Position from, Position to) {
        return palaceMoveStrategy.canMove(obstacles, from, to, side);
    }

}
