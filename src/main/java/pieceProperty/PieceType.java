package pieceProperty;

public enum PieceType {

    BYEONG,
    CHA,
    JANGGUN,
    JOL,
    MA,
    PO,
    SA,
    SANG;

    public static PieceType getPieceTypeBy(String pieceType) {
        if (pieceType.equals("BYEONG")) {
            return BYEONG;
        }

        if (pieceType.equals("CHA")) {
            return CHA;
        }

        if (pieceType.equals("JANGGUN")) {
            return JANGGUN;
        }

        if (pieceType.equals("JOL")) {
            return JOL;
        }

        if (pieceType.equals("MA")) {
            return MA;
        }

        if (pieceType.equals("SANG")) {
            return SANG;
        }

        if (pieceType.equals("PO")) {
            return PO;
        }

        return SA;
    }

}
