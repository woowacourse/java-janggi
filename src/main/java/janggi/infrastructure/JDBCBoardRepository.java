package janggi.infrastructure;

import janggi.domain.position.Position;
import janggi.domain.space.piece.Piece;
import janggi.domain.space.piece.PieceFactory;
import janggi.domain.space.piece.PieceType;
import janggi.domain.space.piece.Team;
import janggi.infrastructure.dao.GameDao;
import janggi.infrastructure.dto.PieceDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCBoardRepository implements BoardRepository {
    private final GameDao gameDAO;

    public JDBCBoardRepository() {
        this.gameDAO = new GameDao();
    }

    @Override
    public void saveGame(long gameId, String turn, Map<Position, Piece> arrivePieces) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            // 기존 기물 정보 삭제
            gameDAO.deletePieceByGameId(connection, gameId);
            // 현재 턴 저장
            gameDAO.saveGameList(connection, gameId, turn);
            // 현재 기물 상태 정보 저장
            for (Map.Entry<Position, Piece> entry : arrivePieces.entrySet()) {
                PieceDto pieceDto = new PieceDto(
                        entry.getKey().x(),
                        entry.getKey().y(),
                        entry.getValue().toString(),
                        entry.getValue().getTeam().toString()
                );
                gameDAO.insertPieces(connection, gameId, pieceDto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        }
    }

    public List<Long> findAllGameIds() {
        try (Connection connection = DatabaseConnector.getConnection()) {
            return gameDAO.findAllGameIds(connection);
        } catch (SQLException e) {
            throw new RuntimeException("게임 목록 조회 실패: " + e.getMessage());
        }
    }

    public Map<Position, Piece> findPiecesById(long gameId) {
        Map<Position, Piece> pieces = new HashMap<>();

        try (Connection connection = DatabaseConnector.getConnection()) {
            List<PieceDto> pieceDtos = gameDAO.findPiecesByGameId(connection, gameId);
            for (PieceDto pieceDto : pieceDtos) {
                Position pos = new Position(pieceDto.x(), pieceDto.y());
                Piece piece = PieceFactory.createPiece(
                        Team.from(pieceDto.team()),
                        PieceType.from(pieceDto.pieceType())
                );
                pieces.put(pos, piece);
            }
        } catch (SQLException e) {
            throw new RuntimeException("기물 정보 조회 실패: " + e.getMessage());
        }

        return pieces;
    }

    public String findTurnById(long gameId) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            return gameDAO.findTurnByGameId(connection, gameId);
        } catch (SQLException e) {
            throw new RuntimeException("턴 정보 조회 실패: " + e.getMessage());
        }
    }
}