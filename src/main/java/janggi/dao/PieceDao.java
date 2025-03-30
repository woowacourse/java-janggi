package janggi.dao;

import janggi.dto.GameDto;
import janggi.dto.PieceDto;
import janggi.dto.PiecesOnBoardDto;
import janggi.entity.GameEntity;
import janggi.entity.PieceEntity;
import janggi.game.Game;
import janggi.game.Team;
import janggi.movement.target.AttackedPiece;
import janggi.piece.Piece;
import janggi.piece.PieceInformation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceDao {

    public void createPiece(Piece piece, Game game) {
        final var createQuery = "INSERT INTO piece (name,is_running,row_index,column_index,team,game_id) VALUES(?,?,?,?,?,?)";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedCreateStatement = connection.prepareStatement(createQuery)) {
            GameEntity gameEntity = GameEntity.findByGame(game);
            preparedCreateStatement.setString(1, piece.getType().name());
            preparedCreateStatement.setBoolean(2, true);
            preparedCreateStatement.setInt(3, piece.getPoint().row());
            preparedCreateStatement.setInt(4, piece.getPoint().column());
            preparedCreateStatement.setString(5, piece.getTeam().name());
            preparedCreateStatement.setInt(6, gameEntity.getId());
            preparedCreateStatement.executeUpdate();

            PieceEntity.addRecord(findCreatedPieceId(piece, gameEntity), piece);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findCreatedPieceId(Piece piece, GameEntity gameEntity) {
        final var checkQuery = "SELECT * FROM piece WHERE game_id=? AND row_index=? AND column_index=?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedCheckStatement = connection.prepareStatement(checkQuery)) {
            preparedCheckStatement.setInt(1, gameEntity.getId());
            preparedCheckStatement.setInt(2, piece.getPoint().row());
            preparedCheckStatement.setInt(3, piece.getPoint().column());
            ResultSet resultSet = preparedCheckStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new IllegalStateException("기물이 생성되지 않았습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PiecesOnBoardDto findPieceDataBy(GameDto gameDto) {
        final var pieceQuery = "SELECT * FROM piece WHERE game_id=?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedPieceStatement = connection.prepareStatement(pieceQuery)) {
            preparedPieceStatement.setInt(1, gameDto.id());
            final var pieceResultSet = preparedPieceStatement.executeQuery();

            List<PieceDto> pieceDtos = new ArrayList<>();
            while (pieceResultSet.next()) {
                pieceDtos.add(new PieceDto(
                        pieceResultSet.getInt("id"),
                        PieceInformation.valueOf(pieceResultSet.getString("name")),
                        Team.valueOf(pieceResultSet.getString("team")),
                        pieceResultSet.getInt("row_index"),
                        pieceResultSet.getInt("column_index"),
                        pieceResultSet.getBoolean("is_running")
                ));
            }
            return createBoardDtoFrom(pieceDtos);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PiecesOnBoardDto createBoardDtoFrom(List<PieceDto> pieceDtos) {
        Map<Integer, Piece> runningPieces = new HashMap<>();
        Map<Integer, AttackedPiece> attackedPieces = new HashMap<>();
        for (PieceDto dto : pieceDtos) {
            Piece piece = dto.information().createPiece(
                    dto.team(), dto.rowIndex(), dto.columnIndex());
            if (dto.isRunning()) {
                runningPieces.put(dto.pieceId(), piece);
            } else {
                attackedPieces.put(dto.pieceId(), new AttackedPiece(piece));
            }
        }
        return PiecesOnBoardDto.from(runningPieces, attackedPieces);
    }

    public void updatePointFrom(Piece movingPiece, Piece newPiece) {
        final var query = "UPDATE piece SET row_index=?, column_index=? WHERE id=? ";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            PieceEntity pieceEntity = PieceEntity.findByPiece(movingPiece);
            preparedStatement.setInt(1, newPiece.getPoint().row());
            preparedStatement.setInt(2, newPiece.getPoint().column());
            preparedStatement.setInt(3, pieceEntity.getId());
            preparedStatement.executeUpdate();

            pieceEntity.updatePiece(newPiece);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateToAttacked(AttackedPiece piece) {
        final var query = "UPDATE piece SET is_running=? WHERE id=? ";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            PieceEntity pieceEntity = PieceEntity.findByAttackedPiece(piece);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, pieceEntity.getId());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
