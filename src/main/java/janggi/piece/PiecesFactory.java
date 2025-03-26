package janggi.piece;

import static janggi.board.BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
import static janggi.board.BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
import static janggi.board.BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;

import janggi.board.BoardOrder;
import janggi.position.Position;
import java.util.HashSet;
import java.util.Set;

public class PiecesFactory {

    private static final int HAN_Y = 1;
    private static final int CHO_Y = 10;
    private static final int FIRST_CHANGE_X = 2;
    private static final int SECOND_CHANGE_X = 7;

    public Pieces makePiecesByOrder(final BoardOrder choBoardOrder, final BoardOrder hanBoardOrder) {
        final Pieces pieces = makeBasicPieces();
        changePieces(choBoardOrder, getYByTeam(Team.CHO), pieces);
        changePieces(hanBoardOrder, getYByTeam(Team.HAN), pieces);
        return pieces;
    }

    private Pieces makeBasicPieces() {
        final Pieces pieces = makeChoPieces();
        pieces.addAll(makeHanPieces());
        return pieces;
    }

    private Pieces makeChoPieces() {
        final Set<Piece> pieces = new HashSet<>();
        pieces.add(new Chariot(Team.CHO, new Position(CHO_Y, 1)));
        pieces.add(new Elephant(Team.CHO, new Position(CHO_Y, 2)));
        pieces.add(new Horse(Team.CHO, new Position(CHO_Y, 3)));
        pieces.add(new Guard(Team.CHO, new Position(CHO_Y, 4)));
        pieces.add(new Guard(Team.CHO, new Position(CHO_Y, 6)));
        pieces.add(new Elephant(Team.CHO, new Position(CHO_Y, 7)));
        pieces.add(new Horse(Team.CHO, new Position(CHO_Y, 8)));
        pieces.add(new Chariot(Team.CHO, new Position(CHO_Y, 9)));
        pieces.add(new King(Team.CHO, new Position(9, 5)));
        pieces.add(new Cannon(Team.CHO, new Position(8, 2)));
        pieces.add(new Cannon(Team.CHO, new Position(8, 8)));
        pieces.add(new Soldier(Team.CHO, new Position(SECOND_CHANGE_X, 1)));
        pieces.add(new Soldier(Team.CHO, new Position(SECOND_CHANGE_X, 3)));
        pieces.add(new Soldier(Team.CHO, new Position(SECOND_CHANGE_X, 5)));
        pieces.add(new Soldier(Team.CHO, new Position(SECOND_CHANGE_X, 7)));
        pieces.add(new Soldier(Team.CHO, new Position(SECOND_CHANGE_X, 9)));
        return new Pieces(pieces);
    }

    private Pieces makeHanPieces() {
        final Set<Piece> pieces = new HashSet<>();
        pieces.add(new Chariot(Team.HAN, new Position(HAN_Y, 1)));
        pieces.add(new Elephant(Team.HAN, new Position(HAN_Y, 2)));
        pieces.add(new Horse(Team.HAN, new Position(HAN_Y, 3)));
        pieces.add(new Guard(Team.HAN, new Position(HAN_Y, 4)));
        pieces.add(new Guard(Team.HAN, new Position(HAN_Y, 6)));
        pieces.add(new Elephant(Team.HAN, new Position(HAN_Y, 7)));
        pieces.add(new Horse(Team.HAN, new Position(HAN_Y, 8)));
        pieces.add(new Chariot(Team.HAN, new Position(HAN_Y, 9)));
        pieces.add(new King(Team.HAN, new Position(2, 5)));
        pieces.add(new Cannon(Team.HAN, new Position(3, 2)));
        pieces.add(new Cannon(Team.HAN, new Position(3, 8)));
        pieces.add(new Soldier(Team.HAN, new Position(4, 1)));
        pieces.add(new Soldier(Team.HAN, new Position(4, 3)));
        pieces.add(new Soldier(Team.HAN, new Position(4, 5)));
        pieces.add(new Soldier(Team.HAN, new Position(4, 7)));
        pieces.add(new Soldier(Team.HAN, new Position(4, 9)));
        return new Pieces(pieces);
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
        final Piece firstPiece = pieces.findPieceByPosition(firstPosition);
        final Position secondPosition = new Position(y, startX + 1);
        final Piece secondPiece = pieces.findPieceByPosition(secondPosition);
        firstPiece.updatePosition(secondPosition);
        secondPiece.updatePosition(firstPosition);
    }
}
