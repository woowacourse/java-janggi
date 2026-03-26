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
                "H차H마H상H사  H사H마H상H차" +
                        "        H궁        " +
                        "  H포          H포  " +
                        "H졸  H졸  H졸  H졸  H졸" +
                        "                  " +
                        "                  " +
                        "C졸  C졸  C졸  C졸  C졸" +
                        "  C포          C포  " +
                        "        C궁        " +
                        "C차C상C마C사  C사C상C마C차"
        );
    }

    @Test
    @DisplayName("기물 선택에서 없는 부분 예외")
    void 기물_선택_없는_부분_예외_테스트() {
        Position from = new Position(4, 1);
        Position to = new Position(7, 1);

        assertThatThrownBy(() -> board.move(from, to, Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 선택한 위치에 기물이 없습니다.");
    }

    @Test
    @DisplayName("상대 기물 선택 예외")
    void 상대_기물_선택_예외_테스트() {
        Position from = new Position(3, 1);
        Position to = new Position(7, 1);

        assertThatThrownBy(() -> board.move(from, to, Side.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 본인의 기물을 선택해야 합니다.");
    }
}
