package domain;

import domain.position.Position;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    //public static Board of(SettingType choSettingType, SettingType hanSettingType) {
    //        Map<Position, Piece> setup = new BoardInitializer().setup(choSettingType, hanSettingType);
    //        return new Board(setup);
    //    }
    @Test
    void 방어적_복사로_BoardStatus_를_생성함() throws NotImplementedException {
        //given
        throw new NotImplementedException("이동 구현 완료 후 구현 필요");
    }

    @Test
    @DisplayName("목적지에 아군 기물이 있으면 이동 불가")
    void move_fail_same_team_piece() {
        //given
        Position start = Position.of(1, 1);
        Position end = Position.of(4, 1);

        Board testBoard = Board.of(SettingType.LEFT, SettingType.LEFT);

        //when, then
        Assertions.assertThatThrownBy(
                () -> testBoard.move(start, end)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}