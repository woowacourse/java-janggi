package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static position.PositionFile.가;
import static position.PositionFile.나;
import static position.PositionFile.다;
import static position.PositionFile.라;
import static position.PositionFile.마;
import static position.PositionFile.바;
import static position.PositionFile.사;
import static position.PositionFile.아;
import static position.PositionFile.자;
import static testUtil.TestConstant.RANK_1;
import static testUtil.TestConstant.RANK_10;
import static testUtil.TestConstant.RANK_2;
import static testUtil.TestConstant.RANK_3;
import static testUtil.TestConstant.RANK_4;
import static testUtil.TestConstant.RANK_5;
import static testUtil.TestConstant.RANK_6;
import static testUtil.TestConstant.RANK_7;
import static testUtil.TestConstant.RANK_8;
import static testUtil.TestConstant.RANK_9;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import position.Position;

public class PieceTest {

    @Nested
    class 이동_테스트 {

        @ParameterizedTest
        @MethodSource("providePieceTypeAndNewPositions")
        void 포를_제외한_기물은_움직일_수_있다(PieceType pieceType, Position newPosition) {
            // given
            final Piece piece = new Piece(new Position(마, RANK_5), pieceType);

            // expected
            assertThatCode(() -> piece.movePiece(newPosition, List.of(), List.of()))
                    .doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource("provideNewPositions")
        void 포는_움직일_수_있다(Position newPosition) {
            // given
            final Piece piece = new Piece(new Position(마, RANK_5), PieceType.CANNON);

            // expected
            assertThatCode(() -> piece.movePiece(newPosition, List.of(
                    new Piece(new Position(마, RANK_3), PieceType.ROOK),
                    new Piece(new Position(마, RANK_7), PieceType.CANNON)
            ), List.of())).doesNotThrowAnyException();
        }

        @ParameterizedTest
        @MethodSource("providePoEncounterPositions")
        void 포는_포를_뛰어넘을_수_없다(Position poEncounterPosition) {
            // given
            final Piece piece = new Piece(new Position(마, RANK_5), PieceType.CANNON);

            // expected
            assertThatThrownBy(() -> piece.movePiece(poEncounterPosition, List.of(
                    new Piece(new Position(마, RANK_3), PieceType.ROOK),
                    new Piece(new Position(마, RANK_7), PieceType.CANNON)
            ), List.of())).isInstanceOf(IllegalArgumentException.class).hasMessage("움직일 수 없는 위치입니다.");
        }

        @ParameterizedTest
        @MethodSource("provideNoJumpPositions")
        void 포는_기물_하나를_뛰어넘어야_한다(Position noJumpPosition) {
            // given
            final Piece piece = new Piece(new Position(마, RANK_5), PieceType.CANNON);

            // expected
            assertThatThrownBy(() -> piece.movePiece(noJumpPosition, List.of(
                    new Piece(new Position(마, RANK_3), PieceType.ROOK),
                    new Piece(new Position(마, RANK_7), PieceType.CANNON)
            ), List.of())).isInstanceOf(IllegalArgumentException.class).hasMessage("움직일 수 없는 위치입니다.");
        }

        @Test
        void 기물은_움직일_수_없는_위치면_예외를_터트린다() {
            // given
            final Piece piece = new Piece(new Position(마, RANK_7), PieceType.ROOK);
            final Position position = new Position(라, RANK_1);

            // expected
            assertThatThrownBy(() -> piece.movePiece(position, List.of(), List.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("움직일 수 없는 위치입니다.");
        }

        @Test
        void 기물은_움직이면_새로운_위치의_기물을_반환한다() {
            // given
            final Piece piece = new Piece(new Position(가, RANK_1), PieceType.GENERAL);
            final Position newPosition = new Position(나, RANK_1);

            // when
            final Piece result = piece.movePiece(newPosition, List.of(), List.of());

            // then
            assertThat(result).extracting(
                    "position", "type"
            ).containsExactly(
                    new Position(나, RANK_1), PieceType.GENERAL
            );
        }

        @ParameterizedTest
        @MethodSource("providePieceTypeAndNewPositions")
        void 중간에_막는_기물이_있으면_예외가_발생한다(PieceType pieceType, Position newPosition) {
            // given
            final Piece piece = new Piece(new Position(마, RANK_5), pieceType);
            final List<Piece> surroundingPieces = List.of(
                    new Piece(new Position(라, RANK_5), PieceType.HORSE),
                    new Piece(new Position(바, RANK_5), PieceType.HORSE),
                    new Piece(new Position(마, RANK_4), PieceType.HORSE),
                    new Piece(new Position(마, RANK_6), PieceType.HORSE)
            );

            // expected
            assertThatThrownBy(() -> piece.movePiece(newPosition, surroundingPieces, List.of()))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("움직일 수 없는 위치입니다.");
        }

        public static Stream<Arguments> providePieceTypeAndNewPositions() {
            return Stream.of(
                    Arguments.of(PieceType.ELEPHANT, new Position(나, RANK_7)),
                    Arguments.of(PieceType.ELEPHANT, new Position(나, RANK_3)),
                    Arguments.of(PieceType.ELEPHANT, new Position(다, RANK_8)),
                    Arguments.of(PieceType.ELEPHANT, new Position(다, RANK_2)),
                    Arguments.of(PieceType.ELEPHANT, new Position(사, RANK_8)),
                    Arguments.of(PieceType.ELEPHANT, new Position(사, RANK_2)),
                    Arguments.of(PieceType.ELEPHANT, new Position(아, RANK_7)),
                    Arguments.of(PieceType.ELEPHANT, new Position(아, RANK_3)),

                    Arguments.of(PieceType.HORSE, new Position(다, RANK_6)),
                    Arguments.of(PieceType.HORSE, new Position(다, RANK_4)),
                    Arguments.of(PieceType.HORSE, new Position(라, RANK_7)),
                    Arguments.of(PieceType.HORSE, new Position(라, RANK_3)),
                    Arguments.of(PieceType.HORSE, new Position(바, RANK_7)),
                    Arguments.of(PieceType.HORSE, new Position(바, RANK_3)),
                    Arguments.of(PieceType.HORSE, new Position(사, RANK_6)),
                    Arguments.of(PieceType.HORSE, new Position(사, RANK_4)),

                    Arguments.of(PieceType.ROOK, new Position(마, RANK_1)),
                    Arguments.of(PieceType.ROOK, new Position(마, RANK_2)),
                    Arguments.of(PieceType.ROOK, new Position(마, RANK_3)),
                    Arguments.of(PieceType.ROOK, new Position(마, RANK_4)),
                    Arguments.of(PieceType.ROOK, new Position(마, RANK_6)),
                    Arguments.of(PieceType.ROOK, new Position(마, RANK_7)),
                    Arguments.of(PieceType.ROOK, new Position(마, RANK_8)),
                    Arguments.of(PieceType.ROOK, new Position(마, RANK_9)),
                    Arguments.of(PieceType.ROOK, new Position(마, RANK_10)),

                    Arguments.of(PieceType.ROOK, new Position(가, RANK_5)),
                    Arguments.of(PieceType.ROOK, new Position(나, RANK_5)),
                    Arguments.of(PieceType.ROOK, new Position(다, RANK_5)),
                    Arguments.of(PieceType.ROOK, new Position(라, RANK_5)),
                    Arguments.of(PieceType.ROOK, new Position(바, RANK_5)),
                    Arguments.of(PieceType.ROOK, new Position(사, RANK_5)),
                    Arguments.of(PieceType.ROOK, new Position(아, RANK_5)),
                    Arguments.of(PieceType.ROOK, new Position(자, RANK_5)),

                    Arguments.of(PieceType.GENERAL, new Position(라, RANK_5)),
                    Arguments.of(PieceType.GENERAL, new Position(바, RANK_5)),
                    Arguments.of(PieceType.GENERAL, new Position(마, RANK_4)),
                    Arguments.of(PieceType.GENERAL, new Position(마, RANK_6)),

                    Arguments.of(PieceType.GUARD, new Position(라, RANK_5)),
                    Arguments.of(PieceType.GUARD, new Position(바, RANK_5)),
                    Arguments.of(PieceType.GUARD, new Position(마, RANK_4)),
                    Arguments.of(PieceType.GUARD, new Position(마, RANK_6)),

                    Arguments.of(PieceType.CHO_SOLDIER, new Position(라, RANK_5)),
                    Arguments.of(PieceType.CHO_SOLDIER, new Position(바, RANK_5)),
                    Arguments.of(PieceType.CHO_SOLDIER, new Position(마, RANK_6)),

                    Arguments.of(PieceType.HAN_SOLDIER, new Position(라, RANK_5)),
                    Arguments.of(PieceType.HAN_SOLDIER, new Position(바, RANK_5)),
                    Arguments.of(PieceType.HAN_SOLDIER, new Position(마, RANK_4))
            );
        }

        public static Stream<Arguments> provideNewPositions() {
            return Stream.of(
                    Arguments.of(new Position(마, RANK_1)),
                    Arguments.of(new Position(마, RANK_2))
            );
        }

        public static Stream<Arguments> providePoEncounterPositions() {
            return Stream.of(
                    Arguments.of(new Position(마, RANK_8)),
                    Arguments.of(new Position(마, RANK_9)),
                    Arguments.of(new Position(마, RANK_10))
            );
        }

        public static Stream<Arguments> provideNoJumpPositions() {
            return Stream.of(
                    Arguments.of(new Position(마, RANK_4)),
                    Arguments.of(new Position(마, RANK_6))
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
            assertThatCode(() -> attacker.movePiece(
                    new Position(마, RANK_5),
                    List.of(),
                    List.of(new Piece(new Position(마, RANK_5), PieceType.HORSE))
            )).doesNotThrowAnyException();
        }

        public static Stream<Arguments> provideEatablePiece() {
            return Stream.of(
                    Arguments.of(new Piece(new Position(다, RANK_6), PieceType.HORSE)),
                    Arguments.of(new Piece(new Position(바, RANK_5), PieceType.ROOK)),
                    Arguments.of(new Piece(new Position(마, RANK_6), PieceType.GENERAL)),
                    Arguments.of(new Piece(new Position(마, RANK_4), PieceType.CHO_SOLDIER)),
                    Arguments.of(new Piece(new Position(나, RANK_3), PieceType.ELEPHANT)),
                    Arguments.of(new Piece(new Position(마, RANK_6), PieceType.GUARD))
            );
        }

        @ParameterizedTest
        @MethodSource("providePoNewPositions")
        void 포는_상대_기물을_먹을_수_있다(Piece piece) {
            // given

            // expected
            assertThatCode(() -> piece.movePiece(
                    new Position(마, RANK_5),
                    List.of(
                            new Piece(new Position(라, RANK_5), PieceType.HORSE),
                            new Piece(new Position(마, RANK_6), PieceType.HORSE)
                    ),
                    List.of(new Piece(new Position(마, RANK_5), PieceType.HORSE))
            )).doesNotThrowAnyException();
        }

        public static Stream<Arguments> providePoNewPositions() {
            return Stream.of(
                    Arguments.of(new Piece(new Position(마, RANK_7), PieceType.CANNON)),
                    Arguments.of(new Piece(new Position(마, RANK_8), PieceType.CANNON)),
                    Arguments.of(new Piece(new Position(다, RANK_5), PieceType.CANNON)),
                    Arguments.of(new Piece(new Position(나, RANK_5), PieceType.CANNON))
            );
        }

        @ParameterizedTest
        @MethodSource("providePoNewPositions")
        void 포는_포를_먹을_수_없다(Piece piece) {
            // given

            // expected
            assertThatThrownBy(() -> piece.movePiece(
                    new Position(마, RANK_5),
                    List.of(
                            new Piece(new Position(라, RANK_5), PieceType.HORSE),
                            new Piece(new Position(마, RANK_6), PieceType.HORSE)
                    ),
                    List.of(new Piece(new Position(마, RANK_5), PieceType.CANNON))
            )).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("움직일 수 없는 위치입니다.");

        }
    }
}
