package janggi;

import static janggi.PieceType.CANON;
import static janggi.PieceType.CHARIOT;
import static janggi.PieceType.ELEPHANT;
import static janggi.PieceType.GENERAL;
import static janggi.PieceType.GUARD;
import static janggi.PieceType.HORSE;
import static janggi.PieceType.SOLDIER;
import static janggi.Team.GREEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import janggi.piece.Canon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Soldier;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceTypeTest {
    @MethodSource("returnPieceAndPieceType")
    @ParameterizedTest
    void from(Piece piece, PieceType expected) {
        // when
        PieceType actual = PieceType.from(piece);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @MethodSource("returnPieceAndPieceType")
    @ParameterizedTest
    void toPiece(Piece expected, PieceType pieceType) {
        // when
        Piece actual = pieceType.toPiece(GREEN);

        // then
        assertThat(actual).hasSameClassAs(expected);
    }

    private static Stream<Arguments> returnPieceAndPieceType() {
        return Stream.of(arguments(new Soldier(GREEN), SOLDIER),
                arguments(new Horse(GREEN), HORSE),
                arguments(new Guard(GREEN), GUARD),
                arguments(new General(GREEN), GENERAL),
                arguments(new Elephant(GREEN), ELEPHANT),
                arguments(new Chariot(GREEN), CHARIOT),
                arguments(new Canon(GREEN), CANON));
    }
}
