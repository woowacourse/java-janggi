package pieceProperty;

import movementRule.Byeong;
import movementRule.Jol;
import movementRule.Ma;
import movementRule.PieceRule;
import movementRule.Sang;
import movementRule.linearMover.Cha;
import movementRule.linearMover.Po;
import movementRule.omniDirectionMover.ChoJanggun;
import movementRule.omniDirectionMover.ChoSa;
import movementRule.omniDirectionMover.HanJanggun;
import movementRule.omniDirectionMover.HanSa;

public enum PieceType {

    BYEONG(new Byeong()),
    CHA(new Cha()),
    HAN_JANGGUN(new HanJanggun()),
    CHO_JANGGUN(new ChoJanggun()),
    JOL(new Jol()),
    MA(new Ma()),
    PO(new Po()),
    CHO_SA(new ChoSa()),
    HAN_SA(new HanSa()),
    SANG(new Sang());

    private final PieceRule pieceRule;

    PieceType(PieceRule pieceRule) {
        this.pieceRule = pieceRule;
    }

    public Positions makeRoute(final Position startPosition, final Position destination) {
        return pieceRule.makeRoute(startPosition, destination);
    }

    public void canMoveTo(final Position startPosition, final Position destination) {
        pieceRule.canMoveTo(startPosition, destination);
    }

    public boolean isPo() {
        return this.equals(PO);
    }

    public boolean isJanggun() {
        return this.equals(CHO_JANGGUN) || this.equals(HAN_JANGGUN);
    }

    public int getScore() { return pieceRule.getScore(); }

    public static PieceType getPieceTypeBy(String pieceType) {
        if (pieceType.equals("BYEONG")) {
            return BYEONG;
        }

        if (pieceType.equals("CHA")) {
            return CHA;
        }

        if (pieceType.equals("JANGGUN")) {
            return HAN_JANGGUN;
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

        return HAN_SA;
    }


}
