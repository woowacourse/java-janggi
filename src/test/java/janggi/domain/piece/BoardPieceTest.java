package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Dynasty;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.point.DefaultPoint;
import janggi.domain.board.point.HanPoint;
import janggi.domain.board.point.Point;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BoardPieceTest {

    @DisplayName("원하는 위치로 이동한다.")
    @Test
    void moveTest() {
        // given
        BoardPiece boardPiece = new BoardPiece(new HanPoint(1, 1), Pawn.newInstance(), Dynasty.HAN);
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                boardPiece
        ));
        Point endPoint = new DefaultPoint(2, 1);

        // when
        boardPiece.move(janggiBoard, endPoint);

        // then
        assertThat(boardPiece)
                .isEqualTo(new BoardPiece(new HanPoint(2, 1), Pawn.newInstance(), Dynasty.HAN));
    }

    @DisplayName("원하는 위치에 같은 편의 말이 있으면 예외가 발생한다.")
    @Test
    void moveFailTest_WhenExistSameDynastyPiece() {
        // given
        BoardPiece samePiece = new BoardPiece(new HanPoint(2, 1), Pawn.newInstance(), Dynasty.HAN);
        BoardPiece boardPiece = new BoardPiece(new HanPoint(1, 1), Pawn.newInstance(), Dynasty.HAN);
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                boardPiece, samePiece
        ));
        Point endPoint = new DefaultPoint(2, 1);

        // when

        // then
        assertThatThrownBy(() -> boardPiece.move(janggiBoard, endPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 놓여져 있는 기물이 존재합니다.");
    }

    @DisplayName("원하는 위치로 이동할 수 없는 경우 예외가 발생한다.")
    @Test
    void moveFailTest_WhenIsNotMovable() {
        // given
        BoardPiece boardPiece = new BoardPiece(new HanPoint(1, 1), Pawn.newInstance(), Dynasty.HAN);
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                boardPiece
        ));
        Point endPoint = new DefaultPoint(2, 5);

        // when

        // then
        assertThatThrownBy(() -> boardPiece.move(janggiBoard, endPoint))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 위치로 이동할 수 없습니다.");
    }

    @DisplayName("같은 위치에 놓여있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "1, 1, 1, 1, true",
            "1, 1, 1, 2, false",
            "1, 1, 2, 1, false",
    })
    void isSamePosition(int x1, int y1, int x2, int y2, boolean expected) {
        //given
        BoardPiece boardPiece = new BoardPiece(new HanPoint(x1, y1), Pawn.newInstance(), Dynasty.HAN);

        //when
        boolean result = boardPiece.isSamePosition(new DefaultPoint(x2, y2));

        //then
        assertThat(result).isEqualTo(expected);
    }
}