package service;

import domain.board.Board;
import domain.board.BoardFactory;
import dto.PieceSnapshot;
import repository.JanggiRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JanggiService {

    private final JanggiRepository janggiRepository;


    public JanggiService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;

    }

    public void setUp(Connection connection) throws SQLException {
        janggiRepository.createTable(connection);
    }

    public Board getBoard(Connection connection, int id) throws SQLException {
        return BoardFactory.from(janggiRepository.findPiecesByGameId(connection, id));
    }

    public void save(Connection connection, int gameId, List<PieceSnapshot> pieceSnapshots) throws SQLException {
        janggiRepository.save(connection, gameId, pieceSnapshots);
    }
}
