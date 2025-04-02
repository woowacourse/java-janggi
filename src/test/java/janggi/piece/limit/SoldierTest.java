package janggi.piece.limit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.move.Route;
import janggi.domain.piece.limit.Soldier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SoldierTest {

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다 - 초나라")
    void test1() {
        Soldier soldier = new Soldier(Turn.CHO);
        Position currentPosition = new Position(0, 6);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(1, 6),
                        new Position(0, 5));
    }

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다 - 한나라")
    void test2() {
        Soldier soldier = new Soldier(Turn.HAN);
        Position currentPosition = new Position(0, 3);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(1, 3),
                        new Position(0, 4));

    }

    @Test
    @DisplayName("궁성의 중앙에 위치할 경우 대각선 이동을 포함하여 5개의 이동을 반환한다 - 초나라")
    void test3() {
        Soldier soldier = new Soldier(Turn.CHO);
        Position currentPosition = new Position(4, 1);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(3, 1),
                        new Position(5, 1),
                        new Position(4, 0),
                        new Position(3, 0),
                        new Position(5, 0));
    }

    @Test
    @DisplayName("출발과 도착의 좌표가 궁성 내부인 것의 이동만 리턴한다. - 초나라")
    void test4() {
        Soldier soldier = new Soldier(Turn.CHO);
        Position currentPosition = new Position(3, 2);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(3, 1),
                        new Position(4, 2),
                        new Position(4, 1));
    }

    @Test
    @DisplayName("궁성 내부 좌표여도, 대각선으로 이어지지 않은 좌표라면 대각선 이동이 불가능하다. - 초나라")
    void test5() {
        Soldier soldier = new Soldier(Turn.CHO);
        Position currentPosition = new Position(4, 2);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(3, 2),
                        new Position(5, 2),
                        new Position(4, 1));
    }

    @Test
    @DisplayName("궁성의 중앙에 위치할 경우 대각선 이동을 포함하여 5개의 이동을 반환한다 - 한나라")
    void test6() {
        Soldier soldier = new Soldier(Turn.HAN);
        Position currentPosition = new Position(4, 8);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(3, 8),
                        new Position(5, 8),
                        new Position(4, 9),
                        new Position(3, 9),
                        new Position(5, 9));
    }

    @Test
    @DisplayName("출발과 도착의 좌표가 궁성 내부인 것의 이동만 리턴한다. - 한나라")
    void test7() {
        Soldier soldier = new Soldier(Turn.HAN);
        Position currentPosition = new Position(5, 7);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(4, 7),
                        new Position(4, 8),
                        new Position(5, 8));
    }

    @Test
    @DisplayName("궁성 내부 좌표여도, 대각선으로 이어지지 않은 좌표라면 대각선 이동이 불가능하다. - 한나라")
    void test8() {
        Soldier soldier = new Soldier(Turn.HAN);
        Position currentPosition = new Position(4, 7);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertThat(candidatePositions).extracting(Route::getLastPosition)
                .contains(new Position(3, 7),
                        new Position(4, 8),
                        new Position(5, 7));
    }

    @Test
    @DisplayName("졸이 3, 2의 위치 (궁성의 왼쪽 아래 꼭지점)에서는 가능한 이동이 대각선 이동 포함 4개이다.")
    void test9() {
        Soldier soldier = new Soldier(Turn.CHO);
        Position position = new Position(3, 2);

        List<Route> reachableDestinations = soldier.computeCandidatePositions(position);

        assertThat(reachableDestinations).extracting(Route::getLastPosition)
                .contains(new Position(2, 2),
                        new Position(3, 1),
                        new Position(4, 2),
                        new Position(4, 1));
    }

    @Test
    @DisplayName("졸이 3, 1의 위치 (궁성의 왼쪽 가운데)에서는 가능한 이동이 3개이다. (대각선 이동 불가)")
    void test10() {
        Soldier soldier = new Soldier(Turn.CHO);
        Position position = new Position(3, 1);

        List<Route> reachableDestinations = soldier.computeCandidatePositions(position);

        assertThat(reachableDestinations).extracting(Route::getLastPosition)
                .contains(new Position(3, 0),
                        new Position(2, 1),
                        new Position(4, 1));
    }

    @Test
    @DisplayName("졸이 4, 1의 위치 (궁성의 중앙)에서는 가능한 이동이 대각선 이동 포함 5개이다.")
    void test11() {
        Soldier soldier = new Soldier(Turn.CHO);
        Position position = new Position(4, 1);

        List<Route> reachableDestinations = soldier.computeCandidatePositions(position);

        assertThat(reachableDestinations).extracting(Route::getLastPosition)
                .contains(new Position(3, 1),
                        new Position(3, 0),
                        new Position(4, 0),
                        new Position(5, 1),
                        new Position(5, 0));
    }
}
