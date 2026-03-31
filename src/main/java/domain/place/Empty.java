package domain.place;

import domain.place.piece.PieceSymbol;
import domain.place.piece.Side;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Empty implements Place {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean hasSide(Side side) {
        return false;
    }

    @Override
    public boolean isSameSymbol(PieceSymbol pieceSymbol) {
        return false;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public Optional<Side> getSide() {
        return Optional.empty();
    }

    @Override
    public String getFormat() {
        return "．";
    }

    @Override
    public List<Position> getNormalPath(Position from) {
        return List.of();
    }

    @Override
    public List<Position> getPalacePath(Position from) {
        return List.of();
    }

    @Override
    public boolean canNormalMove(Map<Position, Place> obstacles, Position from, Position to) {
        return false;
    }

    @Override
    public boolean canPalaceMove(Map<Position, Place> obstacles, Position from, Position to) {
        return false;
    }
}
