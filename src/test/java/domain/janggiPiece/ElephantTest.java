package domain.janggiPiece;

import domain.path.JanggiPath;
import domain.path.Path;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ElephantTest {
    private final Elephant elephant = new Elephant(JanggiTeam.BLUE);

    @Test
    @DisplayName("상의 이동 경로를 반환한다")
    void normalPath() {
        //given
        JanggiPosition horsePosition = JanggiPosition.of(4, 4);
        final List<JanggiPath> expected = List.of(
                new JanggiPath(List.of(
                        JanggiPosition.of(3, 4),
                        JanggiPosition.of(2, 3),
                        JanggiPosition.of(1, 2)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(3, 4),
                        JanggiPosition.of(2, 5),
                        JanggiPosition.of(1, 6)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(4, 3),
                        JanggiPosition.of(3, 2),
                        JanggiPosition.of(2, 1)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(4, 3),
                        JanggiPosition.of(5, 2),
                        JanggiPosition.of(6, 1)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(5, 4),
                        JanggiPosition.of(6, 3),
                        JanggiPosition.of(7, 2)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(5, 4),
                        JanggiPosition.of(6, 5),
                        JanggiPosition.of(7, 6)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(4, 5),
                        JanggiPosition.of(3, 6),
                        JanggiPosition.of(2, 7)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(4, 5),
                        JanggiPosition.of(5, 6),
                        JanggiPosition.of(6, 7)
                ))
        );

        //when
        final List<JanggiPath> paths = elephant.getCoordinatePaths(horsePosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("출발 지점이 궁성인 경우, 궁성 길을 무시하고 이동할 수 있다.")
    void ignoreCastleRoad() {
        //given
        JanggiPosition horsePosition = JanggiPosition.of(2, 3);
        final List<JanggiPath> expected = List.of(
                new JanggiPath(List.of(
                        JanggiPosition.of(2, 4),
                        JanggiPosition.of(1, 5),
                        JanggiPosition.of(0, 6)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(2, 4),
                        JanggiPosition.of(3, 5),
                        JanggiPosition.of(4, 6)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(2, 2),
                        JanggiPosition.of(1, 1),
                        JanggiPosition.of(0, 0)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(2, 2),
                        JanggiPosition.of(3, 1),
                        JanggiPosition.of(4, 0)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(3, 3),
                        JanggiPosition.of(4, 2),
                        JanggiPosition.of(5, 1)
                )),
                new JanggiPath(List.of(
                        JanggiPosition.of(3, 3),
                        JanggiPosition.of(4, 4),
                        JanggiPosition.of(5, 5)
                ))
        );

        //when
        final List<JanggiPath> paths = elephant.getCoordinatePaths(horsePosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }
}
