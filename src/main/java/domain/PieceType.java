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
    CHA("차", new ChaMoveRule(),
            List.of(Position.create(1, 1), Position.create(1, 9)),
            List.of(Position.create(10, 1), Position.create(10, 9))),

    MA("마", new MaMoveRule(), List.of(), List.of()),

    SANG("상", new SangMoveRule(), List.of(), List.of()),

    SA("사", new SaMoveRule(),
            List.of(Position.create(1, 4), Position.create(1, 6)),
            List.of(Position.create(10, 4), Position.create(10, 6))),

    JANG("장", new JangMoveRule(),
            List.of(Position.create(2, 5)),
            List.of(Position.create(9, 5))),

    PO("포", new PoMoveRule(),
            List.of(Position.create(3, 2), Position.create(3, 8)),
            List.of(Position.create(8, 2), Position.create(8, 8))),

    JOL("졸", new JolMoveRule(),
            List.of(
                    Position.create(4, 1), Position.create(4, 3), Position.create(4, 5),
                    Position.create(4, 7), Position.create(4, 9)
            ),
            List.of(
                    Position.create(7, 1), Position.create(7, 3), Position.create(7, 5),
                    Position.create(7, 7), Position.create(7, 9)
            )),
    NONE("＋", (start, end, piece) -> false,
            List.of(), List.of());

    private final String name;
    private final MoveRule moveRule;
    private final List<Position> choPosition;
    private final List<Position> hanPosition;

    PieceType(String name, MoveRule moveRule, List<Position> choPosition, List<Position> hanPosition) {
        this.name = name;
        this.moveRule = moveRule;
        this.choPosition = choPosition;
        this.hanPosition = hanPosition;
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

    public List<Position> getChoPosition() {
        return choPosition;
    }

    public List<Position> getHanPosition() {
        return hanPosition;
    }

    public boolean canMovePosition(Position start, Position end, Piece piece) {
        return moveRule.canMovePosition(start, end, piece);
    }

    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return moveRule.isAvailableRoute(pieces, endPieceType);
    }
}
