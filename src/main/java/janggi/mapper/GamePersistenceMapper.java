package janggi.mapper;

import janggi.domain.Game;
import janggi.domain.PieceInfo;
import janggi.domain.Position;
import janggi.domain.ScoreStatus;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.dto.NewGameRoom;
import janggi.factory.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.Finish;
import janggi.domain.turn.HanTurn;
import janggi.domain.turn.PlayerTurn;
import janggi.dto.BoardPiece;
import janggi.dto.GameRoom;
import janggi.factory.BoardFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static janggi.domain.board.Board.ARRAY_INDEX_OFFSET;
import static janggi.domain.board.Board.BOARD_END_COLS;
import static janggi.domain.board.Board.BOARD_END_ROWS;

public class GamePersistenceMapper {
    private final PieceFactory pieceFactory;

    public GamePersistenceMapper(PieceFactory pieceFactory) {
        this.pieceFactory = pieceFactory;
    }

    public NewGameRoom toNewGameRoom(Game game) {
        ScoreStatus scoreStatus = game.getCurrentScoreStatus();

        return new NewGameRoom(
                game.getCurrentSide().name(),
                game.isFinished(),
                scoreStatus.choScore(),
                scoreStatus.hanScore()
        );
    }

    public GameRoom toGameRoom(long gameRoomId, Game game) {
        ScoreStatus scoreStatus = game.getCurrentScoreStatus();

        return new GameRoom(
                gameRoomId,
                game.getCurrentSide().name(),
                game.isFinished(),
                scoreStatus.choScore(),
                scoreStatus.hanScore()
        );
    }

    public List<BoardPiece> toBoardPieces(long gameRoomId, Game game) {
        List<BoardPiece> boardPieces = new ArrayList<>();
        PieceInfo[][] currentBoard = game.getCurrentBoard();

        for (int row = 0; row < BOARD_END_ROWS; row++) {
            for (int col = 0; col < BOARD_END_COLS; col++) {
                PieceInfo pieceInfo = currentBoard[row][col];
                if (pieceInfo.pieceType() == PieceType.NONE) {
                    continue;
                }
                boardPieces.add(toBoardPiece(gameRoomId, row, col, pieceInfo));
            }
        }
        return boardPieces;
    }

    private BoardPiece toBoardPiece(long gameRoomId, int row, int col, PieceInfo pieceInfo) {
        return new BoardPiece(
                gameRoomId,
                row + ARRAY_INDEX_OFFSET,
                col + ARRAY_INDEX_OFFSET,
                pieceInfo.pieceType().name(),
                pieceInfo.side().name()
        );
    }

    public Game restore(GameRoom gameRoom, List<BoardPiece> boardPieces) {
        Map<Position, Piece> restoredBoard = BoardFactory.createEmptyBoard(pieceFactory);
        restorePieces(restoredBoard, boardPieces);

        Board board = new Board(restoredBoard, restoreScores(gameRoom));
        PlayerTurn playerTurn = restorePlayerTurn(board, gameRoom);

        return new Game(playerTurn);
    }

    private void restorePieces(Map<Position, Piece> board, List<BoardPiece> boardPieces) {
        for (BoardPiece boardPiece : boardPieces) {
            Position position = new Position(boardPiece.rowPos(), boardPiece.colPos());
            Piece piece = pieceFactory.create(
                    PieceType.valueOf(boardPiece.pieceType()),
                    Side.valueOf(boardPiece.side())
            );
            board.put(position, piece);
        }
    }

    private Map<Side, Double> restoreScores(GameRoom gameRoom) {
        Map<Side, Double> restoredScoresBySide = new HashMap<>();
        restoredScoresBySide.put(Side.CHO, gameRoom.choScore());
        restoredScoresBySide.put(Side.HAN, gameRoom.hanScore());
        return restoredScoresBySide;
    }

    private PlayerTurn restorePlayerTurn(Board board, GameRoom gameRoom) {
        Side currentSide = Side.valueOf(gameRoom.turn());
        if (gameRoom.finished()) {
            return new Finish(board, currentSide);
        }
        if (currentSide == Side.CHO) {
            return new ChoTurn(board);
        }
        return new HanTurn(board);
    }
}