package domain;

import domain.strategy.ChaMoveRule;
import domain.strategy.JangMoveRule;
import domain.strategy.JolMoveRule;
import domain.strategy.MaMoveRule;
import domain.strategy.MoveRule;
import domain.strategy.PoMoveRule;
import domain.strategy.SaMoveRule;
import domain.strategy.SangMoveRule;
import java.util.List;

public enum PieceType {
    CHA("차", new ChaMoveRule()),
    MA("마", new MaMoveRule()),
    SANG("상", new SangMoveRule()),
    SA("사", new SaMoveRule()),
    JANG("장", new JangMoveRule()),
    PO("포", new PoMoveRule()),
    JOL("졸", new JolMoveRule()),
    NONE("", (start, end, piece) -> false);

    private final String name;
    private final MoveRule moveRule;

    PieceType(String name, MoveRule moveRule) {
        this.name = name;
        this.moveRule = moveRule;
    }

    public static PieceType of(String name) {
        for  (PieceType pieceType : PieceType.values()) {
            if(pieceType.name.equals(name)){
                return pieceType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 기물입니다.");
    }

    public String getName() {
        return name;
    }

    public boolean canMovePosition(Position start, Position end, Piece piece) {
        return moveRule.canMovePosition(start, end, piece);
    }

    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return moveRule.isAvailableRoute(pieces, endPieceType);
    }
}
