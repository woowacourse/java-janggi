package janggi.domain.board.initializer;

import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ElephantSetUp {

    LEFT_ELEPHANT(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE)),
    RIGHT_ELEPHANT(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT)),
    INNER_ELEPHANT(List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE)),
    OUTER_ELEPHANT(List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT));

    private static final List<Integer> SETTING_COLUMNS = List.of(1, 2, 6, 7);

    private final List<PieceType> elephantOrder;

    ElephantSetUp(List<PieceType> elephantOrder) {
        this.elephantOrder = elephantOrder;
    }

    public Map<Position, Piece> settingUp(Camp camp) {
        List<Integer> settingColumns = settingColumnsOf(camp);

        Map<Position, Piece> map = new HashMap<>();
        for (int i = 0; i < settingColumns.size(); i++) {
            map.put(
                    new Position(camp.getStartRowPosition(), settingColumns.get(i)),
                    new Piece(camp, elephantOrder.get(i))
            );
        }

        return map;
    }

    private List<Integer> settingColumnsOf(Camp camp) {
        if (camp == Camp.CHO) {
            return SETTING_COLUMNS.reversed();
        }
        return SETTING_COLUMNS;
    }
}
