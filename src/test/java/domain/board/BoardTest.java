package domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.moveStrategy.StubBoard;
import domain.place.moveStrategy.ChoSoldierMoveStrategy;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setup() {
        StubBoard stubBoard = new StubBoard();
        Position position = new Position(3, 1);
        stubBoard.put(position, new Soldier(Side.CHO, new ChoSoldierMoveStrategy()));
        board = stubBoard.create();
    }

    @Test
    @DisplayName("보드 초기화 출력 테스트")
    void 초기화_출력테스트() {
        // given
        Board board = BoardFactory.create(
                HorseElephantFormation.SANG_MA_SANG_MA,
                HorseElephantFormation.MA_SANG_MA_SANG
        );

        // when
        List<List<String>> result = board.getFormatBoard();

        StringBuilder target = new StringBuilder();
        result.forEach(list -> list.forEach(target::append));

        // then
        assertThat(result.size()).isEqualTo(10);
        for (List<String> row : result) {
            assertThat(row.size()).isEqualTo(9);
        }
    }

    @Test
    @DisplayName("기물의 이동 규칙에 어긋나는 좌표로 이동 시 예외")
    void 규칙_위반_이동_예외_테스트() {
        //given
        Position from = new Position(3, 1);
        Position to = new Position(7, 1);

        //when & then
        assertThatThrownBy(() -> board.move(from, to, Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 기물이 가지 못하는 자리입니다.");
    }

    @Test
    @DisplayName("기물 선택에서 없는 부분 예외")
    void 기물_선택_없는_부분_예외_테스트() {
        //given
        Position from = new Position(4, 1);
        Position to = new Position(7, 1);

        //when & then
        assertThatThrownBy(() -> board.move(from, to, Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 현재 위치에 기물이 없습니다.");
    }

    @Test
    @DisplayName("상대 기물 선택 예외")
    void 상대_기물_선택_예외_테스트() {
        //given
        Position from = new Position(3, 1);
        Position to = new Position(7, 1);

        //when & then
        assertThatThrownBy(() -> board.move(from, to, Side.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 본인의 기물을 선택해야 합니다.");
    }
}