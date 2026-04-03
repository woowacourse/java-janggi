package domain.pieces;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;
import domain.Position;
import domain.strategy.StraightMovement;
import domain.strategy.MoveStrategy;

public class None extends Piece {
    public static final None INSTANCE = new None();

    public None() {
        super(null, PieceType.NONE);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        return true;
    }

    @Override
    public List<Position> getAvailableRoute(Position start,Direction direction){
        return Collections.emptyList();
    }

    @Override
    public Optional<Position> move(Position start, Direction direction, Country country) {
        MoveStrategy moveStraight = new StraightMovement();
        return moveStraight.move(start, direction, country);
    }


    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return true;
    }
}
