package console.view;

import static model.board.FormationType.MA_SANG_MA_SANG;
import static model.board.FormationType.MA_SANG_SANG_MA;
import static model.board.FormationType.SANG_MA_MA_SANG;
import static model.board.FormationType.SANG_MA_SANG_MA;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import model.GameStatus;
import model.Team;
import model.board.FormationType;
import model.piece.PieceType;

public class ViewMapper {

    public static final Map<PieceType, Map<Team, String>> SYMBOL_MAP = new EnumMap<>(PieceType.class);
    public static Map<Integer, FormationType> FORMATION_ORDER_MAPPER = new LinkedHashMap<>();

    public static Map<FormationType, String> FORMATION_DISPLAY_MAPPER = Map.of(
            SANG_MA_SANG_MA, "상마상마",
            MA_SANG_MA_SANG, "마상마상",
            MA_SANG_SANG_MA, "마상상마",
            SANG_MA_MA_SANG, "상마마상"
    );

    public static Map<GameStatus, String> GAME_STATUS_DISPLAY_MAPPER = Map.of(
            GameStatus.PLAYING, "진행",
            GameStatus.BIG_JANG, "진행",
            GameStatus.BIG_JANG_DONE, "종료",
            GameStatus.DONE, "종료"
    );

    static {
        SYMBOL_MAP.put(PieceType.CHARIOT, Map.of(Team.HAN, "車", Team.CHO, "車"));
        SYMBOL_MAP.put(PieceType.CANNON, Map.of(Team.HAN, "包", Team.CHO, "包"));
        SYMBOL_MAP.put(PieceType.GENERAL, Map.of(Team.HAN, "漢", Team.CHO, "楚"));
        SYMBOL_MAP.put(PieceType.HORSE, Map.of(Team.HAN, "馬", Team.CHO, "馬"));
        SYMBOL_MAP.put(PieceType.ELEPHANT, Map.of(Team.HAN, "象", Team.CHO, "象"));
        SYMBOL_MAP.put(PieceType.GUARD, Map.of(Team.HAN, "士", Team.CHO, "士"));
        SYMBOL_MAP.put(PieceType.SOLDIER, Map.of(Team.HAN, "兵", Team.CHO, "卒"));

        FORMATION_ORDER_MAPPER.put(1, SANG_MA_SANG_MA);
        FORMATION_ORDER_MAPPER.put(2, MA_SANG_MA_SANG);
        FORMATION_ORDER_MAPPER.put(3, MA_SANG_SANG_MA);
        FORMATION_ORDER_MAPPER.put(4, SANG_MA_MA_SANG);
    }

    private ViewMapper() {
    }
}
