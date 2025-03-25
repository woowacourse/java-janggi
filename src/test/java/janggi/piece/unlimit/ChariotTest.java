package janggi.piece.unlimit;

import janggi.board.Position;
import janggi.move.Route;
import janggi.piece.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ChariotTest {

    @Test
    @DisplayName("차 이동 가능 후보군 리턴 테스트")
    void test1() {
        Chariot chariot = new Chariot(Side.CHO);

        List<Route> candidatePositions = chariot.computeCandidatePositions(new Position(2, 9));

        assertAll(
                () -> assertThat(candidatePositions).hasSize(4),
                () -> assertThat(candidatePositions).anySatisfy(route -> {
                    assertThat(route.getLastPosition().getY()).isEqualTo(9);
                }),
                () -> assertThat(candidatePositions).anySatisfy(route -> {
                    assertThat(route.getLastPosition().getX()).isEqualTo(2);
                })
        );
    }

}
