package factory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiBoardFactoryTest {

    @Test
    @DisplayName("장기판 보드를 초기 세팅 해준다.")
    void initial_janggi_board_test() {
        JanggiBoardFactory janggiBoardFactory = new JanggiBoardFactory();

        Assertions.assertThat(janggiBoardFactory.initialBoard().board()).hasSize(90);
    }

}