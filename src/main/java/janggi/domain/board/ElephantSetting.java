package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ElephantSetting {

    LEFT_ELEPHANT("1", List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE)),
    RIGHT_ELEPHANT("2", List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.HORSE, PieceType.ELEPHANT)),
    INNER_ELEPHANT("3", List.of(PieceType.HORSE, PieceType.ELEPHANT, PieceType.ELEPHANT, PieceType.HORSE)),
    OUTER_ELEPHANT("4", List.of(PieceType.ELEPHANT, PieceType.HORSE, PieceType.HORSE, PieceType.ELEPHANT));

    private static final String INVALID_ELEPHANT_SETTING = "[ERROR] 존재하지 않는 상차림 입니다.";
    private static final List<Integer> SETTING_COLUMNS = List.of(1, 2, 6, 7);

    private final String command;
    private final List<PieceType> elephantOrder;

    ElephantSetting(String command, List<PieceType> elephantOrder) {
        this.command = command;
        this.elephantOrder = elephantOrder;
    }

    public static ElephantSetting findElephantSettingBy(String command) {
        return Arrays.stream(values())
                .filter(element -> element.command.equals(command))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException(INVALID_ELEPHANT_SETTING));
    }

    public Map<Position, Piece> createElephantOrder(Camp camp) {
        if (camp == Camp.HAN) {
            return createByCamp(camp, SETTING_COLUMNS);
        }
        return createByCamp(camp, SETTING_COLUMNS.reversed());
    }

    private Map<Position, Piece> createByCamp(Camp camp, List<Integer> settingColumns) {
        Map<Position, Piece> map = new HashMap<>();

        for (int i = 0; i < settingColumns.size(); i++) {
            map.put(
                    new Position(camp.getStartRowPosition(), settingColumns.get(i)),
                    new Piece(elephantOrder.get(i), camp)
            );
        }
        return map;
    }
}
