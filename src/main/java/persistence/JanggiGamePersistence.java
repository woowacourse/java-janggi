package persistence;

import java.util.Map;
import java.util.Optional;
import piece.Piece;
import piece.Pieces;
import piece.player.PlayerPieces;
import piece.player.Team;

public class JanggiGamePersistence {

    private final JanggiTurnDao janggiTurnDao;
    private final JanggiPieceDao janggiPieceDao;
    private final String PIECES_DOESNT_EXIST = "피스 정보가 존재하지 않습니다";

    public JanggiGamePersistence(DatabaseConnection databaseConnection) {
        janggiTurnDao = new MySQLJanggiTurnDao(databaseConnection);
        janggiPieceDao = new MySQLJanggiPieceDao(databaseConnection);
    }

    public JanggiGamePersistence(JanggiTurnDao janggiTurnDao, JanggiPieceDao janggiPieceDao) {
        this.janggiTurnDao = janggiTurnDao;
        this.janggiPieceDao = janggiPieceDao;
    }

    public void saveJanggi(PlayerPieces playerPieces, int turn, Team team) {
        Pieces pieces = playerPieces.allPieces();
        Map<Team, Integer> playerScores = playerPieces.getPlayerScores();
        int score = playerScores.get(team);

        janggiTurnDao.addTurnScore(team, turn, score);

        for (Piece piece : pieces.getPieces()) {
            janggiPieceDao.savePiece(piece, turn);
        }
    }

    public Pieces loadPieces() {
        Optional<Integer> previousLatestTurnId = janggiTurnDao.findLatestTurnId();
        int previousTurnId = previousLatestTurnId.orElseThrow(() -> new PersistenceFailException(PIECES_DOESNT_EXIST));
        return janggiPieceDao.findPiecesByTeamTurn(previousTurnId);
    }

    public void resetJanggi() {
        janggiPieceDao.deleteAll();
        janggiTurnDao.deleteAll();
    }

    public boolean isPreviousGameExist() {
        Optional<Integer> previousGameTurn = janggiTurnDao.findLatestTurnId();
        return previousGameTurn.isPresent();
    }

    public Optional<Integer> findPreviousTurn() {
        return janggiTurnDao.findLatestTurn();
    }
}
