package dao;

import dto.MovementResponseDto;
import dto.SwitchPlayerTurnRequestDto;
import entity.PieceEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public final class PieceDao {

    private final JanggiConnection janggiConnection;

    public PieceDao(JanggiConnection janggiConnection) {
        this.janggiConnection = janggiConnection;
    }

    public PieceEntity findById(final long findId) {
        final var query = "SELECT * FROM piece WHERE id = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, findId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                long teamId = resultSet.getLong("team_id");
                String type = resultSet.getString("type");

                return new PieceEntity(id, teamId, type);
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void saveSwitchedTurn(final List<SwitchPlayerTurnRequestDto> requestDtos) {
        final var query =
                "UPDATE player " +
                        "SET is_turn = ? " +
                        "WHERE team_id = (" +
                        "SELECT id FROM team WHERE name = ?)";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            for (SwitchPlayerTurnRequestDto requestDto : requestDtos) {
                preparedStatement.setString(1, requestDto.team().name());
                preparedStatement.setString(2, String.valueOf(requestDto.isTurn()));
                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMovementResult(final MovementResponseDto movementResponseDto) {
        String pieceIdAtStartBoardPoint = getPieceIdAtStartBoardPoint(movementResponseDto);
        if (pieceIdAtStartBoardPoint != null) {
            deleteAtStartBoardPoint(movementResponseDto);
        }

        boolean pieceExistedAtArrivalPoint = isPieceExistedAtArrivalPoint(movementResponseDto);

        if (pieceExistedAtArrivalPoint) {
            updatePieceOnBoard(movementResponseDto, pieceIdAtStartBoardPoint);
            return;
        }

        insertNewPieceOnBoard(movementResponseDto, pieceIdAtStartBoardPoint);
    }

    private void insertNewPieceOnBoard(MovementResponseDto movementResponseDto, String pieceIdAtStartBoardPoint) {
        final var query = "INSERT INTO board (piece_id, row_index, column_index) "
                + "VALUES (?, ?, ?);";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, String.valueOf(pieceIdAtStartBoardPoint));
            preparedStatement.setString(2, String.valueOf(movementResponseDto.arrivalBoardPoint().row()));
            preparedStatement.setString(3, String.valueOf(movementResponseDto.arrivalBoardPoint().column()));
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePieceOnBoard(MovementResponseDto movementResponseDto, String pieceIdAtStartBoardPoint) {
        final var query = "UPDATE board SET piece_id = ? WHERE row_index = ? and column_index = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, String.valueOf(pieceIdAtStartBoardPoint));
            preparedStatement.setString(2, String.valueOf(movementResponseDto.arrivalBoardPoint().row()));
            preparedStatement.setString(3, String.valueOf(movementResponseDto.arrivalBoardPoint().column()));
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isPieceExistedAtArrivalPoint(MovementResponseDto movementResponseDto) {
        final var query =
                "SELECT * FROM board " +
                        "WHERE row_index = ? AND column_index = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, movementResponseDto.arrivalBoardPoint().row());
            preparedStatement.setInt(2, movementResponseDto.arrivalBoardPoint().column());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteAtStartBoardPoint(MovementResponseDto movementResponseDto) {
        final var query =
                "DELETE FROM board " +
                        "WHERE board.row_index = ? AND board.column_index = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, String.valueOf(movementResponseDto.startBoardPoint().row()));
            preparedStatement.setString(2, String.valueOf(movementResponseDto.startBoardPoint().column()));

            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPieceIdAtStartBoardPoint(MovementResponseDto movementResponseDto) {
        final var query =
                "SELECT * FROM board " +
                        "WHERE board.row_index = ? AND board.column_index = ?";

        try (final var connection = janggiConnection.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, String.valueOf(movementResponseDto.startBoardPoint().row()));
            preparedStatement.setString(2, String.valueOf(movementResponseDto.startBoardPoint().column()));

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("piece_id");
            }
            return null;

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
