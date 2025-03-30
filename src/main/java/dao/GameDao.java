package dao;

import domain.Player;
import domain.Team;
import domain.board.Board;
import domain.board.BoardPoint;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.Elephant;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Horse;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import dto.MovementResponseDto;
import dto.SwitchPlayerTurnRequestDto;
import execptions.JanggiArgumentException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GameDao {
    private static final String SERVER = "localhost:13306";
    private static final String DATABASE = "janggi";
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public java.sql.Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Board getBoard() {
        final var query =
                "SELECT * FROM board " +
                        "INNER JOIN piece ON board.piece_id = piece.id " +
                        "INNER JOIN team ON piece.team_id = team.id";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            Map<BoardPoint, Piece> locations = new HashMap<>();

            while (resultSet.next()) {
                BoardPoint boardPoint = new BoardPoint(
                        Integer.parseInt(resultSet.getString("row_index")),
                        Integer.parseInt(resultSet.getString("column_index"))
                );

                String type = resultSet.getString("type");
                String teamName = resultSet.getString("name");

                Team team = teamName.equals("HAN") ? Team.HAN : Team.CHO;
                Piece piece = getPieceByType(type, team);

                locations.put(boardPoint, piece);
            }
            return new Board(locations);

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Piece getPieceByType(String type, Team team) {
        return switch (type) {
            case "Soldier" -> new Soldier(team);
            case "Elephant" -> new Elephant(team);
            case "Horse" -> new Horse(team);
            case "Cannon" -> new Cannon(team);
            case "Chariot" -> new Chariot(team);
            case "General" -> new General(team);
            case "Guard" -> new Guard(team);
            default -> throw new JanggiArgumentException("타입에 해당하는 기물이 존재하지 않습니다.");
        };
    }

    public List<Player> getPlayers() {
        final var query =
                "SELECT * FROM player " +
                        "INNER JOIN team ON team.id = player.team_id ";

        try (final var connection = getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Player> players = new ArrayList<>();

            while (resultSet.next()) {
                String teamName = resultSet.getString("name");
                Team team = teamName.equals("HAN") ? Team.HAN : Team.CHO;

                players.add(new Player(team));
            }
            return players;

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSwitchedTurn(final List<SwitchPlayerTurnRequestDto> requestDtos) {
        final var query =
                "UPDATE player " +
                        "SET is_turn = ? " +
                        "WHERE team_id = (" +
                        "SELECT id FROM team WHERE name = ?)";

        try (final var connection = getConnection();
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

        try (final var connection = getConnection();
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

        try (final var connection = getConnection();
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

        try (final var connection = getConnection();
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

        try (final var connection = getConnection();
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

        try (final var connection = getConnection();
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
