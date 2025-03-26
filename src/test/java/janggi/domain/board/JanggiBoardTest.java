package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Dynasty;
import janggi.domain.piece.BoardPiece;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.ChuSoldier;
import janggi.domain.piece.General;
import janggi.domain.piece.HanSoldier;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("장기 보드 테스트")
public class JanggiBoardTest {

    @DisplayName("장기판의 특정 위치에 기물이 있는지 알 수 있다.")
    @Test
    void isExistPiece_True() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(9, 5), new BoardPiece(new General(), Dynasty.CHU)
        ));

        //when
        boolean isExistPiece = janggiBoard.isExistPiece(new Point(9, 5));

        //then
        assertThat(isExistPiece)
                .isTrue();
    }

    @DisplayName("장기판의 특정 위치에 기물이 없는지 알 수 있다.")
    @Test
    void isExistPiece_False() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        //when
        boolean isExistPiece = janggiBoard.isExistPiece(new Point(9, 5));

        //then
        assertThat(isExistPiece)
                .isFalse();
    }


    @DisplayName("장기판의 특정 위치에 포가 놓여있는지 알 수 있다.")
    @Test
    void isExistCannon_True() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(9, 5), new BoardPiece(new Cannon(), Dynasty.CHU)
        ));

        //when
        boolean isExistCannon = janggiBoard.isExistCannon(new Point(9, 5));

        //then
        assertThat(isExistCannon)
                .isTrue();
    }

    @DisplayName("장기판의 특정 위치에 포가 없는지 알 수 있다.")
    @Test
    void isExistCannon_False() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
        ));

        //when
        boolean isExistCannon = janggiBoard.isExistCannon(new Point(9, 5));

        //then
        assertThat(isExistCannon)
                .isFalse();
    }

    @DisplayName("기물을 움직일 수 있다")
    @Test
    void move() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(3, 3), new BoardPiece(new HanSoldier(), Dynasty.HAN),
                new Point(4, 3), new BoardPiece(new ChuSoldier(), Dynasty.CHU)
        ));

        //when
        janggiBoard.move(Dynasty.HAN, new Point(3, 3), new Point(4, 3));

        //then
        assertThat(janggiBoard).isEqualTo(new JanggiBoard(Map.of(
                new Point(4, 3), new BoardPiece(new HanSoldier(), Dynasty.HAN)
        )));
    }

    @DisplayName("시작 위치에 기물이 없다면 예외가 발생한다.")
    @Test
    void move_whenNotExistPiece() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of());

        //when
        assertThatThrownBy(() -> janggiBoard.move(Dynasty.HAN, new Point(1, 2), new Point(2, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시작 위치에 기물이 존재하지 않습니다.");
    }

    @DisplayName("다른 나라의 기물을 움직이면 예외가 발생한다.")
    @Test
    void move_whenOtherDynastyPiece() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(4, 3), new BoardPiece(new ChuSoldier(), Dynasty.CHU)
        ));

        //when
        assertThatThrownBy(() -> janggiBoard.move(Dynasty.HAN, new Point(4, 3), new Point(2, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자신의 나라 기물이 아닙니다.");
    }

    @DisplayName("목적지에 자신의 나라 기물이 있다면 예외가 발생한다.")
    @Test
    void move_whenEndIsSameDynasty() {
        //given
        JanggiBoard janggiBoard = new JanggiBoard(Map.of(
                new Point(3, 4), new BoardPiece(new HanSoldier(), Dynasty.HAN),
                new Point(4, 3), new BoardPiece(new HanSoldier(), Dynasty.HAN)
        ));

        //when
        assertThatThrownBy(() -> janggiBoard.move(Dynasty.HAN, new Point(3, 4), new Point(4, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 놓여져 있는 기물이 존재합니다.");
    }
}
