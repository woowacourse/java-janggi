package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.piece.ChuSoldier;
import janggi.domain.piece.Dynasty;
import janggi.domain.piece.General;
import janggi.domain.piece.Point;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiBoardTest {

    @DisplayName("기물을 움직일 수 있다")
    @Test
    void move() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(9, 5), new ChuSoldier()
        ));

        //when
        janggiBoard.move(Dynasty.CHU, new Point(9, 5), new Point(8, 5));

        //then
        assertThat(janggiBoard).isEqualTo(new JanggiBoard(Map.of(
                new Point(8, 5), new ChuSoldier()
        )));
    }

    @DisplayName("시작 위치에 기물이 없다면 예외가 발생한다.")
    @Test
    void move_whenNotExistPiece() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        //when
        assertThatThrownBy(() -> janggiBoard.move(Dynasty.CHU, new Point(9, 5), new Point(8, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("다른 나라의 기물을 움직이면 예외가 발생한다.")
    @Test
    void move_whenOtherDynastyPiece() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(9, 5), new ChuSoldier())
        );

        //when
        assertThatThrownBy(() -> janggiBoard.move(Dynasty.HAN, new Point(9, 5), new Point(9, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자신의 나라 기물만 움직일 수 있습니다.");
    }

    @DisplayName("특정 나라의 국가가 죽었는지 확인할 수 있다.")
    @Test
    void isDeadKing() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(1, 4), new General(Dynasty.HAN)
        ));

        //when
        boolean result = janggiBoard.isDeadKing(Dynasty.CHU);

        //then
        assertThat(result).isTrue();
    }
}
