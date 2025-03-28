package save;

import java.sql.SQLException;

public class SaveFailException extends RuntimeException {

    private static final String ERROR_FORMAT = String.format("메시지 : %s 에러 코드 : %s 로 인해 예외가 발생하였습니다. 관리자의 확인이 필요합니다.");

    public SaveFailException(SQLException e) {
        super(String.format(ERROR_FORMAT, e.getErrorCode() + "", e.getMessage()));
    }
}
