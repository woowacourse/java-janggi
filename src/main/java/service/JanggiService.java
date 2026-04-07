package service;

import domain.board.Board;
import domain.board.BoardFactory;
import dto.PieceDto;
import dto.PieceSnapshot;
import repository.JanggiRepository;

import java.sql.Connection;
import java.util.List;

public class JanggiService {

    private final JanggiRepository janggiRepository;

    public JanggiService(JanggiRepository janggiRepository) {
        this.janggiRepository = janggiRepository;
    }

    public void setUp(Connection connection) {
        janggiRepository.createTable(connection);
    }

    public Board getBoard(Connection connection, int id) {
        return BoardFactory.from(janggiRepository.findPiecesByGameId(connection, id));
    }

    public void save(Connection connection, int gameId, List<PieceSnapshot> pieceSnapshots) {
        janggiRepository.save(connection, gameId, pieceSnapshots);
    }

    public void update(Connection connection, int gameId, List<Integer> from, List<Integer> to) {
        PieceDto piece = janggiRepository.findPieceByPosition(connection, gameId, from);
        janggiRepository.updateFrom(connection, gameId, from);
        janggiRepository.updateTo(connection, gameId, to, piece.pieceType(), piece.team());
    }
}
