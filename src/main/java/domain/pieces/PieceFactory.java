package domain.pieces;

import java.util.Map;
import java.util.function.Function;

import domain.enums.Country;
import domain.enums.PieceType;

public class PieceFactory {
    private static final Map<PieceType, Function<Country, Piece>> factory = Map.of(
            PieceType.CHA, Cha::new,
            PieceType.MA, Ma::new,
            PieceType.SANG, Sang::new,
            PieceType.SA, Sa::new,
            PieceType.JANG, Jang::new,
            PieceType.PO, Po::new,
            PieceType.JOL, Jol::new,
            PieceType.NONE, c -> None.INSTANCE
    );

    public static Piece createPiece(PieceType pieceType,Country country){
        return factory.get(pieceType).apply(country);
    }
}
