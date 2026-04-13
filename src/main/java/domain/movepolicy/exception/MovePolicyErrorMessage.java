package domain.movepolicy.exception;

public enum MovePolicyErrorMessage {
    SAME_SIDE_ATTACK("같은 진영의 말은 공격할 수 없습니다."),
    PO_CANNOT_ATTACK_PO("포는 포를 공격할 수 없습니다."),
    PATH_MUST_BE_EMPTY("이동 경로엔 기물이 있을 수 없습니다."),
    PATH_MUST_CONTAIN_PIECE("이동 경로엔 기물이 존재해야 합니다."),
    PATH_MUST_CONTAIN_ONE_PIECE("이동 경로엔 기물이 1개만 존재해야 합니다."),
    PO_CANNOT_JUMP_OVER_PO("포는 포를 뛰어 넘을 수 없습니다.");

    private final String message;

    MovePolicyErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
