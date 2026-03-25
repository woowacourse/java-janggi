package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("보드 초기화 출력 테스트")
    void 초기화_출력테스트() {
        // given
        Board board = BoardFactory.create(HorseElephantFormation.SANG_MA_SANG_MA,
                HorseElephantFormation.MA_SANG_MA_SANG);

        // when
        List<String> result = board.getFormatBoard();

        StringBuilder target = new StringBuilder();
        result.forEach(target::append);

        // then
        assertThat(target.toString()).isEqualTo(
                "C차C상C마C사  C사C상C마C차" +
                        "        C궁        " +
                        "  C포          C포  " +
                        "C졸  C졸  C졸  C졸  C졸" +
                        "                  " +
                        "                  " +
                        "H졸  H졸  H졸  H졸  H졸" +
                        "  H포          H포  " +
                        "        H궁        " +
                        "H차H마H상H사  H사H마H상H차"
        );
    }
}
