package domain;

import domain.piece.Piece;
import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void BoardStatus는_방어적_복사가_수행되어야_한다() {
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
    void 목적지에_아군_기물이_있으면_예외가_발생해야_한다() {
        //given
        Position start = Position.of(1, 1);
        Position end = Position.of(4, 1);
        Team turn = Team.CHO;

        Board testBoard = Board.of(SettingType.LEFT, SettingType.LEFT);

        //when &then
        Assertions.assertThatThrownBy(() -> testBoard.move(turn, start, end))
                .isInstanceOf(IllegalArgumentException.class);
    }
}