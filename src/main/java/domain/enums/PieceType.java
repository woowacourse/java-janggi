package domain.enums;

import java.util.List;

import domain.Position;

public enum PieceType {
    CHA("차", 13,
            List.of(Position.create(1, 1), Position.create(1, 9)),
            List.of(Position.create(10, 1), Position.create(10, 9))),

    MA("마", 5, List.of(), List.of()),

    SANG("상", 3, List.of(), List.of()),

    SA("사", 3,
            List.of(Position.create(1, 4), Position.create(1, 6)),
            List.of(Position.create(10, 4), Position.create(10, 6))),

    JANG("장", 0,
            List.of(Position.create(2, 5)),
            List.of(Position.create(9, 5))),

    PO("포", 7,
            List.of(Position.create(3, 2), Position.create(3, 8)),
            List.of(Position.create(8, 2), Position.create(8, 8))),

    JOL("졸", 2,
            List.of(
                    Position.create(4, 1), Position.create(4, 3), Position.create(4, 5),
                    Position.create(4, 7), Position.create(4, 9)
            ),
            List.of(
                    Position.create(7, 1), Position.create(7, 3), Position.create(7, 5),
                    Position.create(7, 7), Position.create(7, 9)
            )),
    NONE("＋", 0, List.of(), List.of());

    private String name;
    private int score;
    private List<Position> choPosition;
    private List<Position> hanPosition;

    PieceType(String name, int score, List<Position> choPosition, List<Position> hanPosition) {
        this.name = name;
        this.score = score;
        this.choPosition = choPosition;
        this.hanPosition = hanPosition;
    }

    public static PieceType of(String name) {
        for (PieceType pieceType : PieceType.values()) {
            if (pieceType.name.equals(name)) {
                return pieceType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 기물입니다.");
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public List<Position> getMaSangPosition(Country country) {
        if(country == Country.CHO){
            return choPosition;
        }
        return hanPosition;
    }
}
