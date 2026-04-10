package repository;

import domain.board.Board;
import domain.board.BoardPiece;
import domain.board.Position;
import domain.game.Game;
import domain.piece.Camp;
import domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMapper {

    public List<BoardPiece> toBoardPieces(Game game) {
        return game.board().pieces();
    }

    public Board toBoard(List<BoardPiece> boardPieces) {
        Map<Position, Piece> board = new HashMap<>();

        for (BoardPiece boardPiece : boardPieces) {
            board.put(
                    boardPiece.position(),
                    new Piece(boardPiece.camp(), boardPiece.pieceType())
            );
        }

        return new Board(board);
    }

    public Game toGame(Camp currentTurn, boolean finished, List<BoardPiece> boardPieces) {
        Board board = toBoard(boardPieces);

        return Game.restore(board, currentTurn, finished);
    }
}
