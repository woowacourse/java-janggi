package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @DisplayName("전달 받은 팀이 자신과 같은 팀이면 true를 반환한다.")
    @Test
    void test1() {
        // given
        Piece piece = new Soldier(Side.HAN);

        // when
        boolean actual = piece.isSameSide(Side.HAN);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("상대 팀의 기물이면 false를 반환한다.")
    @Test
    void test2() {
        // given
        Piece piece = new Soldier(Side.CHO);

        // when
        boolean actual = piece.isSameSide(Side.HAN);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("자신의 기물이 Cannon이면 true를 반환한다.")
    @Test
    void test3() {
        // given
        Piece piece = new Cannon(Side.CHO);

        // when
        boolean actual = piece.isCannon();

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("자신의 기물이 Cannon이 아니면 false를 반환한다.")
    @Test
    void test4() {
        // given
        Piece piece = new Soldier(Side.CHO);

        // when
        boolean actual = piece.isCannon();

        // then
        assertThat(actual).isFalse();
    }
}
