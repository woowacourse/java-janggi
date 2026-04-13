package janggi.domain.piece;

import janggi.domain.piece.linear.Cannon;
import janggi.domain.piece.linear.Chariot;
import janggi.domain.piece.single.Advisor;
import janggi.domain.piece.single.General;
import janggi.domain.piece.single.Soldier;
import janggi.domain.piece.stepped.Elephant;
import janggi.domain.piece.stepped.Horse;
import janggi.domain.side.Side;
import java.util.function.Function;

public enum PieceType {
    CHARIOT("車", "車", Chariot::new),
    CANNON("包", "包", Cannon::new),
    HORSE("馬", "馬", Horse::new),
    ELEPHANT("象", "象", Elephant::new),
    SOLDIER("卒", "兵", Soldier::new),
    ADVISOR("士", "士", Advisor::new),
    GENERAL("楚", "漢", General::new),
    NONE("  ", "  ", null);

    private final String choName;
    private final String hanName;
    private final Function<Side, Piece> creator;

    PieceType(String choName, String hanName, Function<Side, Piece> creator) {
        this.choName = choName;
        this.hanName = hanName;
        this.creator = creator;
    }

    public String getNameFormat(Side side) {
        if (side == null) {
            throw new IllegalStateException("Side는 Han 또는 Cho를 넣어주세요");
        }
        if (Side.HAN.equals(side)) {
            return hanName;
        }
        return choName;
    }

    public Piece createPiece(Side side) {
        return creator.apply(side);
    }
}
