package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import janggi.domain.piece.behavior.straightmove.Cannon;
import janggi.domain.piece.behavior.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @DisplayName("전달 받은 팀이 자신과 같은 팀이면 true를 반환한다.")
    @Test
    void test1() {
        // given
        Piece piece = new Piece(Side.HAN, new Soldier());

        // when
        boolean actual = piece.isSameSide(Side.HAN);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("상대 팀의 기물이면 false를 반환한다.")
    @Test
    void test2() {
        // given
        Piece piece = new Piece(Side.CHO, new Soldier());

        // when
        boolean actual = piece.isSameSide(Side.HAN);

        // then
        assertThat(actual).isFalse();
    }
}
