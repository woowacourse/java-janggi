package domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.place.moveStrategy.ChariotMoveStrategy;
import domain.place.moveStrategy.GeneralMoveStrategy;
import domain.place.moveStrategy.SoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceOneStepMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceSoldierMoveStrategy;
import domain.place.palaceMoveStrategy.PalaceStraightMoveStrategy;
import domain.place.piece.Chariot;
import domain.place.piece.General;
import domain.place.piece.Side;
import domain.place.piece.Soldier;
import domain.position.Position;
import factory.BoardFactory;
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
        stubBoard.put(position, new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO),
                new PalaceSoldierMoveStrategy(Side.CHO)));

        Position position2 = new Position(4, 1);
        stubBoard.put(position2, new Soldier(Side.CHO, new SoldierMoveStrategy(Side.CHO),
                new PalaceSoldierMoveStrategy(Side.CHO)));

        Position position3 = new Position(10, 9);
        stubBoard.put(position3, new Chariot(Side.CHO, new ChariotMoveStrategy(),
                new PalaceStraightMoveStrategy()));

        stubBoard.put(new Position(2,5), new General(Side.CHO, new GeneralMoveStrategy(),
                new PalaceOneStepMoveStrategy()));

        Position position4 = new Position(6, 1);
        stubBoard.put(position4, new Soldier(Side.HAN, new SoldierMoveStrategy(Side.HAN),
                new PalaceSoldierMoveStrategy(Side.HAN)));

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
    @DisplayName("아군 위치로 이동 예외")
    void throws_exception_when_moving_to_same_team_position() {
        //given
        Position from = new Position(3, 1);
        Position to = new Position(4, 1);

        //when & then
        assertThatThrownBy(() -> board.move(from, to, Side.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 기물이 가지 못하는 자리입니다.");
    }

    @Test
    @DisplayName("기물 점수 계산")
    void board_calculate_side(){
        //given & when
        double cho = board.getSideScore(Side.CHO);
        double han = board.getSideScore(Side.HAN);

        //then
        assertThat(cho).isEqualTo(15);
        assertThat(han).isEqualTo(2.5);
    }

    @Test
    @DisplayName("보드에 궁이 살아있는지 검사")
    void board_alive_general(){
        //given & when & then
        assertThat(board.isAliveGeneral(Side.CHO)).isTrue();
        assertThat(board.isAliveGeneral(Side.HAN)).isFalse();
    }
}
