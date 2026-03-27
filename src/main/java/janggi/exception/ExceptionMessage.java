package janggi.exception;

import static janggi.constant.GameRule.ELEPHANT_DIAGONAL_MOVE_DISTANCE;
import static janggi.constant.GameRule.ELEPHANT_STRAIGHT_MOVE_DISTANCE;
import static janggi.constant.GameRule.HORSE_DIAGONAL_MOVE_DISTANCE;
import static janggi.constant.GameRule.HORSE_STRAIGHT_MOVE_DISTANCE;
import static janggi.constant.GameRule.MAX_COLUMN_INDEX;
import static janggi.constant.GameRule.MAX_ROW_INDEX;
import static janggi.constant.GameRule.MIN_POSITION_INDEX;
import static janggi.constant.GameRule.PASS_PIECE_COUNT;
import static janggi.constant.GameRule.SINGLE_STEP_DISTANCE;

public enum ExceptionMessage {

    INVALID_BACKWARD_MOVEMENT("해당 기물은 후진할 수 없습니다."),
    ONLY_NUMBERS_ALLOWED("숫자만 입력 가능합니다"),
    ROW_OUT_OF_RANGE(String.format("행은 %d행 이상 %d행 이하여야 합니다.", MIN_POSITION_INDEX, MAX_ROW_INDEX)),
    COLUMN_OUT_OF_RANGE(String.format("열은 %d열 이상 %d열 이하여야 합니다.", MIN_POSITION_INDEX, MAX_COLUMN_INDEX)),
    INVALID_INPUT_FORMAT("잘못된 입력 형식입니다."),
    ONLY_STRAIGHT_MOVE_ALLOWED("해당 기물은 직선 이동만 가능합니다."),
    PIECE_MUST_MOVE("기물은 반드시 이동해야 합니다."),
    INVALID_ELEPHANT_SETTING("존재하지 않는 상차림 입니다."),
    SAME_PIECE_TYPE_IN_PATH("경로상에 같은 종류의 기물이 존재합니다."),
    INVALID_JUMPED_PIECE_COUNT(String.format("해당 기물은 정확히 %d개의 기물만 뛰어넘을 수 있습니다.", PASS_PIECE_COUNT)),
    SAME_CAMP_PIECE_AT_DESTINATION("목적지에 같은 진영의 기물이 존재합니다."),
    SAME_PIECE_TYPE_AT_DESTINATION("목적지에 같은 종류의 기물이 존재합니다."),
    INVALID_SOLDIER_MOVE(String.format("해당 기물은 %d칸만 이동할 수 있습니다.", SINGLE_STEP_DISTANCE)),
    INVALID_HORSE_MOVE(String.format(
            "해당 기물은 직선 %d칸 이동 후 대각선 %d칸 이동만 가능합니다.",
            HORSE_STRAIGHT_MOVE_DISTANCE,
            HORSE_DIAGONAL_MOVE_DISTANCE
    )),
    PATH_NOT_EMPTY("경로 상에 기물이 존재합니다."),
    INVALID_SINGLE_STEP_STRAIGHT_MOVE(String.format(
            "해당 기물은 직선으로 %d칸 이동해야 합니다.",
            SINGLE_STEP_DISTANCE
    )),
    INVALID_ELEPHANT_MOVE(String.format(
            "해당 기물은 직선 %d칸 이동 후 대각선 %d칸 이동만 가능합니다.",
            ELEPHANT_STRAIGHT_MOVE_DISTANCE,
            ELEPHANT_DIAGONAL_MOVE_DISTANCE
    ));

    private static final String ERROR_PREFIX = "[ERROR] ";

    private final String message;

    ExceptionMessage(String message) {
        this.message = ERROR_PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}
