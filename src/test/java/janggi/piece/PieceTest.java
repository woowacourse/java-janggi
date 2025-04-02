package janggi.piece;

import janggi.domain.Turn;
import janggi.domain.piece.limit.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {

    @Test
    @DisplayName("기물이 아군일 경우 true 를 리턴")
    void test1() {
        Soldier soldier = new Soldier(Turn.CHO);

        assertThat(soldier.isAlly(new Soldier(Turn.CHO))).isTrue();
    }

    @Test
    @DisplayName("기물이 적군일 경우 false 를 리턴")
    void test2() {
        Soldier soldier = new Soldier(Turn.CHO);

        assertThat(soldier.isAlly(new Soldier(Turn.HAN))).isFalse();
    }
}