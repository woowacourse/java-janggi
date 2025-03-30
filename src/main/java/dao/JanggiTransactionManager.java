package dao;

import dao.dto.CreatePieceDto;
import domain.board.BoardLocation;
import domain.game.JanggiGame;
import domain.piece.Piece;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JanggiTransactionManager implements TransactionManager<JanggiGame> {

    private final DataBaseConnector dataBaseConnector;
    private final JanggiGameDao janggiGameDao;
    private final PieceDao pieceDao;

    public JanggiTransactionManager(DataBaseConnector dataBaseConnector, JanggiGameDao janggiGameDao, PieceDao pieceDao) {
        this.dataBaseConnector = dataBaseConnector;
        this.janggiGameDao = janggiGameDao;
        this.pieceDao = pieceDao;
    }

    public void createTable() {
        try (Connection connection = dataBaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            try {
                janggiGameDao.createTable(connection);
                pieceDao.createTable(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("[ERROR] 테이블 생성 중 오류 발생하였습니다");
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생하였습니다");
        }
    }

    public void create(JanggiGame janggiGame) {
        Team startingTurn = janggiGame.getTurn().getTeam();
        Map<BoardLocation, Piece> pieces = janggiGame.getBoard().getPieces();
        try (Connection connection = dataBaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            try {
                Long janggiGameId = janggiGameDao.create(connection, startingTurn);
                pieceDao.createAll(connection, mapToPiecesDto(pieces, janggiGameId));
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("[ERROR] 테이블 생성 중 오류 발생하였습니다");
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생하였습니다");
        }
    }

    public void update(JanggiGame janggiGame) {

    }

    public Optional<JanggiGame> find() {
        return Optional.empty();
    }

    private List<CreatePieceDto> mapToPiecesDto(Map<BoardLocation, Piece> pieces, Long janggiGameId) {
        return pieces.entrySet().stream()
                .map(entry -> CreatePieceDto.of(entry.getKey(), entry.getValue(), janggiGameId))
                .toList();
    }
}
