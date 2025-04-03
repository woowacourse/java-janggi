package domain.hurdlePolicy;

import domain.janggiPiece.Elephant;
import domain.janggiPiece.Horse;
import domain.janggiPiece.Pawn;
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

class UnpassableHurdlePolicyTest {
    private final HurdlePolicy policy = new UnpassableHurdlePolicy();
    private final JanggiPosition startPosition = JanggiPositionFactory.of(3, 4);
    private final JanggiTeam team = JanggiTeam.BLUE;

    @DisplayName("경로상에 다른 기물이 존재하는 경우, 움직일 수 없다.")
    @Test
    void existOtherPieceInPath() {
        // given
        final List<Path> coordinates = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(3, 4),
                        JanggiPositionFactory.of(2, 3),
                        JanggiPositionFactory.of(1, 2)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(3, 4),
                        JanggiPositionFactory.of(2, 5),
                        JanggiPositionFactory.of(1, 6)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(3, 2),
                        JanggiPositionFactory.of(2, 1)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(5, 2),
                        JanggiPositionFactory.of(6, 1)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 4),
                        JanggiPositionFactory.of(6, 3),
                        JanggiPositionFactory.of(7, 2)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 4),
                        JanggiPositionFactory.of(6, 5),
                        JanggiPositionFactory.of(7, 6)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(4, 5),
                        JanggiPositionFactory.of(3, 6),
                        JanggiPositionFactory.of(2, 7)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(4, 5),
                        JanggiPositionFactory.of(5, 6),
                        JanggiPositionFactory.of(6, 7)
                ))
        );
        List<JanggiPosition> expected = List.of(
                JanggiPositionFactory.of(2, 1),
                JanggiPositionFactory.of(6, 1),
                JanggiPositionFactory.of(7, 2),
                JanggiPositionFactory.of(7, 6),
                JanggiPositionFactory.of(2, 7),
                JanggiPositionFactory.of(6, 7)
        );
        JanggiPositions positions = new JanggiPositions(Map.of(
                startPosition, new Elephant(team),
                JanggiPositionFactory.of(2, 3), new Elephant(JanggiTeam.BLUE),
                JanggiPositionFactory.of(2, 5), new Pawn(JanggiTeam.RED)
        ));

        // when
        List<JanggiPosition> destinations = policy.pickDestinations(team, coordinates, positions);

        // then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("목적지에 적군의 기물이 존재하는 경우, 해당 위치까지 움직일 수 있다.")
    @Test
    void existSameTeamPiece() {
        // given
        List<Path> coordinates = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(3, 4),
                        JanggiPositionFactory.of(2, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(3, 4),
                        JanggiPositionFactory.of(2, 5)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(3, 2)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(5, 2)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 4),
                        JanggiPositionFactory.of(6, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 4),
                        JanggiPositionFactory.of(6, 5)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(4, 5),
                        JanggiPositionFactory.of(3, 6)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(4, 5),
                        JanggiPositionFactory.of(5, 6)
                ))
        );
        List<JanggiPosition> expected = List.of(
                JanggiPositionFactory.of(2, 3),
                JanggiPositionFactory.of(2, 5),
                JanggiPositionFactory.of(3, 2),
                JanggiPositionFactory.of(5, 2),
                JanggiPositionFactory.of(6, 3),
                JanggiPositionFactory.of(6, 5),
                JanggiPositionFactory.of(3, 6),
                JanggiPositionFactory.of(5, 6)
        );
        JanggiPositions positions = new JanggiPositions(Map.of(
                JanggiPositionFactory.of(4, 4), new Horse(team),
                JanggiPositionFactory.of(2, 3), new Elephant(JanggiTeam.RED),
                JanggiPositionFactory.of(3, 2), new Pawn(JanggiTeam.RED),
                JanggiPositionFactory.of(6, 5), new Pawn(JanggiTeam.RED)
        ));

        // when
        List<JanggiPosition> destinations = policy.pickDestinations(team, coordinates, positions);

        // then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }
}
