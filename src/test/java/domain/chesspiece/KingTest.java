package domain.chesspiece;

import static org.assertj.core.api.Assertions.assertThat;

import domain.path.Path;
import domain.position.ChessPosition;
import domain.type.ChessTeam;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    @DisplayName("(1, 4) 위치 왕의 이동 경로를 계산한다")
    void test1() {
        //given
        final ChessPosition chessPosition = new ChessPosition(1, 4);
        final List<Path> expected = List.of(
                new Path(List.of(new ChessPosition(0, 3))),
                new Path(List.of(new ChessPosition(0, 4))),
                new Path(List.of(new ChessPosition(0, 5))),
                new Path(List.of(new ChessPosition(1, 3))),
                new Path(List.of(new ChessPosition(1, 5))),
                new Path(List.of(new ChessPosition(2, 3))),
                new Path(List.of(new ChessPosition(2, 4))),
                new Path(List.of(new ChessPosition(2, 5)))
        );

        //when
        final King king = new King(ChessTeam.RED, chessPosition);
        final List<Path> coordinatePaths = king.calculateCoordinatePaths(chessPosition);

        //then
        assertThat(coordinatePaths).containsExactlyInAnyOrderElementsOf(expected);

    }

    @Test
    @DisplayName("(1, 3) 위치 왕의 이동경로를 계산한다")
    void test2() {
        //given
        final ChessPosition chessPosition = new ChessPosition(1, 3);
        final List<Path> expected = List.of(
                new Path(List.of(new ChessPosition(0, 3))),
                new Path(List.of(new ChessPosition(2, 3))),
                new Path(List.of(new ChessPosition(1, 4)))
        );

        //when
        final King king = new King(ChessTeam.RED, chessPosition);
        final List<Path> coordinatePaths = king.calculateCoordinatePaths(chessPosition);

        //then
        assertThat(coordinatePaths).containsExactlyInAnyOrderElementsOf(expected);

    }

}
