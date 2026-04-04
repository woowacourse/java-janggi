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
import domain.game.Side;
import domain.piece.AlivePieces;
import domain.piece.Piece;
import domain.piece.PieceType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("기물 초기화 검증")
class InitialPiecesTest {

    private static final Wings DEFAULT_HAN_WINGS = new Wings(
            HAN,
            new WingPieces(Piece.of(HORSE, HAN), Piece.of(ELEPHANT, HAN)),
            new WingPieces(Piece.of(HORSE, HAN), Piece.of(ELEPHANT, HAN))
    );
    private static final Wings DEFAULT_CHO_WINGS = new Wings(
            CHO,
            new WingPieces(Piece.of(HORSE, CHO), Piece.of(ELEPHANT, CHO)),
            new WingPieces(Piece.of(HORSE, CHO), Piece.of(ELEPHANT, CHO))
    );

    @DisplayName("초기화된 기물의 총 개수는 32개여야 한다")
    @Test
    void 초기화_된_기물의_개수_검증() {
        InitialPieces initialPieces = new InitialPieces(DEFAULT_HAN_WINGS, DEFAULT_CHO_WINGS);
        assertThat(initialPieces.size()).isEqualTo(32);
    }

    @DisplayName("한 진영 기물 배치 검증")
    @Nested
    class 한_진영_검증 {

        private AlivePieces alivePieces;

        @BeforeEach
        void setUp() {
            InitialPieces initialPieces = new InitialPieces(DEFAULT_HAN_WINGS, DEFAULT_CHO_WINGS);
            alivePieces = initialPieces.toAlivePieces();
        }

        @DisplayName("차는 1행 1열, 9열에 배치된다")
        @Test
        void 차_배치() {
            assertPieceAt(alivePieces, new Intersection(1, 1), CHARIOT, HAN);
            assertPieceAt(alivePieces, new Intersection(1, 9), CHARIOT, HAN);
        }

        @DisplayName("사는 1행 4열, 6열에 배치된다")
        @Test
        void 사_배치() {
            assertPieceAt(alivePieces, new Intersection(1, 4), GUARD, HAN);
            assertPieceAt(alivePieces, new Intersection(1, 6), GUARD, HAN);
        }

        @DisplayName("궁은 2행 5열에 배치된다")
        @Test
        void 궁_배치() {
            assertPieceAt(alivePieces, new Intersection(2, 5), GENERAL, HAN);
        }

        @DisplayName("포는 3행 2열, 8열에 배치된다")
        @Test
        void 포_배치() {
            assertPieceAt(alivePieces, new Intersection(3, 2), CANNON, HAN);
            assertPieceAt(alivePieces, new Intersection(3, 8), CANNON, HAN);
        }

        @DisplayName("병은 4행 1, 3, 5, 7, 9열에 배치된다")
        @Test
        void 병_배치() {
            for (int file : List.of(1, 3, 5, 7, 9)) {
                assertPieceAt(alivePieces, new Intersection(4, file), SOLDIER, HAN);
            }
        }
    }

    @DisplayName("초 진영 기물 배치 검증")
    @Nested
    class 초_진영_검증 {

        private AlivePieces alivePieces;

        @BeforeEach
        void setUp() {
            InitialPieces initialPieces = new InitialPieces(DEFAULT_HAN_WINGS, DEFAULT_CHO_WINGS);
            alivePieces = initialPieces.toAlivePieces();
        }

        @DisplayName("차는 10행 1열, 9열에 배치된다")
        @Test
        void 차_배치() {
            assertPieceAt(alivePieces, new Intersection(10, 1), CHARIOT, CHO);
            assertPieceAt(alivePieces, new Intersection(10, 9), CHARIOT, CHO);
        }

        @DisplayName("사는 10행 4열, 6열에 배치된다")
        @Test
        void 사_배치() {
            assertPieceAt(alivePieces, new Intersection(10, 4), GUARD, CHO);
            assertPieceAt(alivePieces, new Intersection(10, 6), GUARD, CHO);
        }

        @DisplayName("궁은 9행 5열에 배치된다")
        @Test
        void 궁_배치() {
            assertPieceAt(alivePieces, new Intersection(9, 5), GENERAL, CHO);
        }

        @DisplayName("포는 8행 2열, 8열에 배치된다")
        @Test
        void 포_배치() {
            assertPieceAt(alivePieces, new Intersection(8, 2), CANNON, CHO);
            assertPieceAt(alivePieces, new Intersection(8, 8), CANNON, CHO);
        }

        @DisplayName("졸은 7행 1, 3, 5, 7, 9열에 배치된다")
        @Test
        void 졸_배치() {
            for (int file : List.of(1, 3, 5, 7, 9)) {
                assertPieceAt(alivePieces, new Intersection(7, file), SOLDIER, CHO);
            }
        }
    }

    @DisplayName("상차림 위치 검증")
    @Nested
    class 상차림_검증 {

        @DisplayName("초 진영 상차림")
        @Nested
        class 초_진영_상차림 {

            @DisplayName("상마상마")
            @Test
            void 상마상마() {
                Wings wings = new Wings(
                        CHO,
                        createWingPieces(CHO, ELEPHANT, HORSE),
                        createWingPieces(CHO, ELEPHANT, HORSE)
                );
                AlivePieces alivePieces = new InitialPieces(DEFAULT_HAN_WINGS, wings).toAlivePieces();

                assertPieceAt(alivePieces, new Intersection(10, 2), ELEPHANT, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 3), HORSE, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 7), ELEPHANT, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 8), HORSE, CHO);
            }

            @DisplayName("상마마상")
            @Test
            void 상마마상() {
                Wings wings = new Wings(
                        CHO,
                        createWingPieces(CHO, ELEPHANT, HORSE),
                        createWingPieces(CHO, HORSE, ELEPHANT)
                );
                AlivePieces alivePieces = new InitialPieces(DEFAULT_HAN_WINGS, wings).toAlivePieces();

                assertPieceAt(alivePieces, new Intersection(10, 2), ELEPHANT, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 3), HORSE, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 7), HORSE, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 8), ELEPHANT, CHO);
            }

            @DisplayName("마상마상")
            @Test
            void 마상마상() {
                Wings wings = new Wings(
                        CHO,
                        createWingPieces(CHO, HORSE, ELEPHANT),
                        createWingPieces(CHO, HORSE, ELEPHANT)
                );
                AlivePieces alivePieces = new InitialPieces(DEFAULT_HAN_WINGS, wings).toAlivePieces();

                assertPieceAt(alivePieces, new Intersection(10, 2), HORSE, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 3), ELEPHANT, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 7), HORSE, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 8), ELEPHANT, CHO);
            }

            @DisplayName("마상상마")
            @Test
            void 마상상마() {
                Wings wings = new Wings(
                        CHO,
                        createWingPieces(CHO, HORSE, ELEPHANT),
                        createWingPieces(CHO, ELEPHANT, HORSE)
                );
                AlivePieces alivePieces = new InitialPieces(DEFAULT_HAN_WINGS, wings).toAlivePieces();

                assertPieceAt(alivePieces, new Intersection(10, 2), HORSE, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 3), ELEPHANT, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 7), ELEPHANT, CHO);
                assertPieceAt(alivePieces, new Intersection(10, 8), HORSE, CHO);
            }
        }

        @DisplayName("한 진영 상차림")
        @Nested
        class 한_진영_상차림 {

            @DisplayName("상마상마")
            @Test
            void 상마상마() {
                Wings wings = new Wings(
                        HAN,
                        createWingPieces(HAN, ELEPHANT, HORSE),
                        createWingPieces(HAN, ELEPHANT, HORSE)
                );
                AlivePieces alivePieces = new InitialPieces(wings, DEFAULT_CHO_WINGS).toAlivePieces();

                assertPieceAt(alivePieces, new Intersection(1, 8), ELEPHANT, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 7), HORSE, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 3), ELEPHANT, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 2), HORSE, HAN);
            }

            @Test
            @DisplayName("상마마상")
            void 상마마상() {
                Wings wings = new Wings(
                        HAN,
                        createWingPieces(HAN, ELEPHANT, HORSE),
                        createWingPieces(HAN, HORSE, ELEPHANT)
                );
                AlivePieces alivePieces = new InitialPieces(wings, DEFAULT_CHO_WINGS).toAlivePieces();

                assertPieceAt(alivePieces, new Intersection(1, 8), ELEPHANT, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 7), HORSE, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 3), HORSE, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 2), ELEPHANT, HAN);
            }

            @DisplayName("마상마상")
            @Test
            void 마상마상() {
                Wings wings = new Wings(
                        HAN,
                        createWingPieces(HAN, HORSE, ELEPHANT),
                        createWingPieces(HAN, HORSE, ELEPHANT)
                );
                AlivePieces alivePieces = new InitialPieces(wings, DEFAULT_CHO_WINGS).toAlivePieces();

                assertPieceAt(alivePieces, new Intersection(1, 8), HORSE, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 7), ELEPHANT, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 3), HORSE, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 2), ELEPHANT, HAN);
            }

            @DisplayName("마상상마")
            @Test
            void 마상상마() {
                Wings wings = new Wings(
                        HAN,
                        createWingPieces(HAN, HORSE, ELEPHANT),
                        createWingPieces(HAN, ELEPHANT, HORSE)
                );
                AlivePieces alivePieces = new InitialPieces(wings, DEFAULT_CHO_WINGS).toAlivePieces();

                assertPieceAt(alivePieces, new Intersection(1, 8), HORSE, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 7), ELEPHANT, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 3), ELEPHANT, HAN);
                assertPieceAt(alivePieces, new Intersection(1, 2), HORSE, HAN);
            }
        }
    }

    private void assertPieceAt(AlivePieces alivePieces, Intersection intersection, PieceType type, Side side) {
        Piece piece = alivePieces.placedAt(intersection);
        assertThat(piece.isSameType(type)).as("%s 위치의 기물 타입 확인", intersection).isTrue();
        assertThat(piece.isSameSide(side)).as("%s 위치의 기물 진영 확인", intersection).isTrue();
    }

    private WingPieces createWingPieces(Side side, PieceType first, PieceType second) {
        return new WingPieces(Piece.of(first, side), Piece.of(second, side));
    }
}
