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

    public JanggiSaveService(MySQLConnection mySQConnection) {
        janggiTurnDao = new JanggiTurnDao(mySQConnection);
        janggiPieceDao = new JanggiPieceDao(mySQConnection);
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
