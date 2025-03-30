package janggi.dao;

import janggi.dto.GameDto;
import janggi.dto.PieceDtos;
import janggi.dto.PieceDtos.AttackedPieceDto;
import janggi.dto.PieceDtos.RunningPieceDto;
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

    private final int id;
    private Piece piece;

    public PieceDao(int id, Piece piece) {
        this.id = id;
        this.piece = piece;
    }

    public static PieceDao createPiece(Piece piece, GameDao gameDao) {
        final var createQuery = "INSERT INTO piece (name,is_running,row_index,column_index,team,game_id) VALUES(?,?,?,?,?,?)";
        final var checkQuery = "SELECT * FROM piece WHERE game_id=? AND row_index=? AND column_index=?";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedCreateStatement = connection.prepareStatement(createQuery);
             final var preparedCheckStatement = connection.prepareStatement(checkQuery)) {
            preparedCreateStatement.setString(1, piece.getType().name());
            preparedCreateStatement.setBoolean(2, true); //TINYINT : 1
            preparedCreateStatement.setInt(3, piece.getPoint().row());
            preparedCreateStatement.setInt(4, piece.getPoint().column());
            preparedCreateStatement.setString(5, piece.getTeam().name());
            preparedCreateStatement.setInt(6, gameDao.getId());
            preparedCreateStatement.executeUpdate();

            preparedCheckStatement.setInt(1, gameDao.getId());
            preparedCheckStatement.setInt(2, piece.getPoint().row());
            preparedCheckStatement.setInt(3, piece.getPoint().column());
            ResultSet resultSet = preparedCheckStatement.executeQuery();
            if (resultSet.next()) {
                return new PieceDao(resultSet.getInt("id"), piece);
            }
            throw new IllegalStateException("기물이 생성되지 않았습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static PieceDtos findPiecesBy(GameDto gameDto) {
        final var pieceQuery = "SELECT * FROM piece WHERE game_id=?"; //TODO 조인?
        try (final var connection = JangiDatabase.getConnection();
             final var preparedPieceStatement = connection.prepareStatement(pieceQuery)) {
            preparedPieceStatement.setInt(1, gameDto.id());
            final var pieceResultSet = preparedPieceStatement.executeQuery();

            Map<Integer, Piece> runningPieces = new HashMap<>();
            Map<Integer, AttackedPiece> attackedPieces = new HashMap<>();
            while (pieceResultSet.next()) {
                int pieceId = pieceResultSet.getInt("id");
                PieceInformation information = PieceInformation.valueOf(pieceResultSet.getString("name"));
                boolean isRunning = pieceResultSet.getBoolean("is_running");
                int rowIndex = pieceResultSet.getInt("row_index");
                int columnIndex = pieceResultSet.getInt("column_index");
                Team team = Team.valueOf(pieceResultSet.getString("team"));

                Piece piece = createBy(information, rowIndex, columnIndex, team);
                if (isRunning) {
                    runningPieces.put(pieceId, piece);
                } else {
                    attackedPieces.put(pieceId, new AttackedPiece(piece));
                }
            }
            return PieceDtos.from(runningPieces, attackedPieces);
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePiecePoint(Piece newPiece) {
        final var query = "UPDATE piece SET row_index=?, column_index=? WHERE team=? ";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newPiece.getPoint().row());
            preparedStatement.setInt(2, newPiece.getPoint().column());
            preparedStatement.setString(3, newPiece.getTeam().name());
            preparedStatement.executeUpdate();

            this.piece = newPiece;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateToAttacked() {
        final var query = "UPDATE piece SET is_running=? WHERE id=? ";
        try (final var connection = JangiDatabase.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, this.id);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PieceDao> createNewPiecesFrom(PieceDtos pieceDtos) {
        List<PieceDao> pieceDaos = new ArrayList<>();

        for (RunningPieceDto running : pieceDtos.runningPieces()) {
            pieceDaos.add(new PieceDao(running.id(), running.piece()));
        }
        for (AttackedPieceDto attacked : pieceDtos.attackedPieces()) {
            pieceDaos.add(new PieceDao(attacked.id(), attacked.getPieceValue()));
        }

        return pieceDaos;
    }

    private static Piece createBy(PieceInformation information, int rowIndex, int columnIndex, Team team) {
        return information.createPiece(team, rowIndex, columnIndex);
    }

    public Piece getPiece() {
        return piece;
    }
}
