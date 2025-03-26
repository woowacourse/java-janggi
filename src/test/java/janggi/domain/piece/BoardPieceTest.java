package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("보드 말 테스트")
class BoardPieceTest {

    @DisplayName("원하는 위치로 이동할 수 있는지 검사한다.")
    @Test
    void moveTest() {
        // given
        BoardPiece boardPiece = new BoardPiece(new HanSoldier(), Dynasty.HAN);
        Point start = new Point(1, 1);
        Point end = new Point(2, 1);
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                start, boardPiece
        ));

        // when
        boolean canMove = boardPiece.canMove(janggiBoard, Dynasty.HAN, start, end);

        // then
        assertThat(canMove)
                .isTrue();
    }

    @DisplayName("원하는 위치로 이동할 수 없는 경우 예외가 발생한다.")
    @Test
    void moveFailTest_WhenIsNotMovable() {
        // given
        BoardPiece boardPiece = new BoardPiece(new HanSoldier(), Dynasty.HAN);
        Point start = new Point(1, 1);
        Point end = new Point(2, 5);
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                start, boardPiece
        ));

        // then
        assertThatThrownBy(() -> boardPiece.canMove(janggiBoard, Dynasty.HAN, start, end))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("같은 나라인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HAN, CHU, False",
            "HAN, HAN, True"
    })
    void isSameDynasty_True(Dynasty boardPieceDynasty, Dynasty dynasty, boolean expected) {
        // given
        BoardPiece boardPiece = new BoardPiece(new General(), boardPieceDynasty);

        // when, then
        assertThat(boardPiece.isSameDynasty(dynasty))
                .isSameAs(expected);
    }

    @DisplayName("같은 종류의 기물인지 확인한다.")
    @Test
    void isEqualPieceType() {
        // given
        BoardPiece boardPiece = new BoardPiece(new General(), Dynasty.HAN);

        // when
        assertThat(boardPiece.isEqualPieceType(new General()))
                .isTrue();
        assertThat(boardPiece.isEqualPieceType(new Cannon()))
                .isFalse();
    }
}