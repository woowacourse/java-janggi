package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceRule;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ElephantSetting {

    LEFT_ELEPHANT("1", List.of(PieceRule.ELEPHANT, PieceRule.HORSE, PieceRule.ELEPHANT, PieceRule.HORSE)),
    RIGHT_ELEPHANT("2", List.of(PieceRule.HORSE, PieceRule.ELEPHANT, PieceRule.HORSE, PieceRule.ELEPHANT)),
    INNER_ELEPHANT("3", List.of(PieceRule.HORSE, PieceRule.ELEPHANT, PieceRule.ELEPHANT, PieceRule.HORSE)),
    OUTER_ELEPHANT("4", List.of(PieceRule.ELEPHANT, PieceRule.HORSE, PieceRule.HORSE, PieceRule.ELEPHANT));

    private static final List<Integer> SETTING_COLS = List.of(1, 2, 6, 7);

    private final String command;
    private final List<PieceRule> elephantOrder;

    ElephantSetting(String command, List<PieceRule> elephantOrder) {
        this.command = command;
        this.elephantOrder = elephantOrder;
    }

    public static ElephantSetting findElephantSettingBy(String command) {
        return Arrays.stream(values())
                .filter(element -> element.command.equals(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상차림 입니다."));
    }

    public Map<Position, Piece> createElephantOrder(Camp camp) {
        if (camp == Camp.HAN) {
            return createByCamp(camp, SETTING_COLS);
        }
        return createByCamp(camp, SETTING_COLS.reversed());
    }

    private Map<Position, Piece> createByCamp(Camp camp, List<Integer> settingCols) {
        Map<Position, Piece> map = new HashMap<>();

        for (int i = 0; i < settingCols.size(); i++) {
            map.put(
                    new Position(camp.getStartRowPosition(), settingCols.get(i)),
                    new Piece(elephantOrder.get(i), camp)
            );
        }
        return map;
    }
}
