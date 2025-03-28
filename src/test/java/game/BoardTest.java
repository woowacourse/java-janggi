package game;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Nested
    class 상차림에_따라_말들이_배치된다 {
        @Test
        void 마상마상에_따라_말들이_배치된다() {
            // given

            BoardSetting boardSetting = new BoardSetting();
            // when
            Board board = new Board(StartPosition.MA_SANG_MA_SANG, StartPosition.MA_SANG_MA_SANG);
            // then
            Assertions.assertThat(board)
                    .extracting("board")
                    .


        }
    }

    @Test
    void 상차림에_따라_말들이_배치된다() {
        // given

        BoardSetting boardSetting = new BoardSetting();
        // when
        Board board = new Board(StartPosition.MA_SANG_MA_SANG, StartPosition.MA_SANG_MA_SANG);
        // then

    }
}
