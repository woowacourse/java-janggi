package janggi.view.format;

import janggi.piece.Piece;
import janggi.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public class PieceEnglishFormat extends PieceFormat {

    private final Map<PieceType, String> pieceFormat;

    public PieceEnglishFormat() {
        final Map<PieceType, String> pieceFormat = new HashMap<>();
        pieceFormat.put(PieceType.KING, "K");
        pieceFormat.put(PieceType.GUARD, "G");
        pieceFormat.put(PieceType.ELEPHANT, "E");
        pieceFormat.put(PieceType.HORSE, "H");
        pieceFormat.put(PieceType.CANNON, "C");
        pieceFormat.put(PieceType.SOLDIER, "S");
        pieceFormat.put(PieceType.TANK, "T");
        this.pieceFormat = pieceFormat;
    }

    @Override
    protected String getPieceMessage(final Piece piece) {
        return pieceFormat.get(piece.getPieceType());
    }
}
