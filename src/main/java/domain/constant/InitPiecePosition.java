package domain.constant;

import domain.Position;
import java.util.List;

public enum InitPiecePosition {
    CHA(PieceType.CHA,
            List.of(Position.create(1, 1), Position.create(1, 9)),
            List.of(Position.create(10, 1), Position.create(10, 9))),

    SA(PieceType.SA,
            List.of(Position.create(1, 4), Position.create(1, 6)),
            List.of(Position.create(10, 4), Position.create(10, 6))),

    JANG(PieceType.JANG,
            List.of(Position.create(2, 5)),
            List.of(Position.create(9, 5))),

    PO(PieceType.PO,
            List.of(Position.create(3, 2), Position.create(3, 8)),
            List.of(Position.create(8, 2), Position.create(8, 8))),

    JOL(PieceType.JOL,
            List.of(Position.create(4, 1), Position.create(4, 3), Position.create(4, 5),
                    Position.create(4, 7), Position.create(4, 9)),
            List.of(Position.create(7, 1), Position.create(7, 3), Position.create(7, 5),
                    Position.create(7, 7), Position.create(7, 9)));

    private final PieceType pieceType;
    private final List<Position> choPositions;
    private final List<Position> hanPositions;

    InitPiecePosition(PieceType pieceType, List<Position> choPositions, List<Position> hanPositions) {
        this.pieceType = pieceType;
        this.choPositions = choPositions;
        this.hanPositions = hanPositions;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public List<Position> getPositionsByCountry(Country country) {
        if (country == Country.CHO) {
            return choPositions;
        }
        return hanPositions;
    }
}
