package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceTest {

    @DisplayName("같은 팀의 기물이면 true를 반환한다.")
    @Test
    void test1() {
        // given
        Piece piece1 = new Piece(Side.HAN, new Soldier());
        Piece piece2 = new Piece(Side.HAN, new Soldier());

        // when
        boolean actual = piece1.isSameSide(piece2);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("상대 팀의 기물이면 false를 반환한다.")
    @Test
    void test2() {
        // given
        Piece piece1 = new Piece(Side.CHO, new Soldier());
        Piece piece2 = new Piece(Side.HAN, new Soldier());

        // when
        boolean actual = piece1.isSameSide(piece2);

        // then
        assertThat(actual).isFalse();
    }
}
