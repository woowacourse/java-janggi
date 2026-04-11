package janggi.view.label;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Arrays;

public enum PieceLabel {

    EMPTY(PieceType.EMPTY, "ㆍ"),
    GUNG(PieceType.GUNG, "궁"),
    SA(PieceType.SA, "사"),
    MA(PieceType.MA,"마"),
    SANG(PieceType.SANG, "상"),
    CHA(PieceType.CHA, "차"),
    PO(PieceType.PO, "포"),
    JOL(PieceType.JOL, "졸"),
    BYEONG(PieceType.BYEONG, "병"),
    ;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private final PieceType pieceType;
    private final String koreanNameFormat;

    PieceLabel(PieceType pieceType, String koreanNameFormat) {
        this.pieceType = pieceType;
        this.koreanNameFormat = koreanNameFormat;
    }

    public static String getPieceNameFormat(PieceType pieceType) {
        return Arrays.stream(PieceLabel.values())
                .filter(label -> label.pieceType.equals(pieceType))
                .findAny().map(label -> label.koreanNameFormat)
                .orElseThrow(IllegalArgumentException::new);
    }

    public static String getPieceFormatWithSideInfo(Piece piece) {
        return Arrays.stream(PieceLabel.values())
                .filter(label -> label.pieceType.equals(piece.getPieceType()))
                .findAny().map(label -> label.createPieceFormatBySide(piece))
                .orElseThrow(IllegalArgumentException::new);
    }

    private String createPieceFormatBySide(Piece piece) {
        if (piece.isEmpty()) {
            return koreanNameFormat;
        }
        if (piece.isSameSide(Side.HAN)) {
            return ANSI_RED + koreanNameFormat + ANSI_RESET;
        }
        if (piece.isSameSide(Side.CHO)) {
            return ANSI_BLUE + koreanNameFormat + ANSI_RESET;
        }
        return koreanNameFormat;
    }
}
