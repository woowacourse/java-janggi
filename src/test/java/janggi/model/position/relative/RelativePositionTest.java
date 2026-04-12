package janggi.model.position.relative;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.model.position.absolute.Column;
import janggi.model.position.absolute.Position;
import janggi.model.position.absolute.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RelativePositionTest {

    @DisplayName("from에서 rowOffset과 columnOffset만큼 움직인 결과 위치를 반환한다.")
    @Test
    void moved() {
        //given
        Position from = new Position(Row.TWO, Column.TWO);
        RelativePosition relativePosition = new RelativePosition(2, 2);

        assertThat(
                relativePosition.moved(from)
        ).isEqualTo(new Position(Row.FOUR, Column.FOUR));
    }

    @DisplayName("보드 밖으로 이동하면 예외가 발생한다.")
    @Test
    void moved_out_of_board() {
        //given
        Position from = new Position(Row.TWO, Column.TWO);
        RelativePosition relativePosition = new RelativePosition(10, 10);

        assertThatThrownBy(() -> relativePosition.moved(from))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("보드 밖으로 이동할 수 없습니다.");
    }
}
