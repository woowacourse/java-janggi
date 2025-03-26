package save;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import piece.Piece;
import piece.Pieces;
import piece.player.PlayerPieces;
import piece.player.Team;

public class JanggiSaveService {

    private final JanggiTurnDao janggiTurnDao;
    private final JanggiPieceDao janggiPieceDao;

    public JanggiSaveService(MySQLConnection mySQLConnection) {
        janggiTurnDao = new JanggiTurnDao(mySQLConnection);
        janggiPieceDao = new JanggiPieceDao(mySQLConnection);
    }

    public void saveJanggi(PlayerPieces playerPieces, int turn, Team team) {
        Pieces pieces = playerPieces.allPieces();
        Map<Team, Integer> playerScores = playerPieces.getPlayerScores();
        int score = playerScores.get(team);

        deletePreviousJanggiState(team, turn);
        janggiTurnDao.addTurnScore(team, turn, score);

        for (Piece piece : pieces.getPieces()) {
            janggiPieceDao.savePiece(piece, turn);
        }
    }

    private void deletePreviousJanggiState(Team team, int turn) {
        janggiPieceDao.deletePreviousTurnScore(team, turn);
    }

    public Pieces loadPieces() {
        Optional<Integer> previousTurnId = janggiTurnDao.getLatestTurnId();
        if (previousTurnId.isEmpty()) {
            return new Pieces(List.of());
        }
        return janggiPieceDao.findPiecesByTeamTurn(previousTurnId.get());
    }

    public void initiateJanggi() {
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
