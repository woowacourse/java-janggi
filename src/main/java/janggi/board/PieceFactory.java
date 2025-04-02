package janggi.board;

import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Jol;
import janggi.piece.Ma;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.piece.Po;
import janggi.piece.Sa;
import janggi.piece.Sang;
import janggi.value.JanggiPosition;
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