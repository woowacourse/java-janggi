import domain.Board;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KoreaChessTest {

    @Test
    void 장기판을_초기화한다() {
        // given
        KoreaChess koreaChess = new KoreaChess();

        // when
        Board result = koreaChess.createBoard();

        // then
        assertThat(result.getBoard())
                .hasSize(2);
    }
}
