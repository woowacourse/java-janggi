package domain.constant;

import domain.Piece;
import domain.Position;
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
    CHA("차", new ChaMoveRule(), 13),
    MA("마", new MaMoveRule(), 5),
    SANG("상", new SangMoveRule(), 3),
    SA("사", new SaMoveRule(), 3),
    JANG("장", new JangMoveRule(), 0),
    PO("포", new PoMoveRule(), 7),
    JOL("졸", new JolMoveRule(), 2),
    NONE("", (start, end, piece) -> false, 0);

    private final String name;
    private final MoveRule moveRule;
    private final int score;

    PieceType(String name, MoveRule moveRule, int score) {
        this.name = name;
        this.moveRule = moveRule;
        this.score = score;
    }

    public static PieceType from(String name) {
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

    public int getScore() {
        return score;
    }
}
