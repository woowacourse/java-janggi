package janggi.domain.board;

import janggi.domain.piece.Byeong;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;
import janggi.domain.value.JanggiPosition;
import java.util.Map;
import java.util.function.Function;

public class PieceFactory {
    private static final Map<PieceType, Function<JanggiPosition, Piece>> PIECE_FACTORY = Map.of(
            PieceType.GUNG, Gung::from,
            PieceType.MA, Ma::from,
            PieceType.CHA, Cha::from,
            PieceType.PO, Po::from,
            PieceType.SANG, Sang::from,
            PieceType.SA, Sa::from,
            PieceType.JOL, Jol::from,
            PieceType.BYEONG, Byeong::from
    );

    public static Piece createPiece(PieceType pieceType, JanggiPosition position) {
        return PIECE_FACTORY.getOrDefault(pieceType, pos -> null).apply(position);
    }
}