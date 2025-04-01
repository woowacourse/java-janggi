package janggi.domain.piece.movement.palace;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Palace;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceMaker;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.normal.dynamic.RookMovementStrategy;
import janggi.domain.piece.pieces.Pieces;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class RookPalaceMovementStrategyTest {

    private static final RookPalaceMovementStrategy ROOK_PALACE_MOVEMENT_STRATEGY =
        new RookPalaceMovementStrategy(new RookMovementStrategy());
    private static final Position HAN_MIDDLE_MIDDLE_PALACE = Palace.HAN_MIDDLE_MIDDLE.getPosition();
    private static final Position CHO_MIDDLE_MIDDLE_PALACE = Palace.CHO_MIDDLE_MIDDLE.getPosition();

    private static Pieces makePieces(Piece... pieces) {
        return new Pieces(Arrays.stream(pieces).collect(Collectors.toMap(Piece::getPosition, Function.identity())));
    }

    private static Stream<Arguments> 궁성_안에서는_대각_이동을_할_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_LEFT.getPosition()),
                Palace.HAN_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_RIGHT.getPosition()),
                Palace.HAN_UP_LEFT.getPosition()
            ),
            Arguments.of(
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_LEFT.getPosition()),
                Palace.HAN_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_RIGHT.getPosition()),
                Palace.HAN_DOWN_LEFT.getPosition()
            ),

            Arguments.of(
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_LEFT.getPosition()),
                Palace.CHO_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_RIGHT.getPosition()),
                Palace.CHO_UP_LEFT.getPosition()
            ),
            Arguments.of(
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_LEFT.getPosition()),
                Palace.CHO_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_RIGHT.getPosition()),
                Palace.CHO_DOWN_LEFT.getPosition()
            )
        );
    }

    private static Stream<Arguments> 이동_중__다른_기물이_존재하면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, HAN_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_LEFT.getPosition()),
                Palace.HAN_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, HAN_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_RIGHT.getPosition()),
                Palace.HAN_UP_LEFT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, HAN_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_LEFT.getPosition()),
                Palace.HAN_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, HAN_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_RIGHT.getPosition()),
                Palace.HAN_DOWN_LEFT.getPosition()
            ),

            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, CHO_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_LEFT.getPosition()),
                Palace.CHO_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, CHO_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_RIGHT.getPosition()),
                Palace.CHO_UP_LEFT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, CHO_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_LEFT.getPosition()),
                Palace.CHO_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, CHO_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_RIGHT.getPosition()),
                Palace.CHO_DOWN_LEFT.getPosition()
            )
        );
    }

    private static Stream<Arguments> 목적지에_아군_기물이_있다면_이동할_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, Palace.HAN_UP_RIGHT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_LEFT.getPosition()),
                Palace.HAN_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, Palace.HAN_UP_LEFT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_RIGHT.getPosition()),
                Palace.HAN_UP_LEFT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, Palace.HAN_DOWN_RIGHT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_LEFT.getPosition()),
                Palace.HAN_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, Palace.HAN_DOWN_LEFT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_RIGHT.getPosition()),
                Palace.HAN_DOWN_LEFT.getPosition()
            ),

            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, Palace.CHO_UP_RIGHT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_LEFT.getPosition()),
                Palace.CHO_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, Palace.CHO_UP_LEFT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_RIGHT.getPosition()),
                Palace.CHO_UP_LEFT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, Palace.CHO_DOWN_RIGHT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_LEFT.getPosition()),
                Palace.CHO_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.HAN, Palace.CHO_DOWN_LEFT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_RIGHT.getPosition()),
                Palace.CHO_DOWN_LEFT.getPosition()
            )
        );
    }

    private static Stream<Arguments> 목적지에_적군_기물이_있다면_움직일_수_있다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.CHO, Palace.HAN_UP_RIGHT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_LEFT.getPosition()),
                Palace.HAN_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.CHO, Palace.HAN_UP_LEFT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_RIGHT.getPosition()),
                Palace.HAN_UP_LEFT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.CHO, Palace.HAN_DOWN_RIGHT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_LEFT.getPosition()),
                Palace.HAN_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.CHO, Palace.HAN_DOWN_LEFT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_RIGHT.getPosition()),
                Palace.HAN_DOWN_LEFT.getPosition()
            ),

            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.CHO, Palace.CHO_UP_RIGHT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_LEFT.getPosition()),
                Palace.CHO_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.CHO, Palace.CHO_UP_LEFT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_RIGHT.getPosition()),
                Palace.CHO_UP_LEFT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.CHO, Palace.CHO_DOWN_RIGHT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_LEFT.getPosition()),
                Palace.CHO_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.ROOK, Side.CHO, Palace.CHO_DOWN_LEFT.getPosition())
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_RIGHT.getPosition()),
                Palace.CHO_DOWN_LEFT.getPosition()
            )
        );
    }

    @ParameterizedTest
    @MethodSource("궁성_안에서는_대각_이동을_할_수_있다_테스트_케이스")
    void 룩은_궁성_내에서_대각_이동할_수_있다(Piece cannon, Position destination) {

        Pieces pieces = new Pieces(Map.of());

        assertThat(
            ROOK_PALACE_MOVEMENT_STRATEGY.isMovableInPalace(
                pieces,
                cannon.getPosition(),
                cannon.getSide(),
                destination)
        ).isTrue();
    }

    @ParameterizedTest
    @MethodSource("이동_중__다른_기물이_존재하면_이동할_수_없다_테스트_케이스")
    void 이동_중__다른_기물이_존재하면_이동할_수_없다(
        Pieces pieces,
        Piece cannon,
        Position destination
    ) {
        assertThat(
            ROOK_PALACE_MOVEMENT_STRATEGY.isMovableInPalace(
                pieces,
                cannon.getPosition(),
                cannon.getSide(),
                destination)
        ).isFalse();
    }

    @ParameterizedTest
    @MethodSource("목적지에_아군_기물이_있다면_이동할_수_없다_테스트_케이스")
    void 목적지에_아군_기물이_있다면_이동할_수_없다(
        Pieces pieces,
        Piece cannon,
        Position destination
    ) {
        assertThat(
            ROOK_PALACE_MOVEMENT_STRATEGY.isMovableInPalace(
                pieces,
                cannon.getPosition(),
                cannon.getSide(),
                destination)
        ).isFalse();
    }

    @ParameterizedTest
    @MethodSource("목적지에_적군_기물이_있다면_움직일_수_있다_테스트_케이스")
    void 목적지에_적군_기물이_있다면_움직일_수_있다(
        Pieces pieces,
        Piece cannon,
        Position destination
    ) {
        assertThat(
            ROOK_PALACE_MOVEMENT_STRATEGY.isMovableInPalace(
                pieces,
                cannon.getPosition(),
                cannon.getSide(),
                destination)
        ).isTrue();
    }
}
