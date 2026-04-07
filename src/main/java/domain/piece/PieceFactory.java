package domain.piece;

import domain.country.CountryType;
import domain.strategy.CannonMoveStrategy;
import domain.strategy.ElephantMoveStrategy;
import domain.strategy.HorseMoveStrategy;
import domain.strategy.InsidePalaceMoveStrategy;
import domain.strategy.SoldierMoveStrategy;
import domain.strategy.StraightMoveStrategy;
import java.util.function.Function;

public enum PieceFactory {
    SOLDIER(countryType -> new Piece(new PieceInfo(PieceType.SOLDIER, countryType), new SoldierMoveStrategy())),
    GUARD(countryType -> new Piece(new PieceInfo(PieceType.GUARD, countryType), new InsidePalaceMoveStrategy())),
    ELEPHANT(countryType -> new Piece(new PieceInfo(PieceType.ELEPHANT, countryType), new ElephantMoveStrategy())),
    HORSE(countryType -> new Piece(new PieceInfo(PieceType.HORSE, countryType), new HorseMoveStrategy())),
    CANNON(countryType -> new Piece(new PieceInfo(PieceType.CANNON, countryType), new CannonMoveStrategy())),
    CHARIOT(countryType -> new Piece(new PieceInfo(PieceType.CHARIOT, countryType), new StraightMoveStrategy())),
    GENERAL(countryType -> new Piece(new PieceInfo(PieceType.GENERAL, countryType), new InsidePalaceMoveStrategy())),
    ;

    private final Function<CountryType, Piece> function;

    PieceFactory(Function<CountryType, Piece> function) {
        this.function = function;
    }

    public Piece create(CountryType countryType) {
        return function.apply(countryType);
    }
}
