package service;

import domain.board.Board;
import domain.board.BoardFactory;
import domain.coordination.Coordination;
import domain.piece.Piece;
import domain.piece.PieceFactory;
import dto.BoardRowDetail;
import dto.BoardRowDetails;
import repository.BoardRepository;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void setUp(Connection connection) {
        boardRepository.createTable(connection);
    }

    public Board getBoard(Connection connection, int id) {
        List<BoardRowDetail> boardRowDetails = boardRepository.findPiecesByGameId(connection, id);
        Map<Coordination, Piece> board = new HashMap<>();
        for (BoardRowDetail boardRowDetail : boardRowDetails) {
            Coordination coordination = Coordination.of(boardRowDetail.column(), boardRowDetail.row());
            Piece piece = PieceFactory.create(boardRowDetail.pieceType(), boardRowDetail.team());
            board.put(coordination, piece);
        }
        return BoardFactory.restore(board);
    }

    public void save(Connection connection, int gameId, BoardRowDetails boardRowDetails) {
        boardRepository.save(connection, gameId, boardRowDetails);
    }

    public void update(Connection connection, int gameId, List<Integer> from, List<Integer> to) {
        BoardRowDetail piece = boardRepository.findPieceByPosition(connection, gameId, from);
        boardRepository.updateFrom(connection, gameId, from);
        boardRepository.updateTo(connection, gameId, to, piece.pieceType(), piece.team());
    }
}
