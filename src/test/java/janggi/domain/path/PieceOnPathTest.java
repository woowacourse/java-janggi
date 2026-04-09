package janggi.domain.path;

import janggi.domain.piece.*;
import janggi.domain.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PieceOnPathTest {

    @DisplayName("빈 기물이 아닌 기물의 수를 반환한다.")
    @ParameterizedTest
    @MethodSource("countNonEmptyArguments")
    void 빈기물이_아닌_기물의_수를_반환한다(List<Piece> pieces, long expected) {
        // given
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieces.forEach(pieceOnPath::add);

        // when
        long count = pieceOnPath.countNonEmpty();

        // then
        assertThat(count).isEqualTo(expected);
    }

    private static Stream<Arguments> countNonEmptyArguments() {
        return Stream.of(
                Arguments.of(List.of(new EmptyPiece(), new Soldier(Team.HAN)), 1L),
                Arguments.of(List.of(new EmptyPiece(), new EmptyPiece()), 0L)
        );
    }

    @DisplayName("특정 타입의 기물이 있는지 확인한다.")
    @ParameterizedTest
    @MethodSource("hasTypeArguments")
    void 특정_타입의_기물이_있는지_확인한다(Piece piece, PieceType type, boolean expected) {
        // given
        PieceOnPath pieceOnPath = new PieceOnPath();
        pieceOnPath.add(piece);

        // when & then
        assertThat(pieceOnPath.hasType(type)).isEqualTo(expected);
    }

    private static Stream<Arguments> hasTypeArguments() {
        return Stream.of(
                Arguments.of(new Cannon(Team.HAN), PieceType.CANNON, true),
                Arguments.of(new Soldier(Team.HAN), PieceType.CANNON, false)
        );
    }
}
