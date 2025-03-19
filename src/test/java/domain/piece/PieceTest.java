package domain.piece;

import domain.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static domain.position.PositionFile.*;
import static org.assertj.core.api.Assertions.*;
import static testUtil.TestConstant.*;

public class PieceTest {

    public static Stream<Arguments> providePieceTypeAndNewPosition() {
        return Stream.of(
                Arguments.of(PieceType.상, new Position(나, RANK_7)),
                Arguments.of(PieceType.상, new Position(나, RANK_3)),
                Arguments.of(PieceType.상, new Position(다, RANK_8)),
                Arguments.of(PieceType.상, new Position(다, RANK_2)),
                Arguments.of(PieceType.상, new Position(사, RANK_8)),
                Arguments.of(PieceType.상, new Position(사, RANK_2)),
                Arguments.of(PieceType.상, new Position(아, RANK_7)),
                Arguments.of(PieceType.상, new Position(아, RANK_3)),

                Arguments.of(PieceType.마, new Position(다, RANK_6)),
                Arguments.of(PieceType.마, new Position(다, RANK_4)),
                Arguments.of(PieceType.마, new Position(라, RANK_7)),
                Arguments.of(PieceType.마, new Position(라, RANK_3)),
                Arguments.of(PieceType.마, new Position(바, RANK_7)),
                Arguments.of(PieceType.마, new Position(바, RANK_3)),
                Arguments.of(PieceType.마, new Position(사, RANK_6)),
                Arguments.of(PieceType.마, new Position(사, RANK_4)),

                Arguments.of(PieceType.차, new Position(마, RANK_1)),
                Arguments.of(PieceType.차, new Position(마, RANK_2)),
                Arguments.of(PieceType.차, new Position(마, RANK_3)),
                Arguments.of(PieceType.차, new Position(마, RANK_4)),
                Arguments.of(PieceType.차, new Position(마, RANK_6)),
                Arguments.of(PieceType.차, new Position(마, RANK_7)),
                Arguments.of(PieceType.차, new Position(마, RANK_8)),
                Arguments.of(PieceType.차, new Position(마, RANK_9)),
                Arguments.of(PieceType.차, new Position(마, RANK_10)),

                Arguments.of(PieceType.차, new Position(가, RANK_5)),
                Arguments.of(PieceType.차, new Position(나, RANK_5)),
                Arguments.of(PieceType.차, new Position(다, RANK_5)),
                Arguments.of(PieceType.차, new Position(라, RANK_5)),
                Arguments.of(PieceType.차, new Position(바, RANK_5)),
                Arguments.of(PieceType.차, new Position(사, RANK_5)),
                Arguments.of(PieceType.차, new Position(아, RANK_5)),
                Arguments.of(PieceType.차, new Position(자, RANK_5)),

                Arguments.of(PieceType.장, new Position(라, RANK_5)),
                Arguments.of(PieceType.장, new Position(바, RANK_5)),
                Arguments.of(PieceType.장, new Position(마, RANK_4)),
                Arguments.of(PieceType.장, new Position(마, RANK_6)),

                Arguments.of(PieceType.사, new Position(라, RANK_5)),
                Arguments.of(PieceType.사, new Position(바, RANK_5)),
                Arguments.of(PieceType.사, new Position(마, RANK_4)),
                Arguments.of(PieceType.사, new Position(마, RANK_6)),

                Arguments.of(PieceType.졸, new Position(라, RANK_5)),
                Arguments.of(PieceType.졸, new Position(바, RANK_5)),
                Arguments.of(PieceType.졸, new Position(마, RANK_6))
        );
    }

    @ParameterizedTest
    @MethodSource("providePieceTypeAndNewPosition")
    void 피스는_움직일_수_있다(PieceType pieceType, Position newPosition) {
        // given
        final Piece piece = new Piece(new Position(마, RANK_5), pieceType);

        // expected
        assertThatCode(() -> piece.move(newPosition, List.of()))
                .doesNotThrowAnyException();
    }

    @Test
    void 피스는_움직일_수_없는_위치면_예외를_터트린다() {
        // given
        final Piece piece = new Piece(new Position(마, RANK_7), PieceType.차);
        final Position position = new Position(라, RANK_1);

        // expected
        assertThatThrownBy(() -> piece.move(position, List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("움직일 수 없는 위치입니다.");
    }

    @Test
    void 피스는_움직이면_새로운_위치의_피스를_반환한다() {
        // given
        final Piece piece = new Piece(new Position(가, RANK_1), PieceType.장);
        final Position newPosition = new Position(나, RANK_1);

        // when
        final Piece result = piece.move(newPosition, List.of());

        // then
        assertThat(result).extracting(
                "position", "type"
        ).containsExactly(
                new Position(나, RANK_1), PieceType.장
        );
    }

    @ParameterizedTest
    @MethodSource("providePieceTypeAndNewPosition")
    void 중간에_막는_기물이_있으면_예외가_발생한다(PieceType pieceType, Position newPosition) {
        // given
        final Piece piece = new Piece(new Position(마, RANK_5), pieceType);
        final List<Piece> surroundingPieces = List.of(
                new Piece(new Position(라, RANK_5), PieceType.마),
                new Piece(new Position(바, RANK_5), PieceType.마),
                new Piece(new Position(마, RANK_4), PieceType.마),
                new Piece(new Position(마, RANK_6), PieceType.마)
        );

        // expected
        assertThatThrownBy(() -> piece.move(newPosition, surroundingPieces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("움직일 수 없는 위치입니다.");
    }
}
