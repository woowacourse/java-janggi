package repository;

public enum RepositoryErrorMessage {
    CONFIG_FILE_NOT_FOUND("클래스패스에서 설정 파일을 찾을 수 없습니다"),
    ERROR_ON_CONFIG_FILE_READING("DB 정보를 읽어오는데 실패하였습니다"),
    BATCH_ERROR("배치 쿼리 실행 중 오류가 발생했습니다.");

    private final String message;

    public String getMessage() {
        return message;
    }

    RepositoryErrorMessage(String message) {
        this.message = message;
    }
}
