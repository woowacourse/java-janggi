package janggi.model.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.Team;
import janggi.model.board.position.Column;
import janggi.model.board.position.Position;
import janggi.model.board.position.Row;
import janggi.model.gimul.AbstractGimul;
import janggi.model.gimul.diagonalMove.Ma;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionPathTest {


    @DisplayName("경로 상에 위치하는 기물들을 반환한다.")
    @Test
    void findGimulsOn() {
        //given
        List<Position> positions = List.of(
                new Position(Row.NINE, Column.EIGHT),
                new Position(Row.NINE, Column.SEVEN),
                new Position(Row.EIGHT, Column.SEVEN),
                new Position(Row.SEVEN, Column.SIX)
        );

        PositionPath path = new PositionPath(positions);

        Ma first = new Ma(Team.CHO);
        Ma second = new Ma(Team.CHO);
        Ma third = new Ma(Team.CHO);
        Ma fourth = new Ma(Team.CHO);

        Map<Position, AbstractGimul> board = Map.of(
                new Position(Row.NINE, Column.EIGHT), first,
                new Position(Row.NINE, Column.SEVEN), second,
                new Position(Row.EIGHT, Column.SEVEN), third,
                new Position(Row.SEVEN, Column.SIX), fourth
        );

        //when
        List<AbstractGimul> gimuls = path.findGimulsOn(board);

        //then
        assertThat(gimuls)
                .containsExactly(first, second, third, fourth);
    }
}