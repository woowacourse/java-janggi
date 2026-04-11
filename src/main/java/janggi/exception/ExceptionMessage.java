package janggi.exception;

public enum ExceptionMessage {

    ONLY_NUMBERS_ALLOWED("숫자만 입력 가능합니다"),
    INVALID_INPUT_FORMAT("잘못된 입력 형식입니다."),
    INVALID_ELEPHANT_SET_UP_FORMAT("존재하지 않는 상차림 입니다."),
    ROW_OUT_OF_RANGE("행은 %d행 이상 %d행 이하여야 합니다."),
    COLUMN_OUT_OF_RANGE("열은 %d열 이상 %d열 이하여야 합니다."),
    SOURCE_NOT_EXISTS("출발지에 기물이 존재하지 않습니다."),
    INVALID_CAMP_PIECE("상대 진영의 기물은 이동할 수 없습니다."),
    PATH_NOT_EMPTY("경로 상에 기물이 존재합니다."),
    SAME_CAMP_PIECE_AT_DESTINATION("목적지에 같은 진영의 기물이 존재합니다."),
    SAME_PIECE_TYPE_IN_PATH("경로상에 같은 종류의 기물이 존재합니다."),
    SAME_PIECE_TYPE_AT_DESTINATION("목적지에 같은 종류의 기물이 존재합니다."),
    ONLY_STRAIGHT_MOVE_ALLOWED("해당 기물은 직선 이동만 가능합니다."),
    PIECE_MUST_MOVE("기물은 반드시 이동해야 합니다."),
    INVALID_BACKWARD_MOVEMENT("해당 기물은 후진할 수 없습니다."),
    INVALID_SINGLE_STEP_MOVE("해당 기물은 %d칸만 이동할 수 있습니다."),
    INVALID_JUMPED_PIECE_COUNT("해당 기물은 정확히 %d개의 기물만 뛰어넘을 수 있습니다."),
    INVALID_DIAGONAL_STEP_MOVE("해당 기물은 직선 %d칸 이동 후 대각선 %d칸 이동만 가능합니다."),
    CAMP_FORMAT_NOT_FOUND("존재하지 않는 진영 형식입니다."),
    PIECE_FORMAT_NOT_FOUND("존재하지 않는 기물 형식입니다."),
    INVALID_PALACE_MOVE("궁성 내 %d칸만 이동할 수 있습니다."),
    GAME_SELECTION_NOT_FOUND("존재하지 않는 게임 선택 명령어 입니다."),
    NO_PLAYING_GAME_ROOM("진행 중인 게임이 존재하지 않습니다."),

    GAME_ROOM_SAVE_ERROR("게임방 정보를 저장하는 중 오류가 발생했습니다."),
    GAME_ROOM_NOT_FOUND("%d번 게임방이 존재하지 않습니다."),
    GAME_ROOM_FIND_ERROR("게임방 정보를 조회하는 중 오류가 발생했습니다."),
    GAME_ROOM_UPDATE_ERROR("%d번 게임방 정보를 수정하는 중 오류가 발생했습니다."),

    PIECE_SAVE_ERROR("%d번 게임방의 (%d, %d) 위치에 기물 정보를 저장하는 중 오류가 발생했습니다."),
    PIECE_NOT_FOUND("%d번 게임방의 (%d, %d) 좌표에 기물이 존재하지 않습니다."),
    PIECE_FIND_ERROR("%d번 게임방의 기물 정보를 조회하는 중 오류가 발생했습니다."),
    PIECE_UPDATE_ERROR("%d번 게임방의 (%d, %d) 위치에 있는 기물 정보를 수정하는 중 오류가 발생했습니다."),
    PIECE_DELETE_ERROR("기물 아이디 %d번 정보를 삭제하는 중 오류가 발생했습니다."),
    ;


    private static final String ERROR_PREFIX = "[ERROR] ";

    private final String message;

    ExceptionMessage(String message) {
        this.message = ERROR_PREFIX + message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
