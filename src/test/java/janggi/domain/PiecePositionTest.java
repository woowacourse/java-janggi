package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Side;
import janggi.domain.piece.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PiecePositionTest {

    @DisplayName("기물의 포지션을 변경한다.")
    @Test
    void test1() {
        // given
        Position position = new Position(7, 1);
        Piece piece = new Piece(Side.CHO, new Soldier());
        PiecePosition piecePosition = new PiecePosition(position, piece);

        Position movePosition = new Position(6, 1);

        // when
        piecePosition.move(movePosition);

        // then
        assertThat(position).isEqualTo(movePosition);
    }


    // TODO
    @DisplayName("기물이 움직일 수 없는 포지션이라면 예외를 반환한다.")
    @Test
    void test2() {

    }
}
