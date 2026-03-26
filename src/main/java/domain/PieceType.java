package domain;

import java.util.ArrayList;
import java.util.List;

public enum PieceType {
    CHA("차",
            List.of(Position.create(1, 1), Position.create(1, 9)),
            List.of(Position.create(10, 1), Position.create(10, 9))),

    MA("마", List.of(), List.of()),

    SANG("상",List.of(), List.of()),

    SA("사",
            List.of(Position.create(1, 4), Position.create(1, 6)),
            List.of(Position.create(10, 4), Position.create(10, 6))),

    JANG("장",
            List.of(Position.create(2, 5)),
            List.of(Position.create(9, 5))),

    PO("포",
            List.of(Position.create(3, 2), Position.create(3, 8)),
            List.of(Position.create(8, 2), Position.create(8, 8))),

    JOL("졸",
            List.of(
                    Position.create(4, 1), Position.create(4, 3), Position.create(4, 5),
                    Position.create(4, 7), Position.create(4, 9)
            ),
            List.of(
                    Position.create(7, 1), Position.create(7, 3), Position.create(7, 5),
                    Position.create(7, 7), Position.create(7, 9)
            )),
    NONE("＋", List.of(), List.of());

    private String name;
    private List<Position> choPosition;
    private List<Position> hanPosition;

    PieceType(String name, List<Position> choPosition, List<Position> hanPosition) {
        this.name = name;
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

    public List<Position> getAllPosition() {
        List<Position> allPosition = new ArrayList<>(choPosition);
        allPosition.addAll(hanPosition);
        return allPosition;
    }

    public List<Position> getChoPosition() {
        return choPosition;
    }

    public List<Position> getHanPosition() {
        return hanPosition;
    }
}
