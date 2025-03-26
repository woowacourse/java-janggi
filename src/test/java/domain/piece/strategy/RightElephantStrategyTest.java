package domain.piece.strategy;

import static fixtures.PositionFixture.B0;
import static fixtures.PositionFixture.C0;
import static fixtures.PositionFixture.G0;
import static fixtures.PositionFixture.H0;
import static org.assertj.core.api.Assertions.assertThat;

import domain.position.Position;
import domain.piece.TeamType;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RightElephantStrategyTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("Right Elephant 형식으로 마와상을 반환한다")
    void testCreateElephantHorse(Position position,PieceType expected) {
        RightElephantStrategy rightElephantStrategy = new RightElephantStrategy();

        Map<Position, Piece> elephantHorse = rightElephantStrategy.createElephantHorse(TeamType.CHO);
        Piece piece = elephantHorse.get(position);

        assertThat(piece.getType()).isEqualTo(expected);
    }

    private static Stream<Arguments> testCreateElephantHorse(){
        return Stream.of(
                Arguments.of(B0,PieceType.HORSE),
                Arguments.of(C0,PieceType.ELEPHANT),
                Arguments.of(H0,PieceType.ELEPHANT),
                Arguments.of(G0,PieceType.HORSE)
        );
    }
}
