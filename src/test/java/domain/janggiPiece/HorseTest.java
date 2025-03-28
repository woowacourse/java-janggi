package domain.janggiPiece;

import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HorseTest {
    private final Horse horse = new Horse(JanggiTeam.BLUE);

    @Test
    @DisplayName("마의 이동 경로를 반환한다")
    void test1() {
        //given
        JanggiPosition horsePosition = JanggiPositionFactory.of(4, 4);
        final List<Path> expected = List.of(
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

        //when
        final List<Path> paths = horse.getCoordinatePaths(horsePosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("출발 지점이 궁성인 경우, 궁성 길을 무시하고 이동할 수 있다.")
    void test2() {
        //given
        JanggiPosition horsePosition = JanggiPositionFactory.of(2, 3);
        final List<Path> expected = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(0, 2)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(0, 4)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(2, 4),
                        JanggiPositionFactory.of(1, 5)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(2, 4),
                        JanggiPositionFactory.of(3, 5)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(2, 2),
                        JanggiPositionFactory.of(1, 1)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(2, 2),
                        JanggiPositionFactory.of(3, 1)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(3, 3),
                        JanggiPositionFactory.of(4, 2)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(3, 3),
                        JanggiPositionFactory.of(4, 4)
                ))
        );

        //when
        final List<Path> paths = horse.getCoordinatePaths(horsePosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }
}
