package service;

import domain.board.Board;
import domain.board.BoardFactory;
import dto.PieceDto;
import dto.PieceSnapshot;
import repository.BoardRepository;

import java.sql.Connection;
import java.util.List;

public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void setUp(Connection connection) {
        boardRepository.createTable(connection);
    }

    public Board getBoard(Connection connection, int id) {
        return BoardFactory.from(boardRepository.findPiecesByGameId(connection, id));
    }

    public void save(Connection connection, int gameId, List<PieceSnapshot> pieceSnapshots) {
        boardRepository.save(connection, gameId, pieceSnapshots);
    }

    public void update(Connection connection, int gameId, List<Integer> from, List<Integer> to) {
        PieceDto piece = boardRepository.findPieceByPosition(connection, gameId, from);
        boardRepository.updateFrom(connection, gameId, from);
        boardRepository.updateTo(connection, gameId, to, piece.pieceType(), piece.team());
    }
}
