package janggi.piece;

import janggi.board.TableOption;
import janggi.position.Position;
import janggi.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class PieceGeneratorTest {
    @ParameterizedTest
    @MethodSource("makeInitialPieceTestData")
    @DisplayName("초기 기물이 올바르게 생성되는지 확인")
    void pieceGeneratorTest(Map<Position, Piece> expectedPieces) {
        //given & when
        Map<Position, Piece> pieces = new PieceGenerator().generateInitialPieces(TableOption.HEEH, TableOption.EHHE);
        //then
        Assertions.assertThat(pieces).containsAllEntriesOf(expectedPieces);
    }

    static Stream<Arguments> makeInitialPieceTestData() {
        // 한 - 마상상마
        Map<Position, Piece> hanPieces = new HashMap<>();
        hanPieces.put(new Position(1, 1), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        hanPieces.put(new Position(1, 9), new DefaultPiece(Team.HAN, PieceType.CHARIOT));
        hanPieces.put(new Position(3, 2), new DefaultPiece(Team.HAN, PieceType.CANNON));
        hanPieces.put(new Position(3, 8), new DefaultPiece(Team.HAN, PieceType.CANNON));
        hanPieces.put(new Position(1, 3), new DefaultPiece(Team.HAN, PieceType.ELEPHANT));
        hanPieces.put(new Position(1, 7), new DefaultPiece(Team.HAN, PieceType.ELEPHANT));
        hanPieces.put(new Position(1, 2), new DefaultPiece(Team.HAN, PieceType.HORSE));
        hanPieces.put(new Position(1, 8), new DefaultPiece(Team.HAN, PieceType.HORSE));
        hanPieces.put(new Position(4, 1), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        hanPieces.put(new Position(4, 3), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        hanPieces.put(new Position(4, 5), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        hanPieces.put(new Position(4, 7), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        hanPieces.put(new Position(4, 9), new DefaultPiece(Team.HAN, PieceType.SOLDIER));
        hanPieces.put(new Position(1, 4), new PalacePiece(Team.HAN, PieceType.GUARD));
        hanPieces.put(new Position(1, 6), new PalacePiece(Team.HAN, PieceType.GUARD));
        hanPieces.put(new Position(2, 5), new PalacePiece(Team.HAN, PieceType.KING));

        // 초 - 상마마상
        Map<Position, Piece> choPieces = new HashMap<>();
        choPieces.put(new Position(10, 1), new DefaultPiece(Team.CHO, PieceType.CHARIOT));
        choPieces.put(new Position(10, 9), new DefaultPiece(Team.CHO, PieceType.CHARIOT));
        choPieces.put(new Position(8, 2), new DefaultPiece(Team.CHO, PieceType.CANNON));
        choPieces.put(new Position(8, 8), new DefaultPiece(Team.CHO, PieceType.CANNON));
        choPieces.put(new Position(10, 2), new DefaultPiece(Team.CHO, PieceType.ELEPHANT));
        choPieces.put(new Position(10, 8), new DefaultPiece(Team.CHO, PieceType.ELEPHANT));
        choPieces.put(new Position(10, 3), new DefaultPiece(Team.CHO, PieceType.HORSE));
        choPieces.put(new Position(10, 7), new DefaultPiece(Team.CHO, PieceType.HORSE));
        choPieces.put(new Position(7, 1), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        choPieces.put(new Position(7, 3), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        choPieces.put(new Position(7, 5), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        choPieces.put(new Position(7, 7), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        choPieces.put(new Position(7, 9), new DefaultPiece(Team.CHO, PieceType.SOLDIER));
        choPieces.put(new Position(10, 4), new PalacePiece(Team.CHO, PieceType.GUARD));
        choPieces.put(new Position(10, 6), new PalacePiece(Team.CHO, PieceType.GUARD));
        choPieces.put(new Position(9, 5), new PalacePiece(Team.CHO, PieceType.KING));

        return Stream.of(
                Arguments.arguments(
                        hanPieces
                ),
                Arguments.arguments(
                        choPieces
                )
        );
    }
}
