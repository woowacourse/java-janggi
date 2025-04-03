package domain.hurdlePolicy;

import domain.janggiPiece.Chariot;
import domain.janggiPiece.Elephant;
import domain.janggiPiece.JanggiChessPiece;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.position.JanggiPositions;
import domain.type.JanggiTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class StopAtHurdlePolicyTest {
    private final HurdlePolicy policy = new StopAtHurdlePolicy();
    private final JanggiPosition startPosition = JanggiPositionFactory.of(5, 3);
    private final JanggiTeam team = JanggiTeam.BLUE;
    private final JanggiChessPiece chariot = new Chariot(team);

    @DisplayName("경로 사이에 아군이 존재하는 경우, 해당 위치 직전까지 움직일 수 있다.")
    @Test
    void existSameTeam() {
        // given
        final List<Path> coordinates = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(3, 3),
                        JanggiPositionFactory.of(2, 3),
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(0, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 2),
                        JanggiPositionFactory.of(5, 1),
                        JanggiPositionFactory.of(5, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(6, 3),
                        JanggiPositionFactory.of(7, 3),
                        JanggiPositionFactory.of(8, 3),
                        JanggiPositionFactory.of(9, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 4),
                        JanggiPositionFactory.of(5, 5),
                        JanggiPositionFactory.of(5, 6),
                        JanggiPositionFactory.of(5, 7),
                        JanggiPositionFactory.of(5, 8)
                ))
        );
        JanggiPositions positions = new JanggiPositions(Map.of(
                startPosition, chariot,
                JanggiPositionFactory.of(8, 3), new Elephant(JanggiTeam.BLUE)
        ));
        List<JanggiPosition> expected = List.of(
                JanggiPositionFactory.of(6, 3),
                JanggiPositionFactory.of(7, 3),

                JanggiPositionFactory.of(4, 3),
                JanggiPositionFactory.of(3, 3),
                JanggiPositionFactory.of(2, 3),
                JanggiPositionFactory.of(1, 3),
                JanggiPositionFactory.of(0, 3),

                JanggiPositionFactory.of(5, 2),
                JanggiPositionFactory.of(5, 1),
                JanggiPositionFactory.of(5, 0),

                JanggiPositionFactory.of(5, 4),
                JanggiPositionFactory.of(5, 5),
                JanggiPositionFactory.of(5, 6),
                JanggiPositionFactory.of(5, 7),
                JanggiPositionFactory.of(5, 8)
        );

        // when
        List<JanggiPosition> destinations = policy.pickDestinations(team, coordinates, positions);

        // then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("경로 사이에 적군이 존재하는 경우, 해당 위치까지 움직일 수 있다.")
    @Test
    void existEnemy() {
        // given
        final List<Path> coordinates = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(3, 3),
                        JanggiPositionFactory.of(2, 3),
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(0, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 2),
                        JanggiPositionFactory.of(5, 1),
                        JanggiPositionFactory.of(5, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(6, 3),
                        JanggiPositionFactory.of(7, 3),
                        JanggiPositionFactory.of(8, 3),
                        JanggiPositionFactory.of(9, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 4),
                        JanggiPositionFactory.of(5, 5),
                        JanggiPositionFactory.of(5, 6),
                        JanggiPositionFactory.of(5, 7),
                        JanggiPositionFactory.of(5, 8)
                ))
        );
        JanggiPositions positions = new JanggiPositions(Map.of(
                startPosition, chariot,
                JanggiPositionFactory.of(8, 3), new Elephant(JanggiTeam.RED)
        ));
        List<JanggiPosition> expected = List.of(
                JanggiPositionFactory.of(6, 3),
                JanggiPositionFactory.of(7, 3),
                JanggiPositionFactory.of(8, 3),

                JanggiPositionFactory.of(4, 3),
                JanggiPositionFactory.of(3, 3),
                JanggiPositionFactory.of(2, 3),
                JanggiPositionFactory.of(1, 3),
                JanggiPositionFactory.of(0, 3),

                JanggiPositionFactory.of(5, 2),
                JanggiPositionFactory.of(5, 1),
                JanggiPositionFactory.of(5, 0),

                JanggiPositionFactory.of(5, 4),
                JanggiPositionFactory.of(5, 5),
                JanggiPositionFactory.of(5, 6),
                JanggiPositionFactory.of(5, 7),
                JanggiPositionFactory.of(5, 8)
        );

        // when
        List<JanggiPosition> destinations = policy.pickDestinations(team, coordinates, positions);

        // then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }
}
