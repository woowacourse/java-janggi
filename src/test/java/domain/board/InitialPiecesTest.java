package domain.board;

import static domain.game.Side.CHO;
import static domain.game.Side.HAN;
import static domain.piece.PieceType.CANNON;
import static domain.piece.PieceType.CHARIOT;
import static domain.piece.PieceType.ELEPHANT;
import static domain.piece.PieceType.GENERAL;
import static domain.piece.PieceType.GUARD;
import static domain.piece.PieceType.HORSE;
import static domain.piece.PieceType.SOLDIER;
import static org.assertj.core.api.Assertions.assertThat;

import domain.board.wing.WingPieces;
import domain.board.wing.Wings;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class InitialPiecesTest {

    private static final int INITIAL_PIECES_AMOUNT = 32;

    private final WingPieces hanWing = new WingPieces(
            new Piece(HORSE, HAN),
            new Piece(ELEPHANT, HAN)
    );
    private final WingPieces choWing = new WingPieces(
            new Piece(HORSE, CHO),
            new Piece(ELEPHANT, CHO)
    );
    private final Wings hanWings = new Wings(HAN, hanWing, hanWing);
    private final Wings choWings = new Wings(CHO, choWing, choWing);

    @Test
    void 초기화_된_기물의_개수_검증() {
        // given
        InitialPieces initialPieces = new InitialPieces(hanWings, choWings);

        // when and then
        assertThat(initialPieces.size()).isEqualTo(INITIAL_PIECES_AMOUNT);
    }

    @DisplayName("고정된 위치에 놓이는 기물이 정상적으로 초기화되었는지 확인")
    @Nested
    class 고정_기물_위치_검증 {

        private static final Wings DEFAULT_HAN_WINGS = new Wings(
                HAN,
                new WingPieces(new Piece(HORSE, HAN), new Piece(ELEPHANT, HAN)),
                new WingPieces(new Piece(HORSE, HAN), new Piece(ELEPHANT, HAN))
        );
        private static final Wings DEFAULT_CHO_WINGS = new Wings(
                CHO,
                new WingPieces(new Piece(HORSE, CHO), new Piece(ELEPHANT, CHO)),
                new WingPieces(new Piece(HORSE, CHO), new Piece(ELEPHANT, CHO))
        );

        private InitialPieces initialPieces;

        @BeforeEach
        void setUp() {
            initialPieces = new InitialPieces(DEFAULT_HAN_WINGS, DEFAULT_CHO_WINGS);
        }

        @DisplayName("차")
        @ParameterizedTest(name = "{0} 진영의 차가 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 차(Side side) {
            // given
            AlivePieces alivePieces = initialPieces.toAlivePieces();
            int expectedRow = side.getRowAt(new MoveAmount(0));
            List<Intersection> expectedPositions = Stream.of(1, 9)
                    .map(expectedFile -> new Intersection(expectedRow, expectedFile))
                    .toList();

            // when and then
            assertThat(expectedPositions)
                    .hasSize(2)
                    .extracting(alivePieces::placedAt)
                    .allSatisfy(piece -> {
                        assertThat(piece.isSameType(CHARIOT)).isTrue();
                        assertThat(piece.isSameSide(side)).isTrue();
                    });
        }

        @DisplayName("마")
        @ParameterizedTest(name = "{0} 진영의 마가 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 마(Side side) {
            // given
            AlivePieces alivePieces = initialPieces.toAlivePieces();
            int expectedRow = side.getRowAt(new MoveAmount(0));
            List<Intersection> expectedPositions = Stream.of(1, 9)
                    .map(expectedFile -> new Intersection(expectedRow, expectedFile))
                    .toList();

            // when and then
            assertThat(expectedPositions)
                    .hasSize(2)
                    .extracting(alivePieces::placedAt)
                    .allSatisfy(piece -> {
                        assertThat(piece.isSameType(CHARIOT)).isTrue();
                        assertThat(piece.isSameSide(side)).isTrue();
                    });
        }

        @DisplayName("사")
        @ParameterizedTest(name = "{0} 진영의 사가 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 사(Side side) {
            // given
            AlivePieces alivePieces = initialPieces.toAlivePieces();
            int expectedRow = side.getRowAt(new MoveAmount(0));
            List<Intersection> expectedPositions = Stream.of(4, 6)
                    .map(expectedFile -> new Intersection(expectedRow, expectedFile))
                    .toList();

            // when and then
            assertThat(expectedPositions)
                    .hasSize(2)
                    .extracting(alivePieces::placedAt)
                    .allSatisfy(piece -> {
                        assertThat(piece.isSameType(GUARD)).isTrue();
                        assertThat(piece.isSameSide(side)).isTrue();
                    });
        }

        @DisplayName("궁")
        @ParameterizedTest(name = "{0} 진영의 궁이 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 궁(Side side) {
            // given
            AlivePieces alivePieces = initialPieces.toAlivePieces();
            int expectedRow = side.getRowAt(new MoveAmount(1));
            List<Intersection> expectedPositions = List.of(new Intersection(expectedRow, 5));

            // when and then
            assertThat(expectedPositions)
                    .hasSize(1)
                    .extracting(alivePieces::placedAt)
                    .allSatisfy(piece -> {
                        assertThat(piece.isSameType(GENERAL)).isTrue();
                        assertThat(piece.isSameSide(side)).isTrue();
                    });
        }

        @DisplayName("포")
        @ParameterizedTest(name = "{0} 진영의 포가 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 포(Side side) {
            // given
            AlivePieces alivePieces = initialPieces.toAlivePieces();
            int expectedRow = side.getRowAt(new MoveAmount(2));
            List<Intersection> expectedPositions = Stream.of(2, 8)
                    .map(expectedFile -> new Intersection(expectedRow, expectedFile))
                    .toList();

            // when and then
            assertThat(expectedPositions)
                    .hasSize(2)
                    .extracting(alivePieces::placedAt)
                    .allSatisfy(piece -> {
                        assertThat(piece.isSameType(CANNON)).isTrue();
                        assertThat(piece.isSameSide(side)).isTrue();
                    });
        }

        @ParameterizedTest(name = "{0} 진영의 졸/병이 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 졸_병(Side side) {
            // given
            AlivePieces alivePieces = initialPieces.toAlivePieces();
            int expectedRow = side.getRowAt(new MoveAmount(3));
            List<Intersection> expectedPositions = Stream.of(1, 3, 5, 7, 9)
                    .map(expectedFile -> new Intersection(expectedRow, expectedFile))
                    .toList();

            // when and then
            assertThat(expectedPositions)
                    .hasSize(5)
                    .extracting(alivePieces::placedAt)
                    .allSatisfy(piece -> {
                        assertThat(piece.isSameType(SOLDIER)).isTrue();
                        assertThat(piece.isSameSide(side)).isTrue();
                    });
        }
    }
}
