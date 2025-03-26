package janggi.piece;

import janggi.board.TableOption;
import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class PieceGeneratorTest {
    @ParameterizedTest
    @MethodSource("makeInitialPieceTestData")
    @DisplayName("초기 기물이 올바르게 생성되는지 확인")
    void pieceGeneratorTest(List<Piece> hanPieces) {
        //given & when
        List<Piece> pieces = new PieceGenerator().generateInitialPieces(TableOption.HEEH, TableOption.EHHE);
        //then
        Assertions.assertThat(pieces).containsAll(hanPieces);
    }

    static Stream<Arguments> makeInitialPieceTestData() {
        return Stream.of(
                Arguments.arguments(
                        // 한 - 마상상마
                        List.of(
                                new Chariot(Team.HAN, new Position(1, 1)), new Chariot(Team.HAN, new Position(1,  9)),
                                new Cannon(Team.HAN, new Position(3, 2)), new Cannon(Team.HAN, new Position(3,  8)),
                                new Elephant(Team.HAN, new Position(1, 3)), new Elephant(Team.HAN, new Position(1,  7)),
                                new Horse(Team.HAN, new Position(1, 2)), new Horse(Team.HAN, new Position(1,  8)),
                                new Soldier(Team.HAN, new Position(4, 1)),
                                new Soldier(Team.HAN, new Position(4, 3)),
                                new Soldier(Team.HAN, new Position(4, 5)),
                                new Soldier(Team.HAN, new Position(4, 7)),
                                new Soldier(Team.HAN, new Position(4, 9)),
                                new Guard(Team.HAN, new Position(1, 4)), new Guard(Team.HAN, new Position(1, 6)),
                                new King(Team.HAN, new Position(2, 5))
                        )
                ),
                Arguments.arguments(
                        // 초 - 상마마상
                        List.of(
                                new Chariot(Team.CHO, new Position(10, 1)), new Chariot(Team.CHO, new Position(10, 9)),
                                new Cannon(Team.CHO, new Position(8, 2)), new Cannon(Team.CHO, new Position(8, 8)),
                                new Elephant(Team.CHO, new Position(10, 2)), new Elephant(Team.CHO, new Position(10, 8)),
                                new Horse(Team.CHO, new Position(10, 3)), new Horse(Team.CHO, new Position(10, 7)),
                                new Soldier(Team.CHO, new Position(7, 1)),
                                new Soldier(Team.CHO, new Position(7, 3)),
                                new Soldier(Team.CHO, new Position(7, 5)),
                                new Soldier(Team.CHO, new Position(7, 7)),
                                new Soldier(Team.CHO, new Position(7, 9)),
                                new Guard(Team.CHO, new Position(10, 4)), new Guard(Team.CHO, new Position(10, 6)),
                                new King(Team.CHO, new Position(9, 5))
                        )
                )
        );
    }
}
