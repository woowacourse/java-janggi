package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Dynasty;
import janggi.domain.board.point.ChuPoint;
import janggi.domain.board.point.HanPoint;
import janggi.domain.piece.King;
import janggi.domain.piece.BoardPiece;
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
                new BoardPiece(new ChuPoint(9, 5), King.newInstance(), Dynasty.CHU)
        ));

        //when
        Optional<BoardPiece> result = janggiBoard.findPointPiece(new HanPoint(9, 5));

        //then
        assertThat(result).isEqualTo(Optional.of(new BoardPiece(new ChuPoint(9, 5), King.newInstance(), Dynasty.CHU)));
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
                new BoardPiece(new ChuPoint(9, 5), King.newInstance(), Dynasty.CHU)
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
}
