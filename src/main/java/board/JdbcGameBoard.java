package board;

import dao.JdbcConnection;
import dao.PieceDao;
import dao.PlayerDao;
import dao.TurnDao;
import direction.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.Pieces;
import team.Player;
import team.Team;

public class JdbcGameBoard implements GameBoard {

    private final PieceDao pieceDao;
    private final PlayerDao playerDao;
    private final TurnDao turnDao;
    private List<Player> players = new ArrayList<>();

    public JdbcGameBoard(PieceDao pieceDao, PlayerDao playerDao, TurnDao turnDao) {
        this.pieceDao = pieceDao;
        this.playerDao = playerDao;
        this.turnDao = turnDao;
    }

    public boolean isGameExist() {
        return playerDao.countPlayer() > 0;
    }

    public void loadGame() {
        String playerQuery = "SELECT id, score, team FROM player";

        try (Connection connection = JdbcConnection.getConnection()) {
             PreparedStatement playerStmt = connection.prepareStatement(playerQuery);
             ResultSet playerResults = playerStmt.executeQuery();

            while (playerResults.next()) {
                int playerId = playerResults.getInt("id");
                int score = playerResults.getInt("score");
                Team team = Team.valueOf(playerResults.getString("team"));

                List<Piece> pieces = pieceDao.findPieces(playerId, team, connection);

                players.add(new Player(new Pieces(pieces), score, team));
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] 게임 데이터를 읽어오는데 실패했습니다.");
            e.printStackTrace();
        }
    }

    public void startNewGame(Team turn) {
        try (Connection connection = JdbcConnection.getConnection()) {
            connection.setAutoCommit(false);

            MemoryGameBoard memoryGameBoard = new MemoryGameBoard();
            players.add(memoryGameBoard.findPlayer(Team.CHO));
            players.add(memoryGameBoard.findPlayer(Team.HAN));

            playerDao.addPlayer(memoryGameBoard.findPlayer(Team.CHO));
            int cho = playerDao.getPlayerIdByTeam(Team.CHO);

            for (Piece piece : memoryGameBoard.findPlayer(Team.CHO).getPieces()) {
                pieceDao.savePiece(cho, piece.type(), piece.column(), piece.row());
            }

            playerDao.addPlayer(memoryGameBoard.findPlayer(Team.HAN));
            int han = playerDao.getPlayerIdByTeam(Team.HAN);

            for (Piece piece : memoryGameBoard.findPlayer(Team.HAN).getPieces()) {
                pieceDao.savePiece(han, piece.type(), piece.column(), piece.row());
            }

            turnDao.addTurn(turn);

            connection.commit();
            System.out.println("새 게임이 시작되었습니다!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveGame(Point start, Point end, Team turn) {
        try (Connection conn = JdbcConnection.getConnection()) {
            conn.setAutoCommit(false);
            playerDao.updatePlayer(conn, findPlayer(Team.HAN));

            for (Piece piece : findPlayer(Team.HAN).getPieces()) {
                int pieceId = pieceDao.getPieceIdByPoint(conn, start.column(), start.row());
                pieceDao.updatePiece(conn, pieceId, end.column(), end.row());
            }

            playerDao.updatePlayer(conn, findPlayer(Team.CHO));

            for (Piece piece : findPlayer(Team.CHO).getPieces()) {
                int pieceId = pieceDao.getPieceIdByPoint(conn, start.column(), start.row());
                pieceDao.updatePiece(conn, pieceId, end.column(), end.row());
            }

            turnDao.updateTurn(conn, turn);

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Team loadCurrentTurn() {
        return Team.valueOf(turnDao.getTurn());
    }

    @Override
    public Player findPlayer(Team team) {
        return players.stream()
                .filter(player -> player.isTeam(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 찾으려는 팀이 없습니다."));
    }

    @Override
    public Pieces findTeamPieces(Team team) {
        Player player = findPlayer(team);
        return new Pieces(player.getPieces());
    }

    @Override
    public Pieces findAllPieces() {
        List<Piece> pieces = new ArrayList<>();

        for (Player player : players) {
            pieces.addAll(player.getPieces());
        }

        return new Pieces(pieces);
    }
}
