package janggi.position;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.model.position.Column;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import janggi.model.position.Row;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionPathTest {

    @DisplayName("가장 마지막에 있는 위치를 반환한다.")
    @Test
    void getDestination() {
        //given
        PositionPath positionPath = new PositionPath(List.of(
                new Position(Row.HAN_BACK, Column.EIGHT),
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));

        //when & then
        assertThat(positionPath.getDestination())
                .isEqualTo(new Position(Row.ONE, Column.SEVEN));
    }

    @DisplayName("다른 경로와 연결 시 뒷 부분에 붙는다.")
    @Test
    void concatenate() {
        //given
        PositionPath positionPath1 = new PositionPath(List.of(
                new Position(Row.ONE, Column.EIGHT),
                new Position(Row.ONE, Column.SEVEN)
        ));
        PositionPath positionPath2 = new PositionPath(List.of(
                new Position(Row.ONE, Column.SEVEN),
                new Position(Row.TWO, Column.SEVEN)
        ));

        //when& then
        assertThat(PositionPath.concatenate(positionPath1, positionPath2).getDestination())
                .isEqualTo(new Position(Row.TWO, Column.SEVEN));

    }
}
