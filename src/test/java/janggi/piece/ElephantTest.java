package janggi.piece;

import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ElephantTest {
    @Test
    @DisplayName("Elephant 도착 가능 Position인 경우 True")
    void calculateAvailableArrivedPositionTrueTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO,new Position(new Row(10),new Column(2)));
        //when
//        boolean isArrive = elephant.isAvailableArrivePosition(new Position(new Row(7), new Column(4)), List.of(Movement.UP_UP_RIGHT,Movement.RIGHT_UP));
        //then
//        Assertions.assertThat(isArrive).isTrue();
    }

    @Test
    @DisplayName("Elephant 도착 가능 Position이 아닌 경우 False ")
    void calculateAvailableArrivedPositionFalseTest() {
        //given
        Elephant elephant = new Elephant(Team.CHO,new Position(new Row(10),new Column(2)));
        //when
//        boolean isArrive = elephant.isAvailableArrivePosition(new Position(new Row(7), new Column(4)), List.of(Movement.UP_UP_RIGHT,Movement.RIGHT_UP));
        //then
//        Assertions.assertThat(isArrive).isTrue();
    }
}
