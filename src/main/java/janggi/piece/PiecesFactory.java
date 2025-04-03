package janggi.piece;

import static janggi.piece.board.BoardOrder.ELEPHANT_HORSE_HORSE_ELEPHANT;
import static janggi.piece.board.BoardOrder.HORSE_ELEPHANT_ELEPHANT_HORSE;
import static janggi.piece.board.BoardOrder.HORSE_ELEPHANT_HORSE_ELEPHANT;

import janggi.direction.PieceMoveRule;
import janggi.direction.PieceType;
import janggi.direction.move.EdgeMoveStrategy;
import janggi.direction.move.RelativeMoveStrategy;
import janggi.direction.obstacle.ObstacleBlockStrategy;
import janggi.direction.obstacle.ObstacleJumpingObstacle;
import janggi.piece.board.Board;
import janggi.piece.board.BoardOrder;
import janggi.piece.players.Team;
import janggi.position.Position;
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
                new Piece(new PieceMoveRule(PieceType.CHARIOT, new RelativeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(CHO_Y, 2),
                new Piece(new PieceMoveRule(PieceType.ELEPHANT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(CHO_Y, 3),
                new Piece(new PieceMoveRule(PieceType.HORSE, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(CHO_Y, 4),
                new Piece(
                        new PieceMoveRule(PieceType.GUARD, new RelativeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(CHO_Y, 6),
                new Piece(new PieceMoveRule(PieceType.GUARD, new RelativeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(CHO_Y, 7),
                new Piece(new PieceMoveRule(PieceType.ELEPHANT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(CHO_Y, 8),
                new Piece(new PieceMoveRule(PieceType.HORSE, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(CHO_Y, 9),
                new Piece(new PieceMoveRule(PieceType.CHARIOT, new RelativeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(9, 5),
                new Piece(new PieceMoveRule(PieceType.KING, new RelativeMoveStrategy(), new ObstacleBlockStrategy()))
        );

        board.put(
                new Position(8, 2),
                new Piece(
                        new PieceMoveRule(PieceType.CANNON, new RelativeMoveStrategy(), new ObstacleJumpingObstacle()))
        );
        board.put(
                new Position(8, 8),
                new Piece(
                        new PieceMoveRule(PieceType.CANNON, new RelativeMoveStrategy(), new ObstacleJumpingObstacle()))
        );

        board.put(
                new Position(SECOND_CHANGE_X, 1),
                new Piece(new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(),
                        new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(SECOND_CHANGE_X, 3),
                new Piece(new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(),
                        new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(SECOND_CHANGE_X, 5),
                new Piece(new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(),
                        new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(SECOND_CHANGE_X, 7),
                new Piece(new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(),
                        new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(SECOND_CHANGE_X, 9),
                new Piece(new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(),
                        new ObstacleBlockStrategy()))
        );
        return new Board(board);
    }

    private Board initializeHanPieces() {
        final Map<Position, Piece> board = new HashMap<>();
        board.put(
                new Position(HAN_Y, 1),
                new Piece(new PieceMoveRule(PieceType.CHARIOT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(HAN_Y, 2),
                new Piece(new PieceMoveRule(PieceType.ELEPHANT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(HAN_Y, 3),
                new Piece(new PieceMoveRule(PieceType.HORSE, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(HAN_Y, 4),
                new Piece(new PieceMoveRule(PieceType.GUARD, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(HAN_Y, 6),
                new Piece(new PieceMoveRule(PieceType.GUARD, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(HAN_Y, 7),
                new Piece(new PieceMoveRule(PieceType.ELEPHANT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(HAN_Y, 8),
                new Piece(new PieceMoveRule(PieceType.HORSE, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(HAN_Y, 9),
                new Piece(new PieceMoveRule(PieceType.CHARIOT, new EdgeMoveStrategy(), new ObstacleBlockStrategy())));
        board.put(new Position(2, 5),
                new Piece(new PieceMoveRule(PieceType.KING, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );

        board.put(new Position(3, 2),
                new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), new ObstacleJumpingObstacle()))
        );
        board.put(
                new Position(3, 8),
                new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), new ObstacleJumpingObstacle()))
        );
        board.put(
                new Position(4, 1),
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(4, 3),
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(4, 5),
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(4, 7),
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
        );
        board.put(
                new Position(4, 9),
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()))
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
