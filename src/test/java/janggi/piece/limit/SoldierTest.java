package janggi.piece.limit;

import janggi.board.Position;
import janggi.move.Route;
import janggi.piece.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierTest {

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다 - 초나라")
    void test1() {
        Soldier soldier = new Soldier(Side.CHO);
        Position currentPosition = new Position(0, 6);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(1, 6),
                        new Position(-1, 6),
                        new Position(0, 5));
    }

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다 - 한나라")
    void test2() {
        Soldier soldier = new Soldier(Side.HAN);
        Position currentPosition = new Position(0, 3);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(1, 3),
                        new Position(-1, 3),
                        new Position(0, 4));

    }

    @Test
    @DisplayName("궁성에 위치할 경우 대각선 이동을 포함하여 5개의 이동을 반환한다 - 초나라")
    void test3() {
        Soldier soldier = new Soldier(Side.CHO);
        Position currentPosition = new Position(4, 2);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(3, 2),
                        new Position(5, 2),
                        new Position(4, 1),
                        new Position(3, 1),
                        new Position(5, 1));
    }

    @Test
    @DisplayName("궁성에 위치할 경우 대각선 이동을 포함하여 5개의 이동을 반환한다 - 한나라")
    void test4() {
        Soldier soldier = new Soldier(Side.HAN);
        Position currentPosition = new Position(4, 7);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(3, 7),
                        new Position(5, 7),
                        new Position(4, 8),
                        new Position(3, 8),
                        new Position(5, 8));
    }
}
