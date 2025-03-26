package janggi.team;

import janggi.board.TableOption;
import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class TeamTest {
    @ParameterizedTest
    @MethodSource("makeTableSetPiecesTestData")
    @DisplayName("팀 별 상차림에 따른 기물 세팅 생성")
    void generateTableSetPiecesTest(Team team, TableOption tableOption, List<Piece> expected) {
        //given & when
        List<Piece> pieces = team.generateTableSetPieces(tableOption);

        //then
        Assertions.assertThat(pieces).containsAll(expected);
    }

    static Stream<Arguments> makeTableSetPiecesTestData() {
        return Stream.of(
                Arguments.arguments(
                        Team.CHO,
                        TableOption.EHHE,
                        List.of(
                                new Elephant(Team.CHO, new Position(new Row(10), new Column(2))),
                                new Horse(Team.CHO, new Position(new Row(10), new Column(3))),
                                new Horse(Team.CHO, new Position(new Row(10), new Column(7))),
                                new Elephant(Team.CHO, new Position(new Row(10), new Column(8)))
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.EHEH,
                        List.of(
                                new Elephant(Team.CHO, new Position(new Row(10), new Column(2))),
                                new Horse(Team.CHO, new Position(new Row(10), new Column(3))),
                                new Elephant(Team.CHO, new Position(new Row(10), new Column(7))),
                                new Horse(Team.CHO, new Position(new Row(10), new Column(8)))
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.HEEH,
                        List.of(
                                new Horse(Team.CHO, new Position(new Row(10), new Column(2))),
                                new Elephant(Team.CHO, new Position(new Row(10), new Column(3))),
                                new Elephant(Team.CHO, new Position(new Row(10), new Column(7))),
                                new Horse(Team.CHO, new Position(new Row(10), new Column(8)))
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.HEHE,
                        List.of(
                                new Horse(Team.CHO, new Position(new Row(10), new Column(2))),
                                new Elephant(Team.CHO, new Position(new Row(10), new Column(3))),
                                new Horse(Team.CHO, new Position(new Row(10), new Column(7))),
                                new Elephant(Team.CHO, new Position(new Row(10), new Column(8))
                                )
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.EHHE,
                        List.of(
                                new Elephant(Team.HAN, new Position(new Row(1), new Column(2))),
                                new Horse(Team.HAN, new Position(new Row(1), new Column(3))),
                                new Horse(Team.HAN, new Position(new Row(1), new Column(7))),
                                new Elephant(Team.HAN, new Position(new Row(1), new Column(8)))
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.EHEH,
                        List.of(
                                new Elephant(Team.HAN, new Position(new Row(1), new Column(2))),
                                new Horse(Team.HAN, new Position(new Row(1), new Column(3))),
                                new Elephant(Team.HAN, new Position(new Row(1), new Column(7))),
                                new Horse(Team.HAN, new Position(new Row(1), new Column(8)))
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.HEEH,
                        List.of(
                                new Horse(Team.HAN, new Position(new Row(1), new Column(2))),
                                new Elephant(Team.HAN, new Position(new Row(1), new Column(3))),
                                new Elephant(Team.HAN, new Position(new Row(1), new Column(7))),
                                new Horse(Team.HAN, new Position(new Row(1), new Column(8)))
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.HEHE,
                        List.of(
                                new Horse(Team.HAN, new Position(new Row(1), new Column(2))),
                                new Elephant(Team.HAN, new Position(new Row(1), new Column(3))),
                                new Horse(Team.HAN, new Position(new Row(1), new Column(7))),
                                new Elephant(Team.HAN, new Position(new Row(1), new Column(8)))
                        )
                )
        );
    };
}
