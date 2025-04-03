package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Score;
import janggi.domain.SetupType;
import janggi.domain.Team;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class PiecesTest {

    @ParameterizedTest
    @MethodSource("provideSetupTypeAndTargetPieces")
    @DisplayName("상차림 옵션에 따라 이에 맞는 위치에 상, 마를 배치해야 한다")
    void createPieces(SetupType setupType,
                      List<Position> positions) {
        //given
        List<PieceType> pieceTypes = List.of(PieceType.ELEPHANT, PieceType.ELEPHANT,
                PieceType.HORSE, PieceType.HORSE,
                PieceType.ELEPHANT, PieceType.ELEPHANT,
                PieceType.HORSE, PieceType.HORSE);
        //when
        Pieces pieces = Pieces.createPieces(setupType, setupType);
        //then

        Assertions.assertAll(() -> {
            IntStream.range(0, positions.size())
                    .forEach(index -> {
                        Piece piece = pieces.getPieces().get(positions.get(index));
                        assertThat(piece.isSameType(pieceTypes.get(index))).isTrue();
                    });
        });
    }

    private static Stream<Arguments> provideSetupTypeAndTargetPieces() {
        return Stream.of(
                Arguments.of(SetupType.LEFT_ELEPHANT,
                        List.of(Position.of(10, 2),
                                Position.of(10, 7),
                                Position.of(10, 3),
                                Position.of(10, 8),
                                Position.of(1, 3),
                                Position.of(1, 8),
                                Position.of(1, 2),
                                Position.of(1, 7))),
                Arguments.of(SetupType.RIGHT_ELEPHANT,
                        List.of(Position.of(10, 3),
                                Position.of(10, 8),
                                Position.of(10, 2),
                                Position.of(10, 7),
                                Position.of(1, 2),
                                Position.of(1, 7),
                                Position.of(1, 3),
                                Position.of(1, 8))),
                Arguments.of(SetupType.INNER_ELEPHANT,
                        List.of(Position.of(10, 3),
                                Position.of(10, 7),
                                Position.of(10, 2),
                                Position.of(10, 8),
                                Position.of(1, 3),
                                Position.of(1, 7),
                                Position.of(1, 2),
                                Position.of(1, 8))),
                Arguments.of(SetupType.OUTER_ELEPHANT,
                        List.of(Position.of(10, 2),
                                Position.of(10, 8),
                                Position.of(10, 3),
                                Position.of(10, 7),
                                Position.of(1, 2),
                                Position.of(1, 8),
                                Position.of(1, 3),
                                Position.of(1, 7))));
    }

    @ParameterizedTest
    @CsvSource(value = {"RED,73.5", "GREEN,72.0"})
    @DisplayName("팀에 따른 초기 점수를 계산한다")
    void getInitScore(Team team, double expected) {
        //given
        //when
        Score actual = Pieces.getInitScore(team);

        //then
        assertThat(actual).isEqualTo(new Score(expected));
    }
}
