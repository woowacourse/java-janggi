package domain.board.wing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Intersection;
import domain.game.Side;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class WingsTest {

    @DisplayName("양쪽 진의 진영 검증")
    @Nested
    class 양쪽_진의_진영_검증 {

        private static final Side CHO = Side.CHO;

        @Nested
        class 생성할_때_기물의_진영을_검증한다 {
            private final WingPieces choLeftWingPieces = new WingPieces(
                    Piece.of(PieceType.HORSE, Side.CHO),
                    Piece.of(PieceType.ELEPHANT, Side.CHO)
            );
            private final WingPieces choRightWingPieces = new WingPieces(
                    Piece.of(PieceType.HORSE, Side.CHO),
                    Piece.of(PieceType.ELEPHANT, Side.CHO)
            );
            private final WingPieces hanWingPieces = new WingPieces(
                    Piece.of(PieceType.HORSE, Side.HAN),
                    Piece.of(PieceType.ELEPHANT, Side.HAN)
            );

            @DisplayName("좌진과 우진의 기물 진영이 다르면 예외를 던진다")
            @ParameterizedTest
            @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
            void 죄진과_우진의_기물_진영이_다르면_예외를_던진다(Side side) {
                assertThatThrownBy(() -> new Wings(side, choLeftWingPieces, hanWingPieces))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @DisplayName("좌진과 우진에 같은 진영의 기물만 있다면 정상적으로 생성된다")
            @Test
            void 좌진과_우진에_같은_진영의_기물만_있다면_정상적으로_생성된다() {
                assertThatNoException().isThrownBy(() -> new Wings(CHO, choLeftWingPieces, choRightWingPieces));
            }
        }

        @Test
        void 초의_좌진과_우진_기물의_초기_위치를_반환한다() {
            // given
            Piece first = Piece.of(PieceType.HORSE, Side.CHO);
            Piece second = Piece.of(PieceType.ELEPHANT, Side.CHO);
            Piece third = Piece.of(PieceType.HORSE, Side.CHO);
            Piece fourth = Piece.of(PieceType.ELEPHANT, Side.CHO);

            Wings choWings = new Wings(
                    CHO,
                    new WingPieces(first, second),
                    new WingPieces(third, fourth)
            );

            // when
            Map<Intersection, Piece> setUpPieces = choWings.setUpPieces();

            // then
            assertThat(setUpPieces).containsExactlyInAnyOrderEntriesOf(
                    Map.of(
                            new Intersection(10, 2), first,
                            new Intersection(10, 3), second,
                            new Intersection(10, 7), third,
                            new Intersection(10, 8), fourth
                    )
            );
        }
    }

    @DisplayName("진영별 초기 위치 검증")
    @Nested
    class 진영별_초기_위치_검증 {

        @Test
        void 한의_초기_위치() {
            // given
            Piece first = Piece.of(PieceType.HORSE, Side.HAN);
            Piece second = Piece.of(PieceType.ELEPHANT, Side.HAN);
            Piece third = Piece.of(PieceType.HORSE, Side.HAN);
            Piece fourth = Piece.of(PieceType.ELEPHANT, Side.HAN);

            Wings hanWings = new Wings(
                    Side.HAN,
                    new WingPieces(first, second),
                    new WingPieces(third, fourth)
            );

            // when
            Map<Intersection, Piece> setUpPieces = hanWings.setUpPieces();

            // then
            assertThat(setUpPieces).containsExactlyInAnyOrderEntriesOf(
                    Map.of(
                            new Intersection(1, 8), first,
                            new Intersection(1, 7), second,
                            new Intersection(1, 3), third,
                            new Intersection(1, 2), fourth
                    )
            );
        }

        @Test
        void 초의_초기_위치() {
            // given
            Piece first = Piece.of(PieceType.HORSE, Side.CHO);
            Piece second = Piece.of(PieceType.ELEPHANT, Side.CHO);
            Piece third = Piece.of(PieceType.HORSE, Side.CHO);
            Piece fourth = Piece.of(PieceType.ELEPHANT, Side.CHO);

            Wings choWings = new Wings(
                    Side.CHO,
                    new WingPieces(first, second),
                    new WingPieces(third, fourth)
            );

            // when
            Map<Intersection, Piece> setUpPieces = choWings.setUpPieces();

            // then
            assertThat(setUpPieces).containsExactlyInAnyOrderEntriesOf(
                    Map.of(
                            new Intersection(10, 2), first,
                            new Intersection(10, 3), second,
                            new Intersection(10, 7), third,
                            new Intersection(10, 8), fourth
                    )
            );
        }
    }
}
