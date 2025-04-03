package domain.hurdlePolicy;

import domain.janggiPiece.Cannon;
import domain.janggiPiece.Chariot;
import domain.janggiPiece.Elephant;
import domain.janggiPiece.King;
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

class CannonHurdlePolicyTest {
    private final CannonHurdlePolicy policy = new CannonHurdlePolicy();
    private final JanggiPosition startPosition = JanggiPositionFactory.of(0, 3);

    @DisplayName("다른 기물이 존재하지 않는 경우, 포는 움직일 수 없다.")
    @Test
    void notExistOtherPieces() {
        // given
        final List<Path> coordinates = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(4, 4),
                        JanggiPositionFactory.of(3, 4),
                        JanggiPositionFactory.of(2, 4),
                        JanggiPositionFactory.of(1, 4),
                        JanggiPositionFactory.of(0, 4)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 3),
                        JanggiPositionFactory.of(5, 2),
                        JanggiPositionFactory.of(5, 1),
                        JanggiPositionFactory.of(5, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(5, 5),
                        JanggiPositionFactory.of(5, 6),
                        JanggiPositionFactory.of(5, 7),
                        JanggiPositionFactory.of(5, 8)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(6, 4),
                        JanggiPositionFactory.of(7, 4),
                        JanggiPositionFactory.of(8, 4),
                        JanggiPositionFactory.of(9, 4)
                ))
        );
        JanggiPositions positions = new JanggiPositions(Map.of());

        // when
        List<JanggiPosition> destinations = policy.pickDestinations(JanggiTeam.BLUE, coordinates, positions);

        // then
        assertThat(destinations).isEmpty();
    }

    @DisplayName("포는 포를 뛰어넘을 수 없다.")
    @Test
    void existOtherCannonPieces() {
        // given
        final List<Path> coordinates = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(0, 2),
                        JanggiPositionFactory.of(0, 1),
                        JanggiPositionFactory.of(0, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(0, 4),
                        JanggiPositionFactory.of(0, 5),
                        JanggiPositionFactory.of(0, 6),
                        JanggiPositionFactory.of(0, 7),
                        JanggiPositionFactory.of(0, 8)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(2, 3),
                        JanggiPositionFactory.of(3, 3),
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(5, 3),
                        JanggiPositionFactory.of(6, 3),
                        JanggiPositionFactory.of(7, 3),
                        JanggiPositionFactory.of(8, 3),
                        JanggiPositionFactory.of(9, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 4),
                        JanggiPositionFactory.of(2, 5)
                ))
        );
        JanggiPositions positions = new JanggiPositions(Map.of(
                startPosition, new Cannon(JanggiTeam.BLUE),
                JanggiPositionFactory.of(0, 2), new Cannon(JanggiTeam.RED)
        ));

        // when
        List<JanggiPosition> destinations = policy.pickDestinations(JanggiTeam.BLUE, coordinates, positions);

        // then
        assertThat(destinations).isEmpty();
    }

    @DisplayName("궁성에서 시작할 때, 대각선 방향으로 장애물이 있는 경우 이를 넘을 수 있다.")
    @Test
    void existOtherInCastleDiagonal() {
        // given
        final List<Path> coordinates = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(0, 2),
                        JanggiPositionFactory.of(0, 1),
                        JanggiPositionFactory.of(0, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(0, 4),
                        JanggiPositionFactory.of(0, 5),
                        JanggiPositionFactory.of(0, 6),
                        JanggiPositionFactory.of(0, 7),
                        JanggiPositionFactory.of(0, 8)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(2, 3),
                        JanggiPositionFactory.of(3, 3),
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(5, 3),
                        JanggiPositionFactory.of(6, 3),
                        JanggiPositionFactory.of(7, 3),
                        JanggiPositionFactory.of(8, 3),
                        JanggiPositionFactory.of(9, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 4),
                        JanggiPositionFactory.of(2, 5)
                ))
        );
        JanggiPositions positions = new JanggiPositions(Map.of(
                startPosition, new Cannon(JanggiTeam.BLUE),
                JanggiPositionFactory.of(1, 4), new King(JanggiTeam.RED)
        ));
        final List<JanggiPosition> expected = List.of(
                JanggiPositionFactory.of(2, 5)
        );

        // when
        List<JanggiPosition> destinations = policy.pickDestinations(JanggiTeam.BLUE, coordinates, positions);

        // then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("다른 기물이 존재하는 경우, 다른 팀의 기물을 만날 때까지 이동할 수 있다.")
    @Test
    void existOtherPiecesUntilMeetEnemy() {
        // given
        final List<Path> coordinates = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(0, 2),
                        JanggiPositionFactory.of(0, 1),
                        JanggiPositionFactory.of(0, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(0, 4),
                        JanggiPositionFactory.of(0, 5),
                        JanggiPositionFactory.of(0, 6),
                        JanggiPositionFactory.of(0, 7),
                        JanggiPositionFactory.of(0, 8)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(2, 3),
                        JanggiPositionFactory.of(3, 3),
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(5, 3),
                        JanggiPositionFactory.of(6, 3),
                        JanggiPositionFactory.of(7, 3),
                        JanggiPositionFactory.of(8, 3),
                        JanggiPositionFactory.of(9, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 4),
                        JanggiPositionFactory.of(2, 5)
                ))
        );
        JanggiPositions positions = new JanggiPositions(Map.of(
                startPosition, new Cannon(JanggiTeam.BLUE),
                JanggiPositionFactory.of(5, 3), new Chariot(JanggiTeam.BLUE),
                JanggiPositionFactory.of(8, 3), new Elephant(JanggiTeam.RED)
        ));
        final List<JanggiPosition> expected = List.of(
                JanggiPositionFactory.of(6, 3),
                JanggiPositionFactory.of(7, 3),
                JanggiPositionFactory.of(8, 3)
        );

        // when
        List<JanggiPosition> destinations = policy.pickDestinations(JanggiTeam.BLUE, coordinates, positions);

        // then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("다른 기물이 존재하는 경우, 같은 팀의 기물을 만나기 전 위치까지 이동할 수 있다.")
    @Test
    void existOtherPieces() {
        // given
        final List<Path> coordinates = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(0, 2),
                        JanggiPositionFactory.of(0, 1),
                        JanggiPositionFactory.of(0, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(0, 4),
                        JanggiPositionFactory.of(0, 5),
                        JanggiPositionFactory.of(0, 6),
                        JanggiPositionFactory.of(0, 7),
                        JanggiPositionFactory.of(0, 8)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(2, 3),
                        JanggiPositionFactory.of(3, 3),
                        JanggiPositionFactory.of(4, 3),
                        JanggiPositionFactory.of(5, 3),
                        JanggiPositionFactory.of(6, 3),
                        JanggiPositionFactory.of(7, 3),
                        JanggiPositionFactory.of(8, 3),
                        JanggiPositionFactory.of(9, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 4),
                        JanggiPositionFactory.of(2, 5)
                ))
        );
        JanggiPositions positions = new JanggiPositions(Map.of(
                startPosition, new Cannon(JanggiTeam.BLUE),
                JanggiPositionFactory.of(5, 3), new Chariot(JanggiTeam.BLUE),
                JanggiPositionFactory.of(8, 3), new Elephant(JanggiTeam.BLUE)
        ));
        final List<JanggiPosition> expected = List.of(
                JanggiPositionFactory.of(6, 3),
                JanggiPositionFactory.of(7, 3)
        );

        // when
        List<JanggiPosition> destinations = policy.pickDestinations(JanggiTeam.BLUE, coordinates, positions);

        // then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }
}
