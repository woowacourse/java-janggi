package save;

import java.util.Map;
import java.util.Optional;
import piece.Piece;
import piece.Pieces;
import piece.player.PlayerPieces;
import piece.player.Team;

public class JanggiPersistenceService {

    private final JanggiTurnDao janggiTurnDao;
    private final JanggiPieceDao janggiPieceDao;
    private final String PIECES_DOESNT_EXIST = "피스 정보가 존재하지 않습니다";

    public JanggiPersistenceService(DatabaseConnection databaseConnection) {
        janggiTurnDao = new JanggiTurnDao(databaseConnection);
        janggiPieceDao = new JanggiPieceDao(databaseConnection);
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
        Optional<Integer> previousLatestTurnId = janggiTurnDao.getLatestTurnId();
        int previousTurnId = previousLatestTurnId.orElseThrow(() -> new SaveFailException(PIECES_DOESNT_EXIST));
        return janggiPieceDao.findPiecesByTeamTurn(previousTurnId);
    }

    public void resetJanggi() {
        janggiPieceDao.deleteAll();
        janggiTurnDao.deleteAll();
    }

    public boolean isPreviousGameExist() {
        Optional<Integer> previousGameTurn = janggiTurnDao.getLatestTurnId();
        return previousGameTurn.isPresent();
    }

    public Optional<Integer> getPreviousTurn() {
        return janggiTurnDao.getLatestTurn();
    }
}
