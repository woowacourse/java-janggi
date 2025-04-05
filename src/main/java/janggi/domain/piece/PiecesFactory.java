package janggi.domain.piece;

import static janggi.domain.board.BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
import static janggi.domain.board.BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
import static janggi.domain.board.BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;

import janggi.domain.board.Board;
import janggi.domain.board.BoardOrder;
import janggi.domain.players.Team;
import janggi.domain.piece.position.Position;
import java.util.HashMap;
import java.util.Map;

public class PiecesFactory {

    private static final int HAN_Y = 1;
    private static final int CHO_Y = 10;
    private static final int FIRST_CHANGE_X = 2;
    private static final int SECOND_CHANGE_X = 7;

    public Board initializeChoPieces(final BoardOrder choBoardOrder) {
        final Board choBoard = initializeChoPieces();
        changePieces(choBoardOrder, getYByTeam(Team.CHO), choBoard);
        return choBoard;
    }

    public Board initializeHanPieces(final BoardOrder hanBoardOrder) {
        final Board hanBoard = initializeHanPieces();
        changePieces(hanBoardOrder, getYByTeam(Team.HAN), hanBoard);
        return hanBoard;
    }

    private Board initializeChoPieces() {
        final Map<Position, Piece> board = new HashMap<>();
        board.put(
                new Position(CHO_Y, 1),
                Piece.CHARIOT
        );
        board.put(
                new Position(CHO_Y, 2),
                Piece.ELEPHANT
        );
        board.put(
                new Position(CHO_Y, 3),
                Piece.HORSE
        );
        board.put(
                new Position(CHO_Y, 4),
                Piece.GUARD
        );
        board.put(
                new Position(CHO_Y, 6),
                Piece.GUARD
        );
        board.put(
                new Position(CHO_Y, 7),
                Piece.ELEPHANT
        );
        board.put(
                new Position(CHO_Y, 8),
                Piece.HORSE
        );
        board.put(
                new Position(CHO_Y, 9),
                Piece.CHARIOT
        );
        board.put(
                new Position(9, 5),
                Piece.KING
        );

        board.put(
                new Position(8, 2),
                Piece.CANNON
        );
        board.put(
                new Position(8, 8),
                Piece.CANNON
        );

        board.put(
                new Position(SECOND_CHANGE_X, 1),
                Piece.CHO_SOLDIER
        );
        board.put(
                new Position(SECOND_CHANGE_X, 3),
                Piece.CHO_SOLDIER
        );
        board.put(
                new Position(SECOND_CHANGE_X, 5),
                Piece.CHO_SOLDIER
        );
        board.put(
                new Position(SECOND_CHANGE_X, 7),
                Piece.CHO_SOLDIER
        );
        board.put(
                new Position(SECOND_CHANGE_X, 9),
                Piece.CHO_SOLDIER
        );
        return new Board(board);
    }

    private Board initializeHanPieces() {
        final Map<Position, Piece> board = new HashMap<>();
        board.put(
                new Position(HAN_Y, 1),
                Piece.CHARIOT
        );
        board.put(
                new Position(HAN_Y, 2),
                Piece.ELEPHANT
        );
        board.put(
                new Position(HAN_Y, 3),
                Piece.HORSE
        );
        board.put(
                new Position(HAN_Y, 4),
                Piece.GUARD
        );
        board.put(
                new Position(HAN_Y, 6),
                Piece.GUARD
        );
        board.put(
                new Position(HAN_Y, 7),
                Piece.ELEPHANT
        );
        board.put(
                new Position(HAN_Y, 8),
                Piece.HORSE
        );
        board.put(
                new Position(HAN_Y, 9),
                Piece.CHARIOT
        );
        board.put(new Position(2, 5),
                Piece.KING
        );

        board.put(new Position(3, 2),
                Piece.CANNON
        );
        board.put(
                new Position(3, 8),
                Piece.CANNON
        );
        board.put(
                new Position(4, 1),
                Piece.HAN_SOLDIER
        );
        board.put(
                new Position(4, 3),
                Piece.HAN_SOLDIER
        );
        board.put(
                new Position(4, 5),
                Piece.HAN_SOLDIER
        );
        board.put(
                new Position(4, 7),
                Piece.HAN_SOLDIER
        );
        board.put(
                new Position(4, 9),
                Piece.HAN_SOLDIER
        );
        return new Board(board);
    }

    private int getYByTeam(final Team team) {
        if (team == Team.CHO) {
            return CHO_Y;
        }
        return HAN_Y;
    }

    private void changePieces(final BoardOrder order, final int y, final Board board) {
        if (order == HORSE_ELEPHANT_HORSE_ELEPHANT) {
            swapAdjacentPieces(board, y, FIRST_CHANGE_X);
            swapAdjacentPieces(board, y, SECOND_CHANGE_X);
        }
        if (order == ELEPHANT_HORSE_HORSE_ELEPHANT) {
            swapAdjacentPieces(board, y, SECOND_CHANGE_X);
        }
        if (order == HORSE_ELEPHANT_ELEPHANT_HORSE) {
            swapAdjacentPieces(board, y, FIRST_CHANGE_X);
        }
    }

    private void swapAdjacentPieces(final Board board, final int y, final int startX) {
        final Position firstPosition = new Position(y, startX);
        final Position secondPosition = new Position(y, startX + 1);

        board.swapPieces(firstPosition, secondPosition);
    }
}
