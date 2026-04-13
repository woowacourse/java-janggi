package constant;

public class ErrorMessage {

    private ErrorMessage() {}

    public static final String ERROR_PREFIX = "[ERROR] ";

    public static final String READ_FORMATION_MESSAGE = "[%s] 포진을 선택해주세요.";
    public static final String INVALID_FORMATION_RANGE = "번호는 1~4 사이의 숫자여야 합니다.";
    public static final String INVALID_TURN_ACTION_RANGE = "1, 2, 3 중 하나를 입력해주세요.";
    public static final String INVALID_OPTION_RANGE = "1, 2 중 하나를 입력해주세요.";
    public static final String INVALID_NUMBER_FORMAT = "유효한 숫자를 입력해주세요.";
    public static final String INVALID_POSITION_RANGE = "x 좌표는 1~9, y 좌표는 1~10, 사이여야 합니다.";

    public static final String CANNOT_CAPTURE_OWN_PIECE = "아군 기물은 잡을 수 없습니다.";
    public static final String INVALID_TARGET_POSITION = "이동할 수 없는 목적지입니다.";
    public static final String ROUTE_BLOCKED = "이동 경로가 막혀있습니다.";

    public static final String CANNOT_JUMP_WITH_CANNON = "포를 넘어갈 수 없습니다.";
    public static final String CANNOT_CAPTURE_CANNON_WITH_CANNON = "포는 포끼리 잡을 수 없습니다.";
    public static final String MUST_JUMP_EXACTLY_ONE = "포는 정확히 하나의 기물을 넘어야 합니다.";

    public static final String NOT_OWN_PIECE = "선택한 기물은 아군 기물이 아닙니다.";
    public static final String PIECE_NOT_FOUND = "해당 위치에 기물이 존재하지 않습니다.";
    public static final String INVALID_DIRECTION = "잘못된 방향 값입니다: %d, %d";
    public static final String GAME_NOT_FOUND = "존재하지 않는 게임입니다.";
}
