package janggi.piece.unlimit;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.move.Route;
import janggi.piece.Empty;
import janggi.piece.Piece;
import janggi.piece.Side;
import janggi.view.OutputView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CannonTest {

    @Test
    @DisplayName("포 이동 가능 후보군 리턴 테스트")
    void test1() {
        Cannon cannon = new Cannon(Side.CHO);

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
    @DisplayName("궁성에 위치하면, 총 8개의 이동 가능 방향 리스트 반환 테스트")
    void test2() {
        Cannon cannon = new Cannon(Side.CHO);
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
}
