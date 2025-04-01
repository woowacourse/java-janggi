package janggi.domain.piece.movement.palace;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Palace;
import janggi.domain.ReplaceUnderBar;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceMaker;
import janggi.domain.piece.PieceType;
import janggi.domain.piece.Position;
import janggi.domain.piece.Side;
import janggi.domain.piece.movement.normal.dynamic.CannonMovementStrategy;
import janggi.domain.piece.pieces.Pieces;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@ReplaceUnderBar
class CannonPalaceMovementStrategyTest {

    private static final CannonPalaceMovementStrategy CANNON_PALACE_MOVEMENT_STRATEGY =
        new CannonPalaceMovementStrategy(new CannonMovementStrategy());
    private static final Position HAN_MIDDLE_MIDDLE_PALACE = Palace.HAN_MIDDLE_MIDDLE.getPosition();
    private static final Position CHO_MIDDLE_MIDDLE_PALACE = Palace.CHO_MIDDLE_MIDDLE.getPosition();

    private static Pieces makePieces(Piece... pieces) {
        return new Pieces(Arrays.stream(pieces).collect(Collectors.toMap(Piece::getPosition, Function.identity())));
    }

    private static Stream<Arguments> 궁성_안에서는_대각_이동을_할_수_있다_테스트_케이스() {
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

    private static Stream<Arguments> 궁성_안에서는_Cannon_을_뛰어넘을_수_없다_테스트_케이스() {
        return Stream.of(
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.CANNON, Side.HAN, HAN_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_LEFT.getPosition()),
                Palace.HAN_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.CANNON, Side.HAN, HAN_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_DOWN_RIGHT.getPosition()),
                Palace.HAN_UP_LEFT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.CANNON, Side.HAN, HAN_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_LEFT.getPosition()),
                Palace.HAN_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.CANNON, Side.HAN, HAN_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.HAN_UP_RIGHT.getPosition()),
                Palace.HAN_DOWN_LEFT.getPosition()
            ),

            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.CANNON, Side.HAN, CHO_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_LEFT.getPosition()),
                Palace.CHO_UP_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.CANNON, Side.HAN, CHO_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_DOWN_RIGHT.getPosition()),
                Palace.CHO_UP_LEFT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.CANNON, Side.HAN, CHO_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_LEFT.getPosition()),
                Palace.CHO_DOWN_RIGHT.getPosition()
            ),
            Arguments.of(
                makePieces(
                    PieceMaker.createPiece(PieceType.CANNON, Side.HAN, CHO_MIDDLE_MIDDLE_PALACE)
                ),
                PieceMaker.createPiece(PieceType.CANNON, Side.HAN, Palace.CHO_UP_RIGHT.getPosition()),
                Palace.CHO_DOWN_LEFT.getPosition()
            )
        );
    }

    @ParameterizedTest
    @MethodSource("궁성_안에서는_대각_이동을_할_수_있다_테스트_케이스")
    void 궁성_안에서는_대각_이동을_할_수_있다(Pieces pieces, Piece cannon, Position destination) {
        assertThat(
            CANNON_PALACE_MOVEMENT_STRATEGY.isMovableInPalace(
                pieces,
                cannon.getPosition(),
                cannon.getSide(),
                destination
            )
        ).isTrue();
    }

    @ParameterizedTest
    @MethodSource("궁성_안에서는_Cannon_을_뛰어넘을_수_없다_테스트_케이스")
    void 궁성_안에서는_Cannon_을_뛰어넘을_수_없다(Pieces pieces, Piece cannon, Position destination) {
        assertThat(
            CANNON_PALACE_MOVEMENT_STRATEGY.isMovableInPalace(
                pieces,
                cannon.getPosition(),
                cannon.getSide(),
                destination
            )
        ).isFalse();
    }
}
