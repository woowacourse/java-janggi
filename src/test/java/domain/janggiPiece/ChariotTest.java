package domain.janggiPiece;

import domain.path.Path;
import domain.position.JanggiPosition;
import domain.type.JanggiTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ChariotTest {
    private final Chariot chariot = new Chariot(JanggiTeam.BLUE);

    @Test
    @DisplayName("차의 이동 경로에 궁성이 포함되더라도, 기존의 이동 경로를 유지한다")
    void test1() {
        //given
        JanggiPosition chariotPosition = JanggiPosition.of(5, 3);
        final List<Path> expected = List.of(
                new Path(List.of(
                        JanggiPosition.of(4, 3),
                        JanggiPosition.of(3, 3),
                        JanggiPosition.of(2, 3),
                        JanggiPosition.of(1, 3),
                        JanggiPosition.of(0, 3)
                )),
                new Path(List.of(
                        JanggiPosition.of(5, 2),
                        JanggiPosition.of(5, 1),
                        JanggiPosition.of(5, 0)
                )),
                new Path(List.of(
                        JanggiPosition.of(6, 3),
                        JanggiPosition.of(7, 3),
                        JanggiPosition.of(8, 3),
                        JanggiPosition.of(9, 3)
                )),
                new Path(List.of(
                        JanggiPosition.of(5, 4),
                        JanggiPosition.of(5, 5),
                        JanggiPosition.of(5, 6),
                        JanggiPosition.of(5, 7),
                        JanggiPosition.of(5, 8)
                ))
        );

        //when
        final List<Path> destinations = chariot.getCoordinatePaths(chariotPosition);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("궁성에서 출발하는 경우, 대각선 방향은 궁성 내에서만 가능하며 상하좌우는 끝까지 가능하다")
    void test2() {
        //given
        JanggiPosition chariotPosition = JanggiPosition.of(0, 3);
        final List<Path> expected = List.of(
                new Path(List.of(
                        JanggiPosition.of(1, 4),
                        JanggiPosition.of(2, 5)
                )),
                new Path(List.of(
                        JanggiPosition.of(1, 3),
                        JanggiPosition.of(2, 3),
                        JanggiPosition.of(3, 3),
                        JanggiPosition.of(4, 3),
                        JanggiPosition.of(5, 3),
                        JanggiPosition.of(6, 3),
                        JanggiPosition.of(7, 3),
                        JanggiPosition.of(8, 3),
                        JanggiPosition.of(9, 3)
                )),
                new Path(List.of(
                        JanggiPosition.of(0, 2),
                        JanggiPosition.of(0, 1),
                        JanggiPosition.of(0, 0)
                )),
                new Path(List.of(
                        JanggiPosition.of(0, 4),
                        JanggiPosition.of(0, 5),
                        JanggiPosition.of(0, 6),
                        JanggiPosition.of(0, 7),
                        JanggiPosition.of(0, 8)
                ))
        );

        //when
        final List<Path> destinations = chariot.getCoordinatePaths(chariotPosition);

        //then
        assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
    }
}
