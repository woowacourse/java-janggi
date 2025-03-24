package janggi.piece;

import janggi.board.TableOption;
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

import static org.junit.jupiter.api.Assertions.assertAll;

public class PieceGeneratorTest {
    @ParameterizedTest
    @MethodSource("makeInitialPieceTestData")
    @DisplayName("초기 기물이 올바르게 생성되는지 확인")
    void pieceGeneratorTest(List<Piece> hanPieces, List<Piece> choPieces) {
        //given

        //when
        List<Piece> pieces = new PieceGenerator().generateInitialPieces( TableOption.HEEH, TableOption.EHHE);
        //then
        assertAll(
                () -> Assertions.assertThat(pieces).containsAll(hanPieces),
                () -> Assertions.assertThat(pieces).containsAll(choPieces)
        );
    }

    static Stream<Arguments> makeInitialPieceTestData() {
        return Stream.of(
                Arguments.arguments(
                        // 한 - 마상상마
                        List.of(
                                new Chariot(Team.HAN, new Position(new Row(1), new Column(1))), new Chariot(Team.HAN, new Position(new Row(1), new Column(9))),
                                new Cannon(Team.HAN, new Position(new Row(3), new Column(2))), new Cannon(Team.HAN, new Position(new Row(3), new Column(8))),
                                new Elephant(Team.HAN, new Position(new Row(1), new Column(3))), new Elephant(Team.HAN, new Position(new Row(1), new Column(7))),
                                new Horse(Team.HAN, new Position(new Row(1), new Column(2))), new Horse(Team.HAN, new Position(new Row(1), new Column(8))),
                                new Soldier(Team.HAN, new Position(new Row(4), new Column(1))),
                                new Soldier(Team.HAN, new Position(new Row(4), new Column(3))),
                                new Soldier(Team.HAN, new Position(new Row(4), new Column(5))),
                                new Soldier(Team.HAN, new Position(new Row(4), new Column(7))),
                                new Soldier(Team.HAN, new Position(new Row(4), new Column(9))),
                                new Guard(Team.HAN, new Position(new Row(1), new Column(4))), new Guard(Team.HAN, new Position(new Row(1), new Column(6))),
                                new King(Team.HAN, new Position(new Row(2), new Column(5)))
                        ),
                        // 초 - 상마마상
                        List.of(
                                new Chariot(Team.CHO, new Position(new Row(10), new Column(1))), new Chariot(Team.CHO, new Position(new Row(10), new Column(9))),
                                new Cannon(Team.CHO, new Position(new Row(8), new Column(2))), new Cannon(Team.CHO, new Position(new Row(8), new Column(8))),
                                new Elephant(Team.CHO, new Position(new Row(10), new Column(2))), new Elephant(Team.CHO, new Position(new Row(10), new Column(8))),
                                new Horse(Team.CHO, new Position(new Row(10), new Column(3))), new Horse(Team.CHO, new Position(new Row(10), new Column(7))),
                                new Soldier(Team.CHO, new Position(new Row(7), new Column(1))),
                                new Soldier(Team.CHO, new Position(new Row(7), new Column(3))),
                                new Soldier(Team.CHO, new Position(new Row(7), new Column(5))),
                                new Soldier(Team.CHO, new Position(new Row(7), new Column(7))),
                                new Soldier(Team.CHO, new Position(new Row(7), new Column(9))),
                                new Guard(Team.CHO, new Position(new Row(10), new Column(4))), new Guard(Team.CHO, new Position(new Row(10), new Column(6))),
                                new King(Team.CHO, new Position(new Row(9), new Column(5)))
                        )
                )
        );
    }
}
