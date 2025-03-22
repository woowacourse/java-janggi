package janggi.piece;

import janggi.board.Position;
import janggi.move.Route;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SoldierTest {

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다 - 초나라")
    void computeCandidatePositions() {

        Soldier soldier = new Soldier(Side.CHO);
        Position currentPosition = new Position(0, 6);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertAll(
                () -> assertThat(candidatePositions).hasSize(3),
                () -> assertThat(candidatePositions.getLast().getLastPosition()).isEqualTo(new Position(1, 6))
        );
    }

    @Test
    @DisplayName("현재 위치를 받아와 움직일 수 있는 위치 후보군을 반환한다 - 한나라")
    void computeCandidatePositions2() {

        Soldier soldier = new Soldier(Side.HAN);
        Position currentPosition = new Position(0, 3);
        List<Route> candidatePositions = soldier.computeCandidatePositions(currentPosition);

        assertAll(
                () -> assertThat(candidatePositions).hasSize(3),
                () -> assertThat(candidatePositions.getLast().getLastPosition()).isEqualTo(new Position(1, 3))
        );
    }
}
