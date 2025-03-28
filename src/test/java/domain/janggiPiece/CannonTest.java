package domain.janggiPiece;

import domain.path.Path;
import domain.position.JanggiPosition;
import domain.position.JanggiPositionFactory;
import domain.type.JanggiTeam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CannonTest {
    private final Cannon cannon = new Cannon(JanggiTeam.BLUE);

    @Test
    @DisplayName("궁성 외부에 있을 경우 올바른 경로를 반환할 수 있다.")
    void startAtNormalPosition() {
        //given
        JanggiPosition startPosition = JanggiPositionFactory.of(5, 4);
        final List<Path> expected = List.of(
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

        //when
        final List<Path> paths = cannon.getCoordinatePaths(startPosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("이동 경로 중에서 궁성의 대각선 방향이 존재하는 경우, 이를 무시한다.")
    @Test
    void ignoreCastleDiagonal() {
        // given
        JanggiPosition startPosition = JanggiPositionFactory.of(5, 3);
        final List<Path> expected = List.of(
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
                        JanggiPositionFactory.of(5, 4),
                        JanggiPositionFactory.of(5, 5),
                        JanggiPositionFactory.of(5, 6),
                        JanggiPositionFactory.of(5, 7),
                        JanggiPositionFactory.of(5, 8)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(6, 3),
                        JanggiPositionFactory.of(7, 3),
                        JanggiPositionFactory.of(8, 3),
                        JanggiPositionFactory.of(9, 3)
                ))
        );

        //when
        final List<Path> paths = cannon.getCoordinatePaths(startPosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("시작 지점이 궁성으로 들어가는 모서리인 경우, 궁성의 대각선 방향으로도 움직일 수 있다.")
    @Test
    void startAtCastleInCorner() {
        // given
        JanggiPosition startPosition = JanggiPositionFactory.of(2, 3);
        final List<Path> expected = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(0, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(2, 2),
                        JanggiPositionFactory.of(2, 1),
                        JanggiPositionFactory.of(2, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(2, 4),
                        JanggiPositionFactory.of(2, 5),
                        JanggiPositionFactory.of(2, 6),
                        JanggiPositionFactory.of(2, 7),
                        JanggiPositionFactory.of(2, 8)
                )),
                new Path(List.of(
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
                        JanggiPositionFactory.of(0, 5)
                ))
        );

        //when
        final List<Path> paths = cannon.getCoordinatePaths(startPosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("시작 지점이 궁성에서 나가는 모서리인 경우, 모서리 방향은 궁성 내부까지만 움직일 수 있다")
    @Test
    void startAtCastleOutCorner() {
        // given
        JanggiPosition startPosition = JanggiPositionFactory.of(0, 3);
        final List<Path> expected = List.of(
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

        //when
        final List<Path> paths = cannon.getCoordinatePaths(startPosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("시작 지점이 궁성 중앙인 경우, 8개의 경로를 반환할 수 있다")
    @Test
    void startAtMiddleOfCastle() {
        // given
        JanggiPosition startPosition = JanggiPositionFactory.of(1, 4);
        final List<Path> expected = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(0, 4)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(0, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(0, 5)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(2, 3)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(2, 5)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 3),
                        JanggiPositionFactory.of(1, 2),
                        JanggiPositionFactory.of(1, 1),
                        JanggiPositionFactory.of(1, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(1, 5),
                        JanggiPositionFactory.of(1, 6),
                        JanggiPositionFactory.of(1, 7),
                        JanggiPositionFactory.of(1, 8)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(2, 4),
                        JanggiPositionFactory.of(3, 4),
                        JanggiPositionFactory.of(4, 4),
                        JanggiPositionFactory.of(5, 4),
                        JanggiPositionFactory.of(6, 4),
                        JanggiPositionFactory.of(7, 4),
                        JanggiPositionFactory.of(8, 4),
                        JanggiPositionFactory.of(9, 4)
                ))
        );

        //when
        final List<Path> paths = cannon.getCoordinatePaths(startPosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("시작 지점이 궁성이면서 궁성의 모서리가 아닌 경우, 네 가지 방향의 경로를 반환할 수 있다")
    @Test
    void startAtCastleNotCorner() {
        // given
        JanggiPosition startPosition = JanggiPositionFactory.of(7, 4);
        final List<Path> expected = List.of(
                new Path(List.of(
                        JanggiPositionFactory.of(7, 3),
                        JanggiPositionFactory.of(7, 2),
                        JanggiPositionFactory.of(7, 1),
                        JanggiPositionFactory.of(7, 0)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(7, 5),
                        JanggiPositionFactory.of(7, 6),
                        JanggiPositionFactory.of(7, 7),
                        JanggiPositionFactory.of(7, 8)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(6, 4),
                        JanggiPositionFactory.of(5, 4),
                        JanggiPositionFactory.of(4, 4),
                        JanggiPositionFactory.of(3, 4),
                        JanggiPositionFactory.of(2, 4),
                        JanggiPositionFactory.of(1, 4),
                        JanggiPositionFactory.of(0, 4)
                )),
                new Path(List.of(
                        JanggiPositionFactory.of(8, 4),
                        JanggiPositionFactory.of(9, 4)
                ))
        );

        //when
        final List<Path> paths = cannon.getCoordinatePaths(startPosition);

        //then
        assertThat(paths).containsExactlyInAnyOrderElementsOf(expected);
    }
}
