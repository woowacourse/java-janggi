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

class ChuTest {

    @Test
    @DisplayName("초나라 팀은 초기 배치대로 기물을 가진다.")
    void createInitialChu() {
        // given
        Chu chu = Chu.createInitialChu();

        // when & then
        assertAll(
            () -> assertThat(chu.makeSnapShot()).hasSize(16),
            () -> assertThat(chu.findPiece(new Position(1, 1))).get().isInstanceOf(Cha.class),
            () -> assertThat(chu.findPiece(new Position(9, 1))).get().isInstanceOf(Cha.class),
            () -> assertThat(chu.findPiece(new Position(2, 1))).get().isInstanceOf(Ma.class),
            () -> assertThat(chu.findPiece(new Position(8, 1))).get().isInstanceOf(Ma.class),
            () -> assertThat(chu.findPiece(new Position(3, 1))).get().isInstanceOf(Sang.class),
            () -> assertThat(chu.findPiece(new Position(7, 1))).get().isInstanceOf(Sang.class),
            () -> assertThat(chu.findPiece(new Position(4, 1))).get().isInstanceOf(Sa.class),
            () -> assertThat(chu.findPiece(new Position(6, 1))).get().isInstanceOf(Sa.class),
            () -> assertThat(chu.findPiece(new Position(5, 2))).get().isInstanceOf(Gung.class),
            () -> assertThat(chu.findPiece(new Position(2, 3))).get().isInstanceOf(Po.class),
            () -> assertThat(chu.findPiece(new Position(8, 3))).get().isInstanceOf(Po.class),
            () -> assertThat(chu.findPiece(new Position(1, 4))).get().isInstanceOf(Jol.class),
            () -> assertThat(chu.findPiece(new Position(3, 4))).get().isInstanceOf(Jol.class),
            () -> assertThat(chu.findPiece(new Position(5, 4))).get().isInstanceOf(Jol.class),
            () -> assertThat(chu.findPiece(new Position(7, 4))).get().isInstanceOf(Jol.class),
            () -> assertThat(chu.findPiece(new Position(9, 4))).get().isInstanceOf(Jol.class),
            () -> assertThat(chu.findPiece(new Position(5, 5))).isEmpty()
        );
    }

    @Test
    @DisplayName("기물을 이동한 새 팀 상태를 반환한다.")
    void move() {
        // given
        Team chu = Chu.createInitialChu();
        Position chuJolPosition = new Position(1, 4);
        Position movedChuJolPosition = new Position(1, 5);

        // when
        Team movedChu = chu.move(chuJolPosition, movedChuJolPosition);

        // then
        assertAll(
            () -> assertThat(movedChu.findPiece(chuJolPosition)).isEmpty(),
            () -> assertThat(movedChu.findPiece(movedChuJolPosition)).get().isInstanceOf(Jol.class),
            () -> assertThat(chu.findPiece(movedChuJolPosition)).isEmpty(),
            () -> assertThat(chu.findPiece(chuJolPosition)).get().isInstanceOf(Jol.class)
        );
    }

    @Test
    @DisplayName("기물을 제거하면 새 팀 상태를 반환하여 원본은 유지한다.")
    void remove() {
        // given
        Team chu = Chu.createInitialChu();
        Position chuJolPosition = new Position(1, 4);

        // when
        Team removedChu = chu.remove(chuJolPosition);

        // then
        assertAll(
            () -> assertThat(removedChu.findPiece(chuJolPosition)).isEmpty(),
            () -> assertThat(removedChu.makeSnapShot()).hasSize(15),
            () -> assertThat(chu.findPiece(chuJolPosition)).get().isInstanceOf(Jol.class)
        );
    }
}
