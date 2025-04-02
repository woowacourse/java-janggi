package janggi.piece;

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

import janggi.PieceType;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PieceCreatorTest {
    @MethodSource("returnPieceTypeAndPiece")
    @ParameterizedTest
    void create(PieceType pieceType, Piece expected) {
        // when
        Piece actual = PieceCreator.create(GREEN, pieceType);

        // then
        assertThat(actual).hasSameClassAs(expected);
    }

    private static Stream<Arguments> returnPieceTypeAndPiece() {
        return Stream.of(arguments(SOLDIER, new Soldier(GREEN, SOLDIER)),
                arguments(HORSE, new Horse(GREEN, HORSE)),
                arguments(GUARD, new Guard(GREEN, GUARD)),
                arguments(GENERAL, new General(GREEN, GENERAL)),
                arguments(ELEPHANT, new Elephant(GREEN, ELEPHANT)),
                arguments(CHARIOT, new Chariot(GREEN ,CHARIOT)),
                arguments(CANON, new Canon(GREEN, CANON)));
    }
}
