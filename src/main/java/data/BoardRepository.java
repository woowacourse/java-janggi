package data;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Camp;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardRepository {
    private final PieceDao pieceDao = new PieceDao();
    private final BoardDao boardDao = new BoardDao();

    public void save(Connection connection, Board board) {
        Map<Position, Piece> pieces = board.pieces();

        Long boardId;
        if (board.id() == null) {
            boardId = boardDao.insertBoard(connection, board.isGameInProgress(), board.turn().name());
            board.assignId(boardId);
        } else {
            boardId = board.id();
            boardDao.updateBoard(connection, boardId, board.isGameInProgress(), board.turn().name());
            pieceDao.deleteAllByBoard(connection, boardId);
        }

        pieces.forEach((position, piece) ->
                pieceDao.insertPiece(connection, boardId, piece.pieceType().name(),
                        piece.camp().name(), position.column(), position.row())
        );
    }

    public Board findById(Connection connection, Long boardId) {
        BoardDto boardDto = boardDao.getBoard(connection, boardId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 장기판입니다."));
        List<PieceDto> piecesInfo = pieceDao.getAllPieceByBoard(connection, boardId);

        Map<Position, Piece> pieces = new HashMap<>();
        for (PieceDto piece : piecesInfo) {
            Camp camp = piece.camp();
            PieceType pieceType = piece.type();
            pieces.put(new Position(piece.column(), piece.row()),
                    Piece.of(camp, pieceType));
        }

        return new Board(pieces, boardDto.gameInProgress(), boardDto.turn());
    }

    public List<BoardDto> findAll(Connection connection) {
        return boardDao.getAllBoards(connection);
    }

    public void delete(Connection connection, Board board) {
        Long boardId = board.id();

        if (boardId == null) {
            throw new IllegalStateException("존재하지 않는 게임입니다.");
        }

        pieceDao.deleteAllByBoard(connection, boardId);
        boardDao.deleteBoard(connection, boardId);
    }
}
