package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElephantFormation {

    private static final List<Integer> SETTING_COLS = List.of(1, 2, 6, 7);

    private final Camp camp;
    private final ElephantSetUp setUp;

    public ElephantFormation(Camp camp, ElephantSetUp setUp) {
        this.camp = camp;
        this.setUp = setUp;
    }

    public Map<Position, Piece> placeElephantSetUpPieces() {
        if (camp == Camp.HAN) {
            return createByCamp(SETTING_COLS);
        }
        return createByCamp(SETTING_COLS.reversed());
    }

    private Map<Position, Piece> createByCamp(List<Integer> settingCols) {
        Map<Position, Piece> map = new HashMap<>();

        for (int i = 0; i < settingCols.size(); i++) {
            map.put(
                    new Position(camp.getStartRowPosition(), settingCols.get(i)),
                    new Piece(setUp.getPieceRules().get(i), camp)
            );
        }
        return map;
    }
}
