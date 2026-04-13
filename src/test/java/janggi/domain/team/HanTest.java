package janggi.domain.team;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import janggi.domain.Position;
import janggi.domain.piece.Cha;
import janggi.domain.piece.Gung;
import janggi.domain.piece.Jol;
import janggi.domain.piece.Ma;
import janggi.domain.piece.Po;
import janggi.domain.piece.Sa;
import janggi.domain.piece.Sang;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HanTest {

    @Test
    @DisplayName("한나라 팀은 초기 배치대로 기물을 가진다.")
    void createInitialHan() {
        // given
        Han han = Han.createInitialHan();

        // when & then
        assertAll(
            () -> assertThat(han.makeSnapShot()).hasSize(16),
            () -> assertThat(han.findPiece(new Position(1, 10))).get().isInstanceOf(Cha.class),
            () -> assertThat(han.findPiece(new Position(9, 10))).get().isInstanceOf(Cha.class),
            () -> assertThat(han.findPiece(new Position(2, 10))).get().isInstanceOf(Ma.class),
            () -> assertThat(han.findPiece(new Position(8, 10))).get().isInstanceOf(Ma.class),
            () -> assertThat(han.findPiece(new Position(3, 10))).get().isInstanceOf(Sang.class),
            () -> assertThat(han.findPiece(new Position(7, 10))).get().isInstanceOf(Sang.class),
            () -> assertThat(han.findPiece(new Position(4, 10))).get().isInstanceOf(Sa.class),
            () -> assertThat(han.findPiece(new Position(6, 10))).get().isInstanceOf(Sa.class),
            () -> assertThat(han.findPiece(new Position(5, 9))).get().isInstanceOf(Gung.class),
            () -> assertThat(han.findPiece(new Position(2, 8))).get().isInstanceOf(Po.class),
            () -> assertThat(han.findPiece(new Position(8, 8))).get().isInstanceOf(Po.class),
            () -> assertThat(han.findPiece(new Position(1, 7))).get().isInstanceOf(Jol.class),
            () -> assertThat(han.findPiece(new Position(3, 7))).get().isInstanceOf(Jol.class),
            () -> assertThat(han.findPiece(new Position(5, 7))).get().isInstanceOf(Jol.class),
            () -> assertThat(han.findPiece(new Position(7, 7))).get().isInstanceOf(Jol.class),
            () -> assertThat(han.findPiece(new Position(9, 7))).get().isInstanceOf(Jol.class),
            () -> assertThat(han.findPiece(new Position(5, 6))).isEmpty()
        );
    }

    @Test
    @DisplayName("기물을 이동한 새 팀 상태를 반환한다.")
    void move() {
        // given
        Team han = Han.createInitialHan();

        // when
        Team movedHan = han.move(new Position(1, 7), new Position(1, 6));

        // then
        assertAll(
            () -> assertThat(movedHan.findPiece(new Position(1, 7))).isEmpty(),
            () -> assertThat(movedHan.findPiece(new Position(1, 6))).get().isInstanceOf(Jol.class),
            () -> assertThat(han.findPiece(new Position(1, 7))).get().isInstanceOf(Jol.class)
        );
    }

    @Test
    @DisplayName("기물을 제거하면 새 팀 상태를 반환하여 원본은 유지한다.")
    void remove() {
        // given
        Team han = Han.createInitialHan();

        // when
        Team removedHan = han.remove(new Position(1, 7));

        // then
        assertAll(
            () -> assertThat(removedHan.findPiece(new Position(1, 7))).isEmpty(),
            () -> assertThat(removedHan.makeSnapShot()).hasSize(15),
            () -> assertThat(han.findPiece(new Position(1, 7))).get().isInstanceOf(Jol.class)
        );
    }
}
