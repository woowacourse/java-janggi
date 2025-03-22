package janggi.domain.piece;

import janggi.BaseTest;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static janggi.domain.position.PositionFile.*;
import static janggi.domain.position.PositionRank.*;
import static org.assertj.core.api.Assertions.*;

public class PieceTest extends BaseTest {

    @Nested
    class 이동_테스트 {

        @ParameterizedTest
        @MethodSource("providePieceTypeAndNewPositions")
        void 포를_제외한_기물은_움직일_수_있다(PieceType pieceType, Position newPosition) {
            // given
            final Piece piece = new Piece(new Position(FILE_5, RANK_5), pieceType);

            // expected
            assertThatCode(() -> piece.move(newPosition, List.of(), List.of()))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource("provideNewPositions")
        void 포는_움직일_수_있다(Position newPosition) {
            // given
            final Piece piece = new Piece(new Position(FILE_5, RANK_5), PieceType.포);

            // expected
            assertThatCode(() -> piece.move(newPosition, List.of(
                    new Piece(new Position(FILE_5, RANK_3), PieceType.차),
                    new Piece(new Position(FILE_5, RANK_7), PieceType.포)
            ), List.of())).doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource("providePoEncounterPositions")
        void 포는_포를_뛰어넘을_수_없다(Position poEncounterPosition) {
            // given
            final Piece piece = new Piece(new Position(FILE_5, RANK_5), PieceType.포);

            // expected
            assertThatThrownBy(() -> piece.move(poEncounterPosition, List.of(
                    new Piece(new Position(FILE_5, RANK_3), PieceType.차),
                    new Piece(new Position(FILE_5, RANK_7), PieceType.포)
            ), List.of())).isInstanceOf(IllegalArgumentException.class).hasMessage("움직일 수 없는 위치입니다.");
        }

        @ParameterizedTest
        @MethodSource("provideNoJumpPositions")
        void 포는_기물_하나를_뛰어넘어야_한다(Position noJumpPosition) {
            // given
            final Piece piece = new Piece(new Position(FILE_5, RANK_5), PieceType.포);

            // expected
            assertThatThrownBy(() -> piece.move(noJumpPosition, List.of(
                    new Piece(new Position(FILE_5, RANK_3), PieceType.차),
                    new Piece(new Position(FILE_5, RANK_7), PieceType.포)
            ), List.of())).isInstanceOf(IllegalArgumentException.class).hasMessage("움직일 수 없는 위치입니다.");
        }

        @Test
        void 기물은_움직일_수_없는_위치면_예외를_터트린다() {
            // given
            final Piece piece = new Piece(new Position(FILE_5, RANK_7), PieceType.차);
            final Position position = new Position(FILE_4, RANK_1);

            // expected
            assertThatThrownBy(() -> piece.move(position, List.of(), List.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("움직일 수 없는 위치입니다.");
        }

        @Test
        void 기물은_움직이면_새로운_위치의_기물을_반환한다() {
            // given
            final Piece piece = new Piece(new Position(FILE_1, RANK_1), PieceType.장);
            final Position newPosition = new Position(FILE_2, RANK_1);

            // when
            final Piece result = piece.move(newPosition, List.of(), List.of());

            // then
            assertThat(result).extracting(
                    "position", "type"
            ).containsExactly(
                    new Position(FILE_2, RANK_1), PieceType.장
            );
        }

        @ParameterizedTest
        @MethodSource("providePieceTypeAndNewPositions")
        void 중간에_막는_기물이_있으면_예외가_발생한다(PieceType pieceType, Position newPosition) {
            // given
            final Piece piece = new Piece(new Position(FILE_5, RANK_5), pieceType);
            final List<Piece> surroundingPieces = List.of(
                    new Piece(new Position(FILE_4, RANK_5), PieceType.마),
                    new Piece(new Position(FILE_6, RANK_5), PieceType.마),
                    new Piece(new Position(FILE_5, RANK_4), PieceType.마),
                    new Piece(new Position(FILE_5, RANK_6), PieceType.마)
            );

            // expected
            assertThatThrownBy(() -> piece.move(newPosition, surroundingPieces, List.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("움직일 수 없는 위치입니다.");
        }

        public static Stream<Arguments> providePieceTypeAndNewPositions() {
            return Stream.of(
                    Arguments.of(PieceType.상, new Position(FILE_2, RANK_7)),
                    Arguments.of(PieceType.상, new Position(FILE_2, RANK_3)),
                    Arguments.of(PieceType.상, new Position(FILE_3, RANK_8)),
                    Arguments.of(PieceType.상, new Position(FILE_3, RANK_2)),
                    Arguments.of(PieceType.상, new Position(FILE_7, RANK_8)),
                    Arguments.of(PieceType.상, new Position(FILE_7, RANK_2)),
                    Arguments.of(PieceType.상, new Position(FILE_8, RANK_7)),
                    Arguments.of(PieceType.상, new Position(FILE_8, RANK_3)),

                    Arguments.of(PieceType.마, new Position(FILE_3, RANK_6)),
                    Arguments.of(PieceType.마, new Position(FILE_3, RANK_4)),
                    Arguments.of(PieceType.마, new Position(FILE_4, RANK_7)),
                    Arguments.of(PieceType.마, new Position(FILE_4, RANK_3)),
                    Arguments.of(PieceType.마, new Position(FILE_6, RANK_7)),
                    Arguments.of(PieceType.마, new Position(FILE_6, RANK_3)),
                    Arguments.of(PieceType.마, new Position(FILE_7, RANK_6)),
                    Arguments.of(PieceType.마, new Position(FILE_7, RANK_4)),

                    Arguments.of(PieceType.차, new Position(FILE_5, RANK_1)),
                    Arguments.of(PieceType.차, new Position(FILE_5, RANK_2)),
                    Arguments.of(PieceType.차, new Position(FILE_5, RANK_3)),
                    Arguments.of(PieceType.차, new Position(FILE_5, RANK_4)),
                    Arguments.of(PieceType.차, new Position(FILE_5, RANK_6)),
                    Arguments.of(PieceType.차, new Position(FILE_5, RANK_7)),
                    Arguments.of(PieceType.차, new Position(FILE_5, RANK_8)),
                    Arguments.of(PieceType.차, new Position(FILE_5, RANK_9)),
                    Arguments.of(PieceType.차, new Position(FILE_5, RANK_10)),

                    Arguments.of(PieceType.차, new Position(FILE_1, RANK_5)),
                    Arguments.of(PieceType.차, new Position(FILE_2, RANK_5)),
                    Arguments.of(PieceType.차, new Position(FILE_3, RANK_5)),
                    Arguments.of(PieceType.차, new Position(FILE_4, RANK_5)),
                    Arguments.of(PieceType.차, new Position(FILE_6, RANK_5)),
                    Arguments.of(PieceType.차, new Position(FILE_7, RANK_5)),
                    Arguments.of(PieceType.차, new Position(FILE_8, RANK_5)),
                    Arguments.of(PieceType.차, new Position(FILE_9, RANK_5)),

                    Arguments.of(PieceType.장, new Position(FILE_4, RANK_5)),
                    Arguments.of(PieceType.장, new Position(FILE_6, RANK_5)),
                    Arguments.of(PieceType.장, new Position(FILE_5, RANK_4)),
                    Arguments.of(PieceType.장, new Position(FILE_5, RANK_6)),

                    Arguments.of(PieceType.사, new Position(FILE_4, RANK_5)),
                    Arguments.of(PieceType.사, new Position(FILE_6, RANK_5)),
                    Arguments.of(PieceType.사, new Position(FILE_5, RANK_4)),
                    Arguments.of(PieceType.사, new Position(FILE_5, RANK_6)),

                    Arguments.of(PieceType.졸, new Position(FILE_4, RANK_5)),
                    Arguments.of(PieceType.졸, new Position(FILE_6, RANK_5)),
                    Arguments.of(PieceType.졸, new Position(FILE_5, RANK_6)),

                    Arguments.of(PieceType.병, new Position(FILE_4, RANK_5)),
                    Arguments.of(PieceType.병, new Position(FILE_6, RANK_5)),
                    Arguments.of(PieceType.병, new Position(FILE_5, RANK_4))
            );
        }

        public static Stream<Arguments> provideNewPositions() {
            return Stream.of(
                    Arguments.of(new Position(FILE_5, RANK_1)),
                    Arguments.of(new Position(FILE_5, RANK_2))
            );
        }

        public static Stream<Arguments> providePoEncounterPositions() {
            return Stream.of(
                    Arguments.of(new Position(FILE_5, RANK_8)),
                    Arguments.of(new Position(FILE_5, RANK_9)),
                    Arguments.of(new Position(FILE_5, RANK_10))
            );
        }

        public static Stream<Arguments> provideNoJumpPositions() {
            return Stream.of(
                    Arguments.of(new Position(FILE_5, RANK_4)),
                    Arguments.of(new Position(FILE_5, RANK_6))
            );
        }
    }

    @Nested
    class 먹기_테스트 {

        @ParameterizedTest
        @MethodSource("provideEatablePiece")
        void 포를_제외한_기물은_상대_기물을_먹을_수_있다(Piece attacker) {
            // given

            // expected
            assertThatCode(() -> attacker.move(
                    new Position(FILE_5, RANK_5),
                    List.of(),
                    List.of(new Piece(new Position(FILE_5, RANK_5), PieceType.마))
            )).doesNotThrowAnyException();
        }

        public static Stream<Arguments> provideEatablePiece() {
            return Stream.of(
                    Arguments.of(new Piece(new Position(FILE_3, RANK_6), PieceType.마)),
                    Arguments.of(new Piece(new Position(FILE_6, RANK_5), PieceType.차)),
                    Arguments.of(new Piece(new Position(FILE_5, RANK_6), PieceType.장)),
                    Arguments.of(new Piece(new Position(FILE_5, RANK_4), PieceType.졸)),
                    Arguments.of(new Piece(new Position(FILE_2, RANK_3), PieceType.상)),
                    Arguments.of(new Piece(new Position(FILE_5, RANK_6), PieceType.사))
            );
        }

        @ParameterizedTest
        @MethodSource("providePoNewPositions")
        void 포는_상대_기물을_먹을_수_있다(Piece piece) {
            // given

            // expected
            assertThatCode(() -> piece.move(
                    new Position(FILE_5, RANK_5),
                    List.of(
                            new Piece(new Position(FILE_4, RANK_5), PieceType.마),
                            new Piece(new Position(FILE_5, RANK_6), PieceType.마)
                    ),
                    List.of(new Piece(new Position(FILE_5, RANK_5), PieceType.마))
            )).doesNotThrowAnyException();
        }

        public static Stream<Arguments> providePoNewPositions() {
            return Stream.of(
                    Arguments.of(new Piece(new Position(FILE_5, RANK_7), PieceType.포)),
                    Arguments.of(new Piece(new Position(FILE_5, RANK_8), PieceType.포)),
                    Arguments.of(new Piece(new Position(FILE_3, RANK_5), PieceType.포)),
                    Arguments.of(new Piece(new Position(FILE_2, RANK_5), PieceType.포))
            );
        }

        @ParameterizedTest
        @MethodSource("providePoNewPositions")
        void 포는_포를_먹을_수_없다(Piece piece) {
            // given

            // expected
            assertThatThrownBy(() -> piece.move(
                    new Position(FILE_5, RANK_5),
                    List.of(
                            new Piece(new Position(FILE_4, RANK_5), PieceType.마),
                            new Piece(new Position(FILE_5, RANK_6), PieceType.마)
                    ),
                    List.of(new Piece(new Position(FILE_5, RANK_5), PieceType.포))
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("움직일 수 없는 위치입니다.");

        }
    }
}
