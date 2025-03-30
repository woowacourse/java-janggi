package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.PieceFactory;


class SoldierTest {

    @DisplayName("한나라 졸: (1,1)에서 (1,0)으로 전진 이동 – 올바른 전진")
    @Test
    void soldierRed_전진() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 0);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("한나라 졸: (1,1)에서 (2,1)로 우측 이동")
    @Test
    void soldierRed_우측횡진() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(2, 1);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("한나라 졸: (1,1)에서 (0,1)로 좌측 이동")
    @Test
    void soldierRed_좌측횡진() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(0, 1);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("한나라 졸: (1,1)에서 (1,2)로 후진 이동 불가")
    @Test
    void soldierRed_후진불가() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 2);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("한나라 졸: (1,1)에서 (5,6)로 과도하게 이동 불가 – 한 칸 이동 규칙 위반")
    @Test
    void soldierRed_과도한이동불가() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(5, 6);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("한나라 졸: (3,2)에서 (4,1)로 대각 이동 – 궁성 내 전진 대각 이동 허용")
    @Test
    void soldierRed_궁성내대각이동_1() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(3, 2);
        final Point end = Point.newInstance(4, 1);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("한나라 졸: (5,2)에서 (4,1)로 대각 이동 – 궁성 내 전진 대각 이동 허용")
    @Test
    void soldierRed_궁성내대각이동_2() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(5, 2);
        final Point end = Point.newInstance(4, 1);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("한나라 졸: (4,1)에서 (3,0)로 대각 이동 – 궁성 내 전진 대각 이동 허용")
    @Test
    void soldierRed_궁성내대각이동_3() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(4, 1);
        final Point end = Point.newInstance(3, 0);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("한나라 졸: (4,1)에서 (5,1)로 좌우 이동 – 전진 없이 횡진만 가능한 경우 허용")
    @Test
    void soldierRed_횡진만이동() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(4, 1);
        final Point end = Point.newInstance(5, 1);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("한나라 졸: (3,3)에서 (2,2)로 대각 이동 불가 – 잘못된 대각 이동")
    @Test
    void soldierRed_대각불가_1() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(3, 3);
        final Point end = Point.newInstance(2, 2);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("한나라 졸: (3,2)에서 (2,1)로 대각 이동 불가 – 잘못된 대각 이동")
    @Test
    void soldierRed_대각불가_2() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(3, 2);
        final Point end = Point.newInstance(2, 1);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("한나라 졸: (3,1)에서 (4,0)로 대각 이동 불가 – 잘못된 대각 이동")
    @Test
    void soldierRed_대각불가_3() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(3, 1);
        final Point end = Point.newInstance(4, 0);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("한나라 졸: (4,0)에서 (5,1)로 대각 이동 불가 – 잘못된 대각 이동")
    @Test
    void soldierRed_대각불가_4() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(4, 0);
        final Point end = Point.newInstance(5, 1);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("한나라 졸: (5,1)에서 (4,2)로 대각 이동 불가 – 잘못된 대각 이동")
    @Test
    void soldierRed_대각불가_5() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(5, 1);
        final Point end = Point.newInstance(4, 2);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("한나라 졸: (4,2)에서 (3,1)로 대각 이동 불가 – 잘못된 대각 이동")
    @Test
    void soldierRed_대각불가_6() {
        final Soldier soldier = PieceFactory.createRedTeam(Soldier::new);
        final Point start = Point.newInstance(4, 2);
        final Point end = Point.newInstance(3, 1);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("초나라 졸: (1,1)에서 (1,2)로 전진 이동 – 올바른 전진")
    @Test
    void soldierGreen_전진() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 2);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("초나라 졸: (1,1)에서 (2,1)로 우측 이동 – 횡진 이동 허용")
    @Test
    void soldierGreen_우측횡진() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(2, 1);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("초나라 졸: (1,1)에서 (0,1)로 좌측 이동 – 횡진 이동 허용")
    @Test
    void soldierGreen_좌측횡진() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(0, 1);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("초나라 졸: (1,1)에서 (1,0)으로 후진 이동 불가 – 졸은 후진 불가")
    @Test
    void soldierGreen_후진불가() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(1, 0);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("초나라 졸: (1,1)에서 (5,6)로 과도하게 이동 불가 – 한 칸 이동 규칙 위반")
    @Test
    void soldierGreen_과도한이동불가() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(1, 1);
        final Point end = Point.newInstance(5, 6);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("초나라 졸: 궁성 내 (3,7)에서 (4,8)로 대각 이동 – 올바른 궁성 내 전진 대각 이동")
    @Test
    void soldierGreen_궁성내대각이동_1() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(3, 7);
        final Point end = Point.newInstance(4, 8);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("초나라 졸: 궁성 내 (5,7)에서 (4,8)로 대각 이동 – 올바른 궁성 내 전진 대각 이동")
    @Test
    void soldierGreen_궁성내대각이동_2() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(5, 7);
        final Point end = Point.newInstance(4, 8);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("초나라 졸: 궁성 내 (4,8)에서 (3,9)로 대각 이동 – 올바른 궁성 내 전진 대각 이동")
    @Test
    void soldierGreen_궁성내대각이동_3() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(4, 8);
        final Point end = Point.newInstance(3, 9);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("초나라 졸: 궁성 내 (4,8)에서 (5,9)로 대각 이동 – 올바른 궁성 내 전진 대각 이동")
    @Test
    void soldierGreen_궁성내대각이동_4() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(4, 8);
        final Point end = Point.newInstance(5, 9);
        assertThat(soldier.isMovable(start, end)).isTrue();
    }

    @DisplayName("초나라 졸: 궁성 내 (3,7)에서 (5,9)로 이동 불가 – 과도한 대각 이동")
    @Test
    void soldierGreen_궁성내대각불가_1() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(3, 7);
        final Point end = Point.newInstance(5, 9);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("초나라 졸: 궁성 내 (3,6)에서 (4,7)로 이동 불가 – 올바르지 않은 대각 이동")
    @Test
    void soldierGreen_궁성내대각불가_2() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(3, 6);
        final Point end = Point.newInstance(4, 7);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("초나라 졸: 궁성 내 (3,8)에서 (4,7)로 이동 불가 – 올바르지 않은 대각 이동")
    @Test
    void soldierGreen_궁성내대각불가_3() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(3, 8);
        final Point end = Point.newInstance(4, 7);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("초나라 졸: 궁성 내 (4,7)에서 (5,8)로 이동 불가 – 올바르지 않은 대각 이동")
    @Test
    void soldierGreen_궁성내대각불가_4() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(4, 7);
        final Point end = Point.newInstance(5, 8);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("초나라 졸: 궁성 내 (5,8)에서 (4,9)로 이동 불가 – 올바르지 않은 대각 이동")
    @Test
    void soldierGreen_궁성내대각불가_5() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(5, 8);
        final Point end = Point.newInstance(4, 9);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }

    @DisplayName("초나라 졸: 궁성 내 (4,9)에서 (3,8)로 이동 불가 – 올바르지 않은 대각 이동")
    @Test
    void soldierGreen_궁성내대각불가_6() {
        final Soldier soldier = PieceFactory.createGreenTeam(Soldier::new);
        final Point start = Point.newInstance(4, 9);
        final Point end = Point.newInstance(3, 8);
        assertThat(soldier.isMovable(start, end)).isFalse();
    }
}
