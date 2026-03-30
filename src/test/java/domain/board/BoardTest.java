package domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board board;

    @BeforeEach
    void setup() {
        StubBoard stubBoard = new StubBoard();
        Position position = new Position(3, 1);
        stubBoard.put(position, new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO)));

        Position position2 = new Position(4, 1);
        stubBoard.put(position2, new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO)));
        board = stubBoard.create();
    }

    @Test
    @DisplayName("보드 초기화 출력 테스트")
    void board_initialization_output_test() {
        // given
        Board board = BoardFactory.create(
                HorseElephantFormation.SANG_MA_SANG_MA,
                HorseElephantFormation.MA_SANG_MA_SANG
        );

        // when
        List<List<String>> result = board.getFormatBoard();

        // then
        assertThat(result.size()).isEqualTo(10);
        for (List<String> row : result) {
            assertThat(row.size()).isEqualTo(9);
        }
    }

    @Test
    @DisplayName("기물의 이동 규칙에 어긋나는 좌표로 이동 시 예외")
    void throw_exception_when_moving_to_invalid_position() {
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
    void throw_exception_when_selecting_empty_position() {
        //given
        Position from = new Position(5, 1);
        Position to = new Position(7, 1);

        //when & then
        assertThatThrownBy(() -> board.move(from, to, Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 잘못된 기물 선택입니다.");
    }

    @Test
    @DisplayName("상대 기물 선택 예외")
    void throw_exception_when_selecting_opponent_piece() {
        //given
        Position from = new Position(3, 1);
        Position to = new Position(7, 1);

        //when & then
        assertThatThrownBy(() -> board.move(from, to, Side.HAN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 잘못된 기물 선택입니다.");
    }

    @Test
    @DisplayName("아군 위치로 이동")
    void throws_exception_when_moving_to_same_team_position() {
        //given
        Position from = new Position(3, 1);
        Position to = new Position(4, 1);

        //when & then
        assertThatThrownBy(() -> board.move(from, to, Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 기물이 가지 못하는 자리입니다.");
    }
}
