package janggi.board;

import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.position.Position;
import janggi.team.Team;

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
        //given & when
        List<Piece> pieces = team.generateTableSetPieces(tableOption);
        //then
        assertThat(pieces).containsAll(expectedPieces);
    }

    static Stream<Arguments> setTablePositions() {
        return Stream.of(
                Arguments.arguments(
                        Team.HAN,
                        TableOption.HEHE,
                        List.of(
                                new Elephant(Team.HAN, new Position(1,8)),
                                new Elephant(Team.HAN, new Position(1,3)),
                                new Horse(Team.HAN, new Position(1,7)),
                                new Horse(Team.HAN, new Position(1,2))
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.EHEH,
                        List.of(
                                new Elephant(Team.HAN, new Position(1, 7)),
                                new Elephant(Team.HAN, new Position(1, 2)),
                                new Horse(Team.HAN, new Position(1, 8)),
                                new Horse(Team.HAN, new Position(1, 3))
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.EHHE,
                        List.of(
                                new Elephant(Team.HAN, new Position(1, 8)),
                                new Elephant(Team.HAN, new Position(1, 2)),
                                new Horse(Team.HAN, new Position(1, 7)),
                                new Horse(Team.HAN, new Position(1, 3))
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.HEEH,
                        List.of(
                                new Elephant(Team.HAN, new Position(1, 7)),
                                new Elephant(Team.HAN, new Position(1, 3)),
                                new Horse(Team.HAN, new Position(1, 8)),
                                new Horse(Team.HAN, new Position(1, 2))
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.HEHE,
                        List.of(
                                new Elephant(Team.CHO, new Position(10, 8)),
                                new Elephant(Team.CHO, new Position(10, 3)),
                                new Horse(Team.CHO, new Position(10, 7)),
                                new Horse(Team.CHO, new Position(10, 2))
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.EHEH,
                        List.of(
                                new Elephant(Team.CHO, new Position(10, 7)),
                                new Elephant(Team.CHO, new Position(10, 2)),
                                new Horse(Team.CHO, new Position(10, 8)),
                                new Horse(Team.CHO, new Position(10, 3))
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.EHHE,
                        List.of(
                                new Elephant(Team.CHO, new Position(10, 8)),
                                new Elephant(Team.CHO, new Position(10, 2)),
                                new Horse(Team.CHO, new Position(10, 7)),
                                new Horse(Team.CHO, new Position(10, 3))
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.HEEH,
                        List.of(
                                new Elephant(Team.CHO, new Position(10, 7)),
                                new Elephant(Team.CHO, new Position(10, 3)),
                                new Horse(Team.CHO, new Position(10, 8)),
                                new Horse(Team.CHO, new Position(10, 2))
                        )
                )
        );
    }
}
