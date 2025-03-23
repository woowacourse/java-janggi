package domain.constant;

import domain.piece.*;
import domain.piece.궁;
import domain.piece.마;
import domain.piece.병;
import domain.piece.사;
import domain.piece.상;
import domain.piece.졸;
import domain.piece.차;
import domain.piece.포;

public class JanggiPieceConstant {

    public static final JanggiPiece CHO_궁 = new 궁(JanggiSide.CHO);
    public static final JanggiPiece HAN_궁 = new 궁(JanggiSide.HAN);
    public static final JanggiPiece CHO_마 = new 마(JanggiSide.CHO);
    public static final JanggiPiece HAN_마 = new 마(JanggiSide.HAN);
    public static final JanggiPiece CHO_사 = new 사(JanggiSide.CHO);
    public static final JanggiPiece HAN_사 = new 사(JanggiSide.HAN);
    public static final JanggiPiece CHO_상 = new 상(JanggiSide.CHO);
    public static final JanggiPiece HAN_상 = new 상(JanggiSide.HAN);
    public static final JanggiPiece CHO_졸 = new 졸(JanggiSide.CHO);
    public static final JanggiPiece HAN_병 = new 병(JanggiSide.HAN);
    public static final JanggiPiece CHO_차 = new 차(JanggiSide.CHO);
    public static final JanggiPiece HAN_차 = new 차(JanggiSide.HAN);
    public static final JanggiPiece CHO_포 = new 포(JanggiSide.CHO);
    public static final JanggiPiece HAN_포 = new 포(JanggiSide.HAN);
    public static final JanggiPiece EMPTY = new Empty();
}
