package infra.jdbc;

import domain.board.Board;
import domain.game.GameResult;
import domain.game.GameStatus;
import domain.game.JanggiGame;
import domain.pieces.Cha;
import domain.pieces.EmptyPiece;
import domain.pieces.Gung;
import domain.pieces.JolByeong;
import domain.pieces.Ma;
import domain.pieces.Piece;
import domain.pieces.PieceType;
import domain.pieces.Po;
import domain.pieces.Sa;
import domain.pieces.Sang;
import domain.pieces.Side;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class SavedGameReadMapper {

    public JanggiGame toJanggiGame(GameEntity gameEntity) {
        Board board = new Board(restoredPieces(gameEntity.pieces()));
        GameResult gameResult = toGameResult(gameEntity);
        return JanggiGame.restore(gameEntity.gameId(), board, gameEntity.currentTurn(), gameResult);
    }

    private Map<Position, Piece> restoredPieces(java.util.List<PieceEntity> pieceEntities) {
        Map<Position, Piece> pieces = emptyBoard();
        for (PieceEntity pieceEntity : pieceEntities) {
            Position position = new Position(pieceEntity.row(), pieceEntity.column());
            pieces.put(position, toPiece(pieceEntity));
        }
        return pieces;
    }

    private Map<Position, Piece> emptyBoard() {
        Map<Position, Piece> pieces = new HashMap<>();
        for (int row = 0; row <= 9; row++) {
            for (int column = 0; column <= 8; column++) {
                pieces.put(new Position(row, column), new EmptyPiece());
            }
        }
        return pieces;
    }

    private Piece toPiece(PieceEntity pieceEntity) {
        PieceType pieceType = pieceEntity.pieceType();
        Side side = pieceEntity.side();

        return switch (pieceType) {
            case CHA -> new Cha(side);
            case PO -> new Po(side);
            case MA -> new Ma(side);
            case SANG -> new Sang(side);
            case SA -> new Sa(side);
            case JOL_BYEONG -> new JolByeong(side);
            case GUNG -> new Gung(side);
            case EMPTY -> new EmptyPiece();
        };
    }

    private GameResult toGameResult(GameEntity gameEntity) {
        if (gameEntity.status() == GameStatus.ENDED) {
            return GameResult.ended(gameEntity.winner());
        }
        return GameResult.running();
    }
}
