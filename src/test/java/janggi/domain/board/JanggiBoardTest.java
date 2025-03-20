package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Dynasty;
import janggi.domain.board.point.ChuPoint;
import janggi.domain.board.point.DefaultPoint;
import janggi.domain.board.point.HanPoint;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.General;
import janggi.domain.piece.Soldier;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JanggiBoardTest {


    @DisplayName("위치에 있는 기물을 찾을 수 있다.")
    @Test
    void findPointPieceTest() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new ChuPoint(9, 5), new General(), Dynasty.CHU)
        ));

        //when
        Optional<BoardPiece> result = janggiBoard.findPointPiece(new HanPoint(9, 5));

        //then
        assertThat(result).isEqualTo(Optional.of(new BoardPiece(new ChuPoint(9, 5), new General(), Dynasty.CHU)));
    }

    @DisplayName("해당 위치에 기물이 없을 수 있다.")
    @Test
    void notFindPointPieceTest() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of());

        //when
        Optional<BoardPiece> result = janggiBoard.findPointPiece(new HanPoint(9, 5));

        //then
        assertThat(result).isNotPresent();
    }

    @DisplayName("해당 위치에 있는 기물이 있는지 확인할 수 있다.")
    @Test
    void isExistPieceTest() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new ChuPoint(9, 5), new General(), Dynasty.CHU)
        ));

        //when
        boolean result = janggiBoard.isExistPiece(new HanPoint(9, 5));

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("해당 위치에 있는 기물이 없는지 확인할 수 있다.")
    @Test
    void isNotExistPieceTest() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of());

        //when
        boolean result = janggiBoard.isExistPiece(new HanPoint(9, 5));

        //then
        assertThat(result).isFalse();
    }

    @DisplayName("기물을 움직일 수 있다")
    @Test
    void move() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new ChuPoint(9, 5), new Soldier(), Dynasty.CHU)
        ));

        //when
        janggiBoard.move(Dynasty.CHU, new DefaultPoint(9, 5), new DefaultPoint(8, 5));

        //then
        assertThat(janggiBoard).isEqualTo(new JanggiBoard(Set.of(
                new BoardPiece(new ChuPoint(8, 5), new Soldier(), Dynasty.CHU)
        )));
    }

    @DisplayName("시작 위치에 기물이 없다면 예외가 발생한다.")
    @Test
    void move_whenNotExistPiece() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new ChuPoint(7, 5), new Soldier(), Dynasty.CHU)
        ));

        //when
        assertThatThrownBy(() -> janggiBoard.move(Dynasty.CHU, new DefaultPoint(9, 5), new DefaultPoint(8, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("다른 나라의 기물을 움직이면 예외가 발생한다.")
    @Test
    void move_whenOtherDynastyPiece() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Set.of(
                new BoardPiece(new ChuPoint(9, 5), new Soldier(), Dynasty.CHU)
        ));

        //when
        assertThatThrownBy(() -> janggiBoard.move(Dynasty.HAN, new DefaultPoint(9, 5), new DefaultPoint(8, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자신의 나라 기물이 아닙니다.");
    }
}
