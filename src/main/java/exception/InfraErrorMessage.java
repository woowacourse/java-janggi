package exception;

public enum InfraErrorMessage {
    DEFAULT_INFRA_ERROR("외부 인프라와 연결하는 와중에 문제가 발생하였습니다. 관리자에게 문의하세요."),

    DEFAULT_DATABASE_CONNECTION_ERROR("데이터베이스 연결에서 문제가 발생하였습니다. 관리자에게 문의하세요."),
    INVALID_DATABASE_INFORMATION("데이터 베이스 연결에 실패하였습니다. 주소와 사용자 이름, 비밀번호를 확인해주세요."),

    DEFAULT_CONFIG_LOAD_ERROR("설정파일을 불러오는 과정에서 에러가 발생하였습니다."),
    CONFIG_FILE_NOT_FOUND_ERROR("application.properties이 존재하지 않습니다."),

    CURRENT_PIECE_POSITION_SAVE_ERROR("현재 피스 위치 정보를 저장하는 과정에서 에러가 발생하였습니다."),
    CURRENT_PIECE_POSITION_READ_ERROR("현재 피스 위치 정보를 불러오는 과정에서 에러가 발생하였습니다."),
    CURRENT_PIECE_POSITION_DELETE_ERROR("현재 피스 위치 정보를 삭제하는 과정에서 에러가 발생하였습니다."),

    TURN_SAVE_ERROR("현재 차례를 저장하는 과정에서 에러가 발생하였습니다."),
    TURN_READ_ERROR("현재 차례를 불러오는 과정에서 에러가 발생하였습니다."),
    TURN_DELETE_ERROR("현재 차례를 삭제하는 과정에서 에러가 발생하였습니다."),

    GAME_SAVE_ERROR("게임을 저장하는 과정에서 에러가 발생하였습니다."),
    GAME_READ_ERROR("게임을 불러오는 과정에서 에러가 발생하였습니다."),
    GAME_DELETE_ERROR("게임을 삭제하는 과정에서 에러가 발생하였습니다."),
    PK_GENERATION_ERROR("PK 생성에 실패하였습니다."),

    FORMATION_READ_ERROR("상차림 정보를 불러오는 과정에서 에러가 발생하였습니다."),
    FORMATION_NOT_FOUND_ERROR("해당 상차림 정보를 찾을 수 없습니다.");

    private final String message;

    InfraErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
