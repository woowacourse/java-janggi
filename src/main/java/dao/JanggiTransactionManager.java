package dao;

import dao.dto.CreatePieceDto;
import domain.board.Board;
import domain.board.BoardLocation;
import domain.entity.JanggiGameEntity;
import domain.entity.PieceEntity;
import domain.game.JanggiGame;
import domain.game.Turn;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.Scholar;
import domain.piece.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
                throw new RuntimeException("[ERROR] 엔터티 생성 중 오류 발생하였습니다");
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생하였습니다");
        }
    }

    public void update(Long id, JanggiGame janggiGame) {

    }

    public Optional<JanggiGame> findById(Long id) {
        try (Connection connection = dataBaseConnector.getConnection()) {
            connection.setAutoCommit(false);

            try {
                JanggiGameEntity janggiGameEntity = janggiGameDao.findById(connection, id)
                        .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 id로 장기 게임을 찾지 못했습니다."));
                List<PieceEntity> pieceEntities = pieceDao.findAllByJanggiGameId(connection, id);
                connection.commit();
                JanggiGame janggiGame = mapToJanggiGame(janggiGameEntity, pieceEntities);
                return Optional.of(janggiGame);
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("[ERROR] 게임 조회 중 오류 발생하였습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("[ERROR] DB 연결 중 오류 발생하였습니다");
        }
    }

    private JanggiGame mapToJanggiGame(JanggiGameEntity janggiGameEntity, List<PieceEntity> pieceEntities) {
        Turn turn = janggiGameEntity.getTurn();
        Board board = mapToBoard(pieceEntities);
        return new JanggiGame(board, turn);
    }

    private Board mapToBoard(List<PieceEntity> pieceEntities) {
        Map<BoardLocation, Piece> pieces = pieceEntities.stream()
                .collect(Collectors.toMap(
                        entity -> new BoardLocation(entity.getX(), entity.getY()),
                        this::createPiece
                ));
        return new Board(pieces);
    }

    private Piece createPiece(PieceEntity entity) {
        return switch (entity.getType()) {
            case CANNON -> new Cannon(entity.getTeam());
            case HORSE -> new Horse(entity.getTeam());
            case CHARIOT -> new Chariot(entity.getTeam());
            case ELEPHANT -> new Elephant(entity.getTeam());
            case KING -> King.createByTeam(entity.getTeam());
            case PAWN -> new Pawn(entity.getTeam());
            case SCHOLAR -> new Scholar(entity.getTeam());
        };
    }

    private List<CreatePieceDto> mapToPiecesDto(Map<BoardLocation, Piece> pieces, Long janggiGameId) {
        return pieces.entrySet().stream()
                .map(entry -> CreatePieceDto.of(entry.getKey(), entry.getValue(), janggiGameId))
                .toList();
    }
}
