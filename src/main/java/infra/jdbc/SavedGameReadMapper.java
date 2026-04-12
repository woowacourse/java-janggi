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

    public JanggiGame toJanggiGame(SavedGameDto savedGameDto) {
        Board board = new Board(restoredPieces(savedGameDto.pieces()));
        GameResult gameResult = toGameResult(savedGameDto);
        return JanggiGame.restore(board, savedGameDto.currentTurn(), gameResult);
    }

    private Map<Position, Piece> restoredPieces(java.util.List<SavedPieceDto> savedPieceDtos) {
        Map<Position, Piece> pieces = emptyBoard();
        for (SavedPieceDto savedPieceDto : savedPieceDtos) {
            Position position = new Position(savedPieceDto.row(), savedPieceDto.column());
            pieces.put(position, toPiece(savedPieceDto));
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

    private Piece toPiece(SavedPieceDto savedPieceDto) {
        PieceType pieceType = savedPieceDto.pieceType();
        Side side = savedPieceDto.side();

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

    private GameResult toGameResult(SavedGameDto savedGameDto) {
        if (savedGameDto.status() == GameStatus.ENDED) {
            return GameResult.ended(savedGameDto.winner());
        }
        return GameResult.running();
    }
}
