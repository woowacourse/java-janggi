package database.exception;

public enum DataAccessError {

    CONNECTION_FAILED("데이터베이스 연결에 실패했습니다."),
    COMMIT_FAILED("트랜잭션 커밋에 실패했습니다."),
    ROLLBACK_FAILED("트랜잭션 롤백에 실패했습니다."),
    CONNECTION_CLOSE_FAILED("데이터베이스 연결 종료에 실패했습니다."),

    PREPARE_STATEMENT_FAILED("SQL 실행 준비에 실패했습니다."),
    QUERY_FAILED("쿼리 실행에 실패했습니다."),
    UPDATE_NOT_FOUND("UPDATE 쿼리가 적용된 행이 없습니다."),
    PARAMETER_BINDING_FAILED("파라미터 바인딩에 실패했습니다."),
    GENERATED_KEY_NOT_FOUND("생성된 키를 찾지 못했습니다."),

    PROPERTIES_FILE_NOT_FOUND("application.properties 파일을 찾을 수 없습니다."),
    PROPERTIES_LOAD_FAILED("application.properties 파일을 읽는 데 실패했습니다.");

    private final String message;

    DataAccessError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
