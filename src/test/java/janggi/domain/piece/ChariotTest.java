package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("차 테스트")
class ChariotTest {

    @DisplayName("차가 가는 방향에 기물이 없다면 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "4, 4, 4, 1",
            "4, 4, 1, 4",
            "4, 4, 8, 4",
            "4, 4, 4, 8"
    })
    void isMovable(int x1, int y1, int x2, int y2) {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());
        Piece chariot = new Chariot();

        // when
        boolean isMovable = chariot.isMovable(janggiBoard, new Point(x1, y1), new Point(x2, y2));

        // then
        assertThat(isMovable).isTrue();
    }

    @DisplayName("차가 규칙 상 갈 수 없는 목적지는 갈 수 없다.")
    @Test
    void isNotMovable_WhenImpossibleEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());
        Piece chariot = new Chariot();

        // when
        boolean isMovable = chariot.isMovable(janggiBoard, new Point(1, 1), new Point(2, 2));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("차가 가는 방향에 기물이 있다면 이동할 수 없다.")
    @Test
    void isNotMovable_WhenPieceInPath() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(1, 2), new BoardPiece(new Horse(), Dynasty.HAN)
        ));
        Piece chariot = new Chariot();

        // when
        boolean isMovable = chariot.isMovable(janggiBoard, new Point(1, 1), new Point(1, 4));

        // then
        assertThat(isMovable)
                .isFalse();
    }

    @DisplayName("목적지에 상대편의 기물이 있는 경우에는 갈 수 있다.")
    @Test
    void isNotMovable_WhenOtherPieceInEndPoint() {
        // given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(1, 4), new BoardPiece(new Horse(), Dynasty.CHU)
        ));
        Piece chariot = new Chariot();

        // when
        boolean isMovable = chariot.isMovable(janggiBoard, new Point(1, 1), new Point(1, 4));

        // then
        assertThat(isMovable)
                .isTrue();
    }
}