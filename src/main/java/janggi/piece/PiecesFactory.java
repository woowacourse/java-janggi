package janggi.piece;

import static janggi.board.BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
import static janggi.board.BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
import static janggi.board.BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;

import janggi.board.BoardOrder;
import janggi.direction.PieceMovement;
import janggi.position.Position;
import janggi.strategy.JumpingStrategy;
import janggi.strategy.WalkingStrategy;
import java.util.HashSet;
import java.util.Set;

public class PiecesFactory {

    private static final int HAN_Y = 1;
    private static final int CHO_Y = 10;
    private static final int FIRST_CHANGE_X = 2;
    private static final int SECOND_CHANGE_X = 7;

    public Pieces makeChoPieces(final BoardOrder choBoardOrder) {
        final Pieces choPieces = makeChoPieces();
        changePieces(choBoardOrder, getYByTeam(Team.CHO), choPieces);
        return choPieces;
    }

    public Pieces makeHanPieces(final BoardOrder hanBoardOrder) {
        final Pieces hanPieces = makeHanPieces();
        changePieces(hanBoardOrder, getYByTeam(Team.HAN), hanPieces);
        return hanPieces;
    }

    private Pieces makeChoPieces() {
        final Set<Piece> choPieces = new HashSet<>();
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.CHARIOT), new Position(CHO_Y, 1)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.ELEPHANT), new Position(CHO_Y, 2)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.HORSE), new Position(CHO_Y, 3)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.GUARD), new Position(CHO_Y, 4)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.GUARD), new Position(CHO_Y, 6)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.ELEPHANT), new Position(CHO_Y, 7)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.HORSE), new Position(CHO_Y, 8)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.CHARIOT), new Position(CHO_Y, 9)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.KING), new Position(9, 5)));

        choPieces.add(new Piece(new JumpingStrategy(PieceMovement.CANNON), new Position(8, 2)));
        choPieces.add(new Piece(new JumpingStrategy(PieceMovement.CANNON), new Position(8, 8)));

        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), new Position(SECOND_CHANGE_X, 1)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), new Position(SECOND_CHANGE_X, 3)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), new Position(SECOND_CHANGE_X, 5)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), new Position(SECOND_CHANGE_X, 7)));
        choPieces.add(new Piece(new WalkingStrategy(PieceMovement.CHO_SOLDIER), new Position(SECOND_CHANGE_X, 9)));
        return Pieces.from(choPieces);
    }

    private Pieces makeHanPieces() {
        final Set<Piece> pieces = new HashSet<>();
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.CHARIOT), new Position(HAN_Y, 1)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.ELEPHANT), new Position(HAN_Y, 2)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.HORSE), new Position(HAN_Y, 3)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.GUARD), new Position(HAN_Y, 4)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.GUARD), new Position(HAN_Y, 6)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.ELEPHANT), new Position(HAN_Y, 7)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.HORSE), new Position(HAN_Y, 8)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.CHARIOT), new Position(HAN_Y, 9)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.KING), new Position(2, 5)));

        pieces.add(new Piece(new JumpingStrategy(PieceMovement.CANNON), new Position(3, 2)));
        pieces.add(new Piece(new JumpingStrategy(PieceMovement.CANNON), new Position(3, 8)));

        pieces.add(new Piece(new WalkingStrategy(PieceMovement.HAN_SOLDIER), new Position(4, 1)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.HAN_SOLDIER), new Position(4, 3)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.HAN_SOLDIER), new Position(4, 5)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.HAN_SOLDIER), new Position(4, 7)));
        pieces.add(new Piece(new WalkingStrategy(PieceMovement.HAN_SOLDIER), new Position(4, 9)));
        return Pieces.from(pieces);
    }

    private int getYByTeam(final Team team) {
        if (team == Team.CHO) {
            return CHO_Y;
        }
        return HAN_Y;
    }

    private void changePieces(final BoardOrder order, final int y, final Pieces pieces) {
        if (order == HORSE_ELEPHANT_HORSE_ELEPHANT) {
            swapAdjacentPieces(pieces, y, FIRST_CHANGE_X);
            swapAdjacentPieces(pieces, y, SECOND_CHANGE_X);
        }
        if (order == ELEPHANT_HORSE_HORSE_ELEPHANT) {
            swapAdjacentPieces(pieces, y, SECOND_CHANGE_X);
        }
        if (order == HORSE_ELEPHANT_ELEPHANT_HORSE) {
            swapAdjacentPieces(pieces, y, FIRST_CHANGE_X);
        }
    }

    private void swapAdjacentPieces(final Pieces pieces, final int y, final int startX) {
        final Position firstPosition = new Position(y, startX);
        final Position secondPosition = new Position(y, startX + 1);

        pieces.swapPieces(firstPosition, secondPosition);
    }
}
