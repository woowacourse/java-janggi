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
import java.util.HashSet;
import java.util.Set;

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
        final Set<Piece> choPieces = new HashSet<>();
        choPieces.add(
                new Piece(new PieceMoveRule(PieceType.CHARIOT, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(CHO_Y, 1)));
        choPieces.add(
                new Piece(new PieceMoveRule(PieceType.ELEPHANT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(CHO_Y, 2)));
        choPieces.add(
                new Piece(new PieceMoveRule(PieceType.HORSE, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(CHO_Y, 3)));
        choPieces.add(
                new Piece(new PieceMoveRule(PieceType.GUARD, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(CHO_Y, 4)));
        choPieces.add(
                new Piece(new PieceMoveRule(PieceType.GUARD, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(CHO_Y, 6)));
        choPieces.add(
                new Piece(new PieceMoveRule(PieceType.ELEPHANT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(CHO_Y, 7)));
        choPieces.add(
                new Piece(new PieceMoveRule(PieceType.HORSE, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(CHO_Y, 8)));
        choPieces.add(
                new Piece(new PieceMoveRule(PieceType.CHARIOT, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(CHO_Y, 9)));
        choPieces.add(
                new Piece(new PieceMoveRule(PieceType.KING, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(9, 5)));

        choPieces.add(
                new Piece(
                        new PieceMoveRule(PieceType.CANNON, new RelativeMoveStrategy(), new ObstacleJumpingObstacle()),
                        new Position(8, 2)));
        choPieces.add(
                new Piece(
                        new PieceMoveRule(PieceType.CANNON, new RelativeMoveStrategy(), new ObstacleJumpingObstacle()),
                        new Position(8, 8)));

        choPieces.add(new Piece(
                new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                new Position(SECOND_CHANGE_X, 1)));
        choPieces.add(new Piece(
                new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                new Position(SECOND_CHANGE_X, 3)));
        choPieces.add(new Piece(
                new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                new Position(SECOND_CHANGE_X, 5)));
        choPieces.add(new Piece(
                new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                new Position(SECOND_CHANGE_X, 7)));
        choPieces.add(new Piece(
                new PieceMoveRule(PieceType.CHO_SOLDIER, new RelativeMoveStrategy(), new ObstacleBlockStrategy()),
                new Position(SECOND_CHANGE_X, 9)));
        return Board.from(choPieces);
    }

    private Board initializeHanPieces() {
        final Set<Piece> pieces = new HashSet<>();
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.CHARIOT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(HAN_Y, 1)));
        pieces.add(new Piece(new PieceMoveRule(PieceType.ELEPHANT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                new Position(HAN_Y, 2)));
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.HORSE, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(HAN_Y, 3)));
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.GUARD, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(HAN_Y, 4)));
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.GUARD, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(HAN_Y, 6)));
        pieces.add(new Piece(new PieceMoveRule(PieceType.ELEPHANT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                new Position(HAN_Y, 7)));
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.HORSE, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(HAN_Y, 8)));
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.CHARIOT, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(HAN_Y, 9)));
        pieces.add(new Piece(new PieceMoveRule(PieceType.KING, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                new Position(2, 5)));

        pieces.add(new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), new ObstacleJumpingObstacle()),
                new Position(3, 2)));
        pieces.add(new Piece(new PieceMoveRule(PieceType.CANNON, new EdgeMoveStrategy(), new ObstacleJumpingObstacle()),
                new Position(3, 8)));

        pieces.add(
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(4, 1)));
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(4, 3)));
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(4, 5)));
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(4, 7)));
        pieces.add(
                new Piece(new PieceMoveRule(PieceType.HAN_SOLDIER, new EdgeMoveStrategy(), new ObstacleBlockStrategy()),
                        new Position(4, 9)));
        return Board.from(pieces);
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
