package janggi.board;

import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.team.Team;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TableOptionTest {
    @ParameterizedTest
    @MethodSource("setTablePositions")
    @DisplayName("상차림 옵션에 해당하는 상,마 생성 확인")
    void tableOptionTest(Team team, TableOption tableOption, List<Piece> expectedPieces) {
        //given
        //when
        List<Piece> pieces = team.locatePiece(tableOption);
        //then
        for (Piece piece : pieces) {
            assertThat(expectedPieces).contains(piece);
        }
    }

    static Stream<Arguments> setTablePositions() {
        return Stream.of(
                Arguments.arguments(
                        Team.HAN,
                        TableOption.HEHE,
                        List.of(
                                new Elephant(new Position(new Row(10), new Column(8))),
                                new Elephant(new Position(new Row(10), new Column(3))),
                                new Horse(new Position(new Row(10), new Column(7))),
                                new Horse(new Position(new Row(10), new Column(2)))
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.EHEH,
                        List.of(
                                new Elephant(new Position(new Row(10), new Column(7))),
                                new Elephant(new Position(new Row(10), new Column(2))),
                                new Horse(new Position(new Row(10), new Column(8))),
                                new Horse(new Position(new Row(10), new Column(3)))
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.EHHE,
                        List.of(
                                new Elephant(new Position(new Row(10), new Column(8))),
                                new Elephant(new Position(new Row(10), new Column(2))),
                                new Horse(new Position(new Row(10), new Column(7))),
                                new Horse(new Position(new Row(10), new Column(3)))
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.HEEH,
                        List.of(
                                new Elephant(new Position(new Row(10), new Column(8))),
                                new Elephant(new Position(new Row(10), new Column(2))),
                                new Horse(new Position(new Row(10), new Column(7))),
                                new Horse(new Position(new Row(10), new Column(3)))
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.HEHE,
                        List.of(
                                new Elephant(new Position(new Row(1), new Column(8))),
                                new Elephant(new Position(new Row(1), new Column(3))),
                                new Horse(new Position(new Row(1), new Column(7))),
                                new Horse(new Position(new Row(1), new Column(2)))
                                )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.EHEH,
                        List.of(
                                new Elephant(new Position(new Row(1), new Column(7))),
                                new Elephant(new Position(new Row(1), new Column(2))),
                                new Horse(new Position(new Row(1), new Column(8))),
                                new Horse(new Position(new Row(1), new Column(3)))
                                )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.EHHE,
                        List.of(
                                new Elephant(new Position(new Row(1), new Column(8))),
                                new Elephant(new Position(new Row(1), new Column(2))),
                                new Horse(new Position(new Row(1), new Column(7))),
                                new Horse(new Position(new Row(1), new Column(3)))
                                )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.HEEH,
                        List.of(
                                new Elephant(new Position(new Row(1), new Column(8))),
                                new Elephant(new Position(new Row(1), new Column(2))),
                                new Horse(new Position(new Row(1), new Column(7))),
                                new Horse(new Position(new Row(1), new Column(3)))
                                )
                )
        );
    }
}
