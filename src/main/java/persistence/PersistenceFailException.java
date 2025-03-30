package persistence;

import java.sql.SQLException;

public class PersistenceFailException extends RuntimeException {

    private static final String ERROR_FORMAT = "메시지 : %s 에러 코드 : %s 로 인해 예외가 발생하였습니다. 관리자의 확인이 필요합니다.";

    public PersistenceFailException(SQLException e) {
        super(String.format(ERROR_FORMAT, e.getErrorCode() + "", e.getMessage()));
    }

    public PersistenceFailException(String message) {
        super(message);
    }
}
