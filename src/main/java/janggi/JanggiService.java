package janggi;

import janggi.dao.GameRoom;
import janggi.dao.Piece;
import janggi.db.SQLManager;
import janggi.domain.PieceInitInfo;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.piece.PieceType;
import janggi.dto.GameDto;
import janggi.dto.GameResponseDto;
import janggi.dto.PieceDto;
import janggi.dto.TurnDto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class JanggiService {
    private final SQLManager sqlManager;
    private final GameRoom gameRoom;
    private final Piece piece;

    public JanggiService(SQLManager sqlManager, GameRoom gameRoom, Piece piece) {
        this.sqlManager = sqlManager;
        this.gameRoom = gameRoom;
        this.piece = piece;
    }

    public List<GameResponseDto> getEntireGame() {
        gameRoom.initTable();
        return gameRoom.findAllGames();
    }

    public int addGameData(GameDto gameDto, List<PieceDto> pieceDtos) {
        Connection connection = sqlManager.ensureConnection();

        try {
            int gameId = gameRoom.insertGame(connection, gameDto);
            piece.updatePieces(connection, gameId, pieceDtos);

            connection.commit();
            return gameId;
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("게임 데이터 생성 중 오류가 발생하여 롤백되었습니다.", e);
        }
    }

    public void removeGame(int id) {
        Connection connection = sqlManager.ensureConnection();
        try {
            gameRoom.removeGame(connection, id);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("게임 데이터 삭제 중 오류가 발생하여 롤백되었습니다.", e);
        }
    }

    public List<PieceInitInfo> getPieceInitInfos(int gameId) {
        return piece.getAllPieces(gameId).stream()
                .map(pieceDto -> new PieceInitInfo(new Position(pieceDto.x(), pieceDto.y()), Side.from(pieceDto.side()), PieceType.from(pieceDto.pieceType())))
                .toList();
    }

    public void movePiece(int gameId, Position start, Position end, Side side, PieceType pieceType, TurnDto turnDto) {
        Connection connection = sqlManager.ensureConnection();
        PieceDto pieceDto = new PieceDto(end.getX(), end.getY(), side.name(), pieceType.name());
        try {
            gameRoom.updateGameTurn(connection, gameId, turnDto);
            piece.deletePiece(connection, gameId, start.getX(), start.getY());
            piece.updatePiece(connection, gameId, pieceDto);

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException("기물 이동 중 오류가 발생하여 롤백되었습니다.", e);
        }
    }
}
