package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Piece;
import janggi.domain.piece.camp.CampType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElephantFormation {

    private static final List<Integer> SETTING_COLS = List.of(1, 2, 6, 7);

    private final CampType campType;
    private final ElephantSetUp setUp;

    public ElephantFormation(CampType campType, ElephantSetUp setUp) {
        this.campType = campType;
        this.setUp = setUp;
    }

    public Map<Position, Piece> placeElephantSetUpPieces() {
        if (campType == CampType.HAN) {
            return createByCamp(SETTING_COLS);
        }
        return createByCamp(SETTING_COLS.reversed());
    }

    private Map<Position, Piece> createByCamp(List<Integer> settingCols) {
        Map<Position, Piece> map = new HashMap<>();

        for (int i = 0; i < settingCols.size(); i++) {
            map.put(
                    new Position(campType.getStartRowPosition(), settingCols.get(i)),
                    new Piece(setUp.getPieceRules().get(i), campType)
            );
        }
        return map;
    }
}
