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

        testBoard.move(start, end);

        Piece piece = prevMoveBoardStatus.getBoardStatus().get(Position.of(3, 1));
        Assertions.assertThat(piece).isNull();
    }
}