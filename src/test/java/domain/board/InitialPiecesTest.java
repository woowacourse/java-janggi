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
import domain.piece.PieceType;
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

    @Test
    void 초기화_된_기물의_개수_검증() {
        // given
        InitialPieces initialPieces = new InitialPieces(DEFAULT_HAN_WINGS, DEFAULT_CHO_WINGS);

        // when and then
        assertThat(initialPieces.size()).isEqualTo(INITIAL_PIECES_AMOUNT);
    }

    @DisplayName("고정된 위치에 놓이는 기물이 정상적으로 초기화되었는지 확인")
    @Nested
    class 고정_기물_위치_검증 {

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

    @DisplayName("좌진과 우진에 놓이는 기물이 정상적으로 초기화되었는지 확인")
    @Nested
    class 좌진과_우진_확인 {

        @ParameterizedTest(name = "{0} 진영의 상마상마 차림이 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 상마상마_차림_위치_검증(Side side) {
            // given
            Wings sangMaSangMa = new Wings(
                    side,
                    createWingPieces(side, ELEPHANT, HORSE),
                    createWingPieces(side, ELEPHANT, HORSE)
            );

            InitialPieces initialPieces = createInitialPieces(side, sangMaSangMa);
            AlivePieces alivePieces = initialPieces.toAlivePieces();

            int expectedRow = side.getRowAt(new MoveAmount(0));
            List<Intersection> expectedPositions = createExpectedWingPositions(side, expectedRow);

            List<PieceType> expectedTypes = List.of(ELEPHANT, HORSE, ELEPHANT, HORSE);

            // when and then
            assertThat(expectedPositions)
                    .hasSize(expectedTypes.size())
                    .extracting(alivePieces::placedAt)
                    .zipSatisfy(expectedTypes, (piece, type) -> assertPiece(piece, type, side));
        }

        @ParameterizedTest(name = "{0} 진영의 상마마상 차림이 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 상마마상_차림_위치_검증(Side side) {
            // given
            Wings sangMaMaSang = new Wings(
                    side,
                    createWingPieces(side, ELEPHANT, HORSE),
                    createWingPieces(side, HORSE, ELEPHANT)
            );

            InitialPieces initialPieces = createInitialPieces(side, sangMaMaSang);
            AlivePieces alivePieces = initialPieces.toAlivePieces();

            int expectedRow = side.getRowAt(new MoveAmount(0));
            List<Intersection> expectedPositions = createExpectedWingPositions(side, expectedRow);

            List<PieceType> expectedTypes = List.of(ELEPHANT, HORSE, HORSE, ELEPHANT);

            // when and then
            assertThat(expectedPositions)
                    .hasSize(expectedTypes.size())
                    .extracting(alivePieces::placedAt)
                    .zipSatisfy(expectedTypes, (piece, type) -> assertPiece(piece, type, side));
        }

        @ParameterizedTest(name = "{0} 진영의 마상마상 차림이 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 마상마상_차림_위치_검증(Side side) {
            // given
            Wings MaSangMaSang = new Wings(
                    side,
                    createWingPieces(side, HORSE, ELEPHANT),
                    createWingPieces(side, HORSE, ELEPHANT)
            );

            InitialPieces initialPieces = createInitialPieces(side, MaSangMaSang);
            AlivePieces alivePieces = initialPieces.toAlivePieces();

            int expectedRow = side.getRowAt(new MoveAmount(0));
            List<Intersection> expectedPositions = createExpectedWingPositions(side, expectedRow);

            List<PieceType> expectedTypes = List.of(HORSE, ELEPHANT, HORSE, ELEPHANT);

            // when and then
            assertThat(expectedPositions)
                    .hasSize(expectedTypes.size())
                    .extracting(alivePieces::placedAt)
                    .zipSatisfy(expectedTypes, (piece, type) -> assertPiece(piece, type, side));
        }

        @ParameterizedTest(name = "{0} 진영의 마상상마 차림이 올바른 위치에 초기화된다")
        @EnumSource(value = Side.class, names = {"HAN", "CHO"})
        void 마상상마_차림_위치_검증(Side side) {
            // given
            Wings MaSangSangMa = new Wings(
                    side,
                    createWingPieces(side, HORSE, ELEPHANT),
                    createWingPieces(side, ELEPHANT, HORSE)
            );

            InitialPieces initialPieces = createInitialPieces(side, MaSangSangMa);
            AlivePieces alivePieces = initialPieces.toAlivePieces();

            int expectedRow = side.getRowAt(new MoveAmount(0));
            List<Intersection> expectedPositions = createExpectedWingPositions(side, expectedRow);

            List<PieceType> expectedTypes = List.of(HORSE, ELEPHANT, ELEPHANT, HORSE);

            // when and then
            assertThat(expectedPositions)
                    .hasSize(expectedTypes.size())
                    .extracting(alivePieces::placedAt)
                    .zipSatisfy(expectedTypes, (piece, type) -> assertPiece(piece, type, side));
        }

        private static List<Intersection> createExpectedWingPositions(Side side, int expectedRow) {
            return Stream.of(1, 2, 6, 7)
                    .map(expectedFile -> new Intersection(expectedRow, side.getFileAt(new MoveAmount(expectedFile))))
                    .toList();
        }
    }

    private WingPieces createWingPieces(Side side, PieceType first, PieceType second) {
        return new WingPieces(new Piece(first, side), new Piece(second, side));
    }

    private InitialPieces createInitialPieces(Side side, Wings targetWings) {
        if (side == Side.HAN) {
            return new InitialPieces(targetWings, DEFAULT_CHO_WINGS);
        }
        return new InitialPieces(DEFAULT_HAN_WINGS, targetWings);
    }

    private void assertPiece(Piece piece, PieceType type, Side side) {
        assertThat(piece.isSameType(type)).isTrue();
        assertThat(piece.isSameSide(side)).isTrue();
    }
}
