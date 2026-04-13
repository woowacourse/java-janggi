package laboratory.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.SQLException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LostExceptionProblemTest {

    @DisplayName("수습 로직(rollback)에서 예외가 터지면, 비즈니스 로직의 근본 예외는 실종된다")
    @Test
    void lost_exception_demonstration() {
        // [Given] 롤백 시 예외를 던지는 상황을 가정함
        ConnectionStub conn = null; // null 상황

        // [When & Then]
        assertThatThrownBy(() -> executeWithBadRollback(conn))
                // 1. 결국 밖으로 던져지는 건 마지막에 터진 IllegalArgumentException임
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Connection is null")

                // 2. [핵심] 근본 원인인 SQLException 정보가 사라졌음을 검증
                .satisfies(e -> {
                    assertThat(e.getCause()).isNull();
                    // 원래는 SQLException이 Cause로 담겨있어야 하지만,
                    // 롤백에서 터진 예외가 흐름을 끊어서 null이 됨
                });
    }

    /**
     * 현재 JanggiService.execute()와 동일한 구조의 실험용 메서드
     */
    private void executeWithBadRollback(ConnectionStub conn) {
        try {
            // 비즈니스 로직 도중 예외 발생
            throw new SQLException("근본 원인: DB 데이터 오류");
        } catch (SQLException e) {
            // 수습하러 들어왔다가 여기서 새로운 예외가 발생하여 흐름이 끊김
            badRollback(conn);

            // [실행 불가] 위에서 예외가 터졌으므로 이 아래 코드는 죽은 코드가 됨
            throw new RuntimeException("DataAccessException 변환", e);
        }
    }

    private void badRollback(ConnectionStub conn) {
        if (conn == null) {
            throw new IllegalArgumentException("Connection is null");
        }
    }

    // 테스트용 스텁 클래스
    private static class ConnectionStub {}
}