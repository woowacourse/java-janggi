package janggi.piece.unlimit;

import janggi.domain.Turn;
import janggi.domain.board.Position;
import janggi.domain.move.Route;
import janggi.domain.piece.unlimit.Cannon;
import janggi.domain.piece.unlimit.Chariot;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CannonTest {

    @Test
    @DisplayName("포 이동 가능 후보군 리턴 테스트")
    void test1() {
        Cannon cannon = new Cannon(Turn.CHO);

        List<Route> candidatePositions = cannon.computeCandidateDirections(new Position(1, 7));

        assertAll(
                () -> assertThat(candidatePositions).hasSize(4),
                () -> assertThat(candidatePositions).anySatisfy(route -> {
                    assertThat(route.getLastPosition().getY()).isEqualTo(7);
                }),
                () -> assertThat(candidatePositions).anySatisfy(route -> {
                    assertThat(route.getLastPosition().getX()).isEqualTo(1);
                })
        );
    }

    @Test
    @DisplayName("궁성에 위치하면, 총 4개의 이동 가능 방향 리스트 반환 테스트")
    void test2() {
        Cannon cannon = new Cannon(Turn.CHO);
        List<Route> candidateDirections = cannon.computeCandidateDirections(new Position(5, 9));

        assertAll(
                () -> assertThat(candidateDirections.size()).isEqualTo(4),
                () -> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(3, 7));
                }),
                ()-> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(4, 8));
                }),
                () -> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(5, 6));
                })
        );
    }

    @Test
    @DisplayName("궁성의 중앙에 위치하면, 총 4개의 이동 가능 방향 리스트 반환 테스트 (대각선 이동 불가)")
    void test3() {
        Chariot chariot = new Chariot(Turn.CHO);
        List<Route> candidateDirections = chariot.computeCandidateDirections(new Position(4, 8));

        assertAll(
                () -> assertThat(candidateDirections.size()).isEqualTo(8),
                () -> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(3, 7));
                }),
                ()-> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(4, 9));
                }),
                () -> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(5, 7));
                }),
                () -> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(4, 0));
                }),
                () -> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(0, 8));
                }),
                () -> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(8, 8));
                }),
                () -> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(5, 9));
                }),
                () -> assertThat(candidateDirections).anySatisfy(route -> {
                    assertThat(route.getPositions()).contains(new Position(3, 9));
                })
        );
    }
}
