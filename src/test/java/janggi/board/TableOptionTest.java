package janggi.board;

import janggi.piece.DefaultPiece;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.Team;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TableOptionTest {
    @ParameterizedTest
    @MethodSource("setTablePositions")
    @DisplayName("상차림 옵션에 해당하는 상,마 생성 확인")
    void tableOptionTest(Team team, TableOption tableOption, Map<Position, Piece> expectedPieces) {
        //given & when
        Map<Position, Piece> pieces = team.generateTableSetPieces(tableOption);
        //then
        assertThat(pieces).containsAllEntriesOf(expectedPieces);
    }

    static Stream<Arguments> setTablePositions() {
        return Stream.of(
                Arguments.arguments(
                        Team.HAN,
                        TableOption.HEHE,
                        Map.of(
                             new Position(1,8), new DefaultPiece(Team.HAN, PieceType.ELEPHANT),
                             new Position(1,3), new DefaultPiece(Team.HAN, PieceType.ELEPHANT),
                             new Position(1,7), new DefaultPiece(Team.HAN, PieceType.HORSE),
                             new Position(1,2), new DefaultPiece(Team.HAN, PieceType.HORSE)
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.EHEH,
                        Map.of(
                                new Position(1, 7), new DefaultPiece(Team.HAN, PieceType.ELEPHANT),
                                new Position(1, 2), new DefaultPiece(Team.HAN, PieceType.ELEPHANT),
                                new Position(1, 8), new DefaultPiece(Team.HAN, PieceType.HORSE),
                                new Position(1, 3), new DefaultPiece(Team.HAN, PieceType.HORSE)
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.EHHE,
                        Map.of(
                                new Position(1, 8), new DefaultPiece(Team.HAN, PieceType.ELEPHANT),
                                new Position(1, 2), new DefaultPiece(Team.HAN, PieceType.ELEPHANT),
                                new Position(1, 7), new DefaultPiece(Team.HAN, PieceType.HORSE),
                                new Position(1, 3), new DefaultPiece(Team.HAN, PieceType.HORSE)
                        )
                ),
                Arguments.arguments(
                        Team.HAN,
                        TableOption.HEEH,
                        Map.of(
                                new Position(1, 7), new DefaultPiece(Team.HAN, PieceType.ELEPHANT),
                                new Position(1, 3), new DefaultPiece(Team.HAN, PieceType.ELEPHANT),
                                new Position(1, 8), new DefaultPiece(Team.HAN, PieceType.HORSE),
                                new Position(1, 2), new DefaultPiece(Team.HAN, PieceType.HORSE)
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.HEHE,
                        Map.of(
                                new Position(10, 8), new DefaultPiece(Team.CHO, PieceType.ELEPHANT),
                                new Position(10, 3), new DefaultPiece(Team.CHO, PieceType.ELEPHANT),
                                new Position(10, 7), new DefaultPiece(Team.CHO, PieceType.HORSE),
                                new Position(10, 2), new DefaultPiece(Team.CHO, PieceType.HORSE)
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.EHEH,
                        Map.of(
                                new Position(10, 7), new DefaultPiece(Team.CHO, PieceType.ELEPHANT),
                                new Position(10, 2), new DefaultPiece(Team.CHO, PieceType.ELEPHANT),
                                new Position(10, 8), new DefaultPiece(Team.CHO, PieceType.HORSE),
                                new Position(10, 3), new DefaultPiece(Team.CHO, PieceType.HORSE)
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.EHHE,
                        Map.of(
                                new Position(10, 8), new DefaultPiece(Team.CHO, PieceType.ELEPHANT),
                                new Position(10, 2), new DefaultPiece(Team.CHO, PieceType.ELEPHANT),
                                new Position(10, 7), new DefaultPiece(Team.CHO, PieceType.HORSE),
                                new Position(10, 3), new DefaultPiece(Team.CHO, PieceType.HORSE)
                        )
                ),
                Arguments.arguments(
                        Team.CHO,
                        TableOption.HEEH,
                        Map.of(
                                new Position(10, 7), new DefaultPiece(Team.CHO, PieceType.ELEPHANT),
                                new Position(10, 3), new DefaultPiece(Team.CHO, PieceType.ELEPHANT),
                                new Position(10, 8), new DefaultPiece(Team.CHO, PieceType.HORSE),
                                new Position(10, 2), new DefaultPiece(Team.CHO, PieceType.HORSE)
                        )
                )
        );
    }
}
