package janggi.factory;

import janggi.domain.PalaceTopology;
import janggi.domain.Side;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Ma;
import janggi.domain.piece.None;
import janggi.domain.piece.Pawn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;

import java.util.Map;
import java.util.function.BiFunction;

public class PieceFactory {
    private static final Map<PieceType, BiFunction<Side, PalaceTopology, Piece>> pieceMap = Map.of(
            PieceType.CHA, Cha::new,
            PieceType.GUNG, Gung::new,
            PieceType.MA, Ma::new,
            PieceType.NONE, (side, palaceTopology) -> new None(),
            PieceType.PAWN, Pawn::new,
            PieceType.PO, Po::new,
            PieceType.SA, Sa::new,
            PieceType.SANG, Sang::new
    );

    private final PalaceTopology palaceTopology;

    public PieceFactory(PalaceTopology palaceTopology) {
        this.palaceTopology = palaceTopology;
    }

    public Piece create(PieceType pieceType, Side side) {
        return pieceMap.get(pieceType).apply(side, palaceTopology);
    }
}
