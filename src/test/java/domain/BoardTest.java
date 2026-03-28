package domain;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void 방어적_복사로_BoardStatus_를_생성함() {
        //given
        Position start = Position.of(1, 1);
        Position end = Position.of(3, 1);
        Team turn = Team.CHO;

        Board testBoard = Board.of(SettingType.LEFT, SettingType.LEFT);
        BoardStatus prevMoveBoardStatus = testBoard.getBoardStatus();

        testBoard.move(turn, start, end);

        Piece piece = prevMoveBoardStatus.getBoardStatus().get(Position.of(3, 1));
        Assertions.assertThat(piece).isNull();
    }

    @Test
    @DisplayName("목적지에 아군 기물이 있으면 이동 불가")
    void move_fail_same_team_piece() {
        //given
        Position start = Position.of(1, 1);
        Position end = Position.of(4, 1);
        Team turn = Team.CHO;

        Board testBoard = Board.of(SettingType.LEFT, SettingType.LEFT);

        //when, then
        Assertions.assertThatThrownBy(
                () -> testBoard.move(turn, start, end)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}