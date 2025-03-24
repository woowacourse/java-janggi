package domain.constant;

import domain.piece.*;

public class JanggiPieceConstant {

    public static final JanggiPiece CHO_궁 = new JanggiPiece(JanggiSide.CHO, JanggiPieceType.KING);
    public static final JanggiPiece HAN_궁 = new JanggiPiece(JanggiSide.HAN, JanggiPieceType.KING);
    public static final JanggiPiece CHO_마 = new JanggiPiece(JanggiSide.CHO, JanggiPieceType.HORSE);
    public static final JanggiPiece HAN_마 = new JanggiPiece(JanggiSide.HAN, JanggiPieceType.HORSE);
    public static final JanggiPiece CHO_사 = new JanggiPiece(JanggiSide.CHO, JanggiPieceType.ADVISOR);
    public static final JanggiPiece HAN_사 = new JanggiPiece(JanggiSide.HAN, JanggiPieceType.ADVISOR);
    public static final JanggiPiece CHO_상 = new JanggiPiece(JanggiSide.CHO, JanggiPieceType.ELEPHANT);
    public static final JanggiPiece HAN_상 = new JanggiPiece(JanggiSide.HAN, JanggiPieceType.ELEPHANT);
    public static final JanggiPiece CHO_졸 = new JanggiPiece(JanggiSide.CHO, JanggiPieceType.SOLDIER_OF_CHO);
    public static final JanggiPiece HAN_병 = new JanggiPiece(JanggiSide.HAN, JanggiPieceType.SOLDIER_OF_HAN);
    public static final JanggiPiece CHO_차 = new JanggiPiece(JanggiSide.CHO, JanggiPieceType.CHARIOT);
    public static final JanggiPiece HAN_차 = new JanggiPiece(JanggiSide.HAN, JanggiPieceType.CHARIOT);
    public static final JanggiPiece CHO_포 = new JanggiPiece(JanggiSide.CHO, JanggiPieceType.CANNON);
    public static final JanggiPiece HAN_포 = new JanggiPiece(JanggiSide.HAN, JanggiPieceType.CANNON);
    public static final JanggiPiece EMPTY = new JanggiPiece(JanggiSide.NONE, JanggiPieceType.EMPTY);
}
