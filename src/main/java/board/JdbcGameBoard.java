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
                double score = playerResults.getDouble("score");
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
        PiecesInitializer piecesInitializer = new PiecesInitializer();
        List<Piece> choPieces = piecesInitializer.makeChoPieces();
        List<Piece> hanPieces = piecesInitializer.makeHanPieces();

        Player choPlayer = new Player(new Pieces(choPieces), 0, Team.CHO);
        Player hanPlayer = new Player(new Pieces(hanPieces), 1.5, Team.HAN);
        players.add(choPlayer);
        players.add(hanPlayer);

        int choPlayerId = playerDao.addPlayer(choPlayer);
        for (Piece piece : choPlayer.getPieces()) {
            pieceDao.savePiece(choPlayerId, piece.type(), piece.column(), piece.row());
        }

        int hanPlayerId = playerDao.addPlayer(hanPlayer);
        for (Piece piece : hanPlayer.getPieces()) {
            pieceDao.savePiece(hanPlayerId, piece.type(), piece.column(), piece.row());
        }

        turnDao.addTurn(turn);
    }

    @Override
    public void saveGame(Point start, Point end, Team turn) {
        try (Connection conn = JdbcConnection.getConnection()) {
            conn.setAutoCommit(false);

            Player hanPlayer = findPlayer(Team.HAN);
            playerDao.updatePlayer(conn, hanPlayer);

            for (Piece piece : hanPlayer.getPieces()) {
                int pieceId = pieceDao.getPieceIdByPoint(conn, start.column(), start.row());
                pieceDao.updatePiece(conn, pieceId, end.column(), end.row());
            }

            Player choPlayer = findPlayer(Team.CHO);
            playerDao.updatePlayer(conn, choPlayer);

            for (Piece piece : choPlayer.getPieces()) {
                int pieceId = pieceDao.getPieceIdByPoint(conn, start.column(), start.row());
                pieceDao.updatePiece(conn, pieceId, end.column(), end.row());
            }

            turnDao.updateTurn(conn, turn);

            conn.commit();
        } catch (SQLException e) {
            System.err.println("[ERROR] 게임을 저장하는데 실패했습니다.");
            e.printStackTrace();
        }
    }

    public Team loadCurrentTurn() {
        return Team.valueOf(turnDao.getTurn());
    }

    @Override
    public void resetGame() {
        pieceDao.removeAll();
        playerDao.removeAll();
        turnDao.removeAll();
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
