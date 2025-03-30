package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import board.Board;
import board.Position;

class GuardTest {


    @CsvSource(value = {
            "RED,2", "BLUE,9"
    })
    @ParameterizedTest
    void 사는_같은_팀_기물이_존재하면_움직일_수_없다(Team team, int row) {
        Piece piece = new Guard(team);
        Position guardPosition = new Position(row, 5);
        Board board = new Board(Map.of(
                guardPosition, piece,
                new Position(row, 4), new Soldier(team), new Position(row, 6), new Soldier(team),
                new Position(row - 1, 5), new Soldier(team), new Position(row + 1, 5), new Soldier(team)
        ));

        assertThat(piece.getMovablePositions(guardPosition, board))
                .containsExactlyInAnyOrder(
                        new Position(row - 1, 4), new Position(row - 1, 6),
                        new Position(row + 1, 4), new Position(row + 1, 6)
                );
    }

    @CsvSource(value = {
            "RED,BLUE,2", "BLUE,RED,9"
    })
    @ParameterizedTest
    void 사는_다른_팀_기물이_존재하면_취한_후_멈춘다(Team team, Team enemy, int row) {
        Piece piece = new Guard(team);
        Position guardPosition = new Position(row, 5);
        Board board = new Board(Map.of(
                guardPosition, piece,
                new Position(row, 4), new Soldier(team), new Position(row, 6), new Soldier(team),
                new Position(row + 1, 5), new Soldier(team), new Position(row - 1, 5), new Soldier(team),
                new Position(row - 1, 4), new Soldier(enemy), new Position(row - 1, 6), new Soldier(enemy),
                new Position(row + 1, 4), new Soldier(enemy), new Position(row + 1, 6), new Soldier(enemy)
        ));

        assertThat(piece.getMovablePositions(guardPosition, board))
                .containsExactlyInAnyOrder(
                        new Position(row - 1, 4), new Position(row - 1, 6),
                        new Position(row + 1, 4), new Position(row + 1, 6)
                );
    }

    @MethodSource
    @ParameterizedTest
    void 사는_궁성_영역_바깥으로_움직일_수_없다(Team team, int row, int column, Set<Position> expected) {
        Piece piece = new Guard(team);
        Position guardPosition = new Position(row, column);
        Board board = new Board(Map.of(guardPosition, piece));

        assertThat(piece.getMovablePositions(guardPosition, board))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    private static Stream<Arguments> 사는_궁성_영역_바깥으로_움직일_수_없다() {
        return Stream.of(
                Arguments.of(Team.RED, 1, 4, Set.of(
                        new Position(2, 4), new Position(2, 5), new Position(1, 5)
                )),
                Arguments.of(Team.RED, 1, 6, Set.of(
                        new Position(2, 6), new Position(2, 5), new Position(1, 5)
                )),
                Arguments.of(Team.RED, 3, 4, Set.of(
                        new Position(2, 4), new Position(2, 5), new Position(3, 5)
                )),
                Arguments.of(Team.RED, 3, 6, Set.of(
                        new Position(2, 6), new Position(2, 5), new Position(3, 5)
                )),
                Arguments.of(Team.BLUE, 8, 4, Set.of(
                        new Position(9, 4), new Position(9, 5), new Position(8, 5)
                )),
                Arguments.of(Team.BLUE, 8, 6, Set.of(
                        new Position(9, 6), new Position(9, 5), new Position(8, 5)
                )),
                Arguments.of(Team.BLUE, 10, 4, Set.of(
                        new Position(9, 4), new Position(9, 5), new Position(10, 5)
                )),
                Arguments.of(Team.BLUE, 10, 6, Set.of(
                        new Position(9, 6), new Position(9, 5), new Position(10, 5)
                ))
        );
    }

    @MethodSource
    @ParameterizedTest
    void 사는_궁성_영역_외에_존재할_수_없다(Team team, Position position) {
        Piece piece = new Guard(team);
        Board board = new Board(Map.of(position, piece));

        assertThatThrownBy(() -> piece.getMovablePositions(position, board))
                .isInstanceOf(IllegalStateException.class);
    }

    private static Stream<Arguments> 사는_궁성_영역_외에_존재할_수_없다() {
        return Stream.of(
                Arguments.of(Team.RED, new Position(2, 3)),
                Arguments.of(Team.RED, new Position(2, 7)),
                Arguments.of(Team.RED, new Position(4, 5)),
                Arguments.of(Team.BLUE, new Position(9, 3)),
                Arguments.of(Team.BLUE, new Position(9, 7)),
                Arguments.of(Team.BLUE, new Position(7, 5))
        );
    }

}
