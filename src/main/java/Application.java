import static player.Nation.CHO;
import static player.Nation.HAN;

import dao.GameStateDao;
import dao.JanggiGimulDao;
import java.util.Map;
import pieceProperty.Position;
import pieceProperty.JanggiPieceInitializer;
import player.JanggiPan;
import player.Nation;
import player.Player;
import player.Players;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {

        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();
        JanggiPan choPieces = janggiPieceInitializer.choInit();
        JanggiPan hanPieces = janggiPieceInitializer.hanInit();
        Player hanPlayer = new Player(hanPieces);
        Player choPlayer = new Player(choPieces);
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        JanggiGimulDao janggiGimulDao = new JanggiGimulDao();
        janggiGimulDao.getConnection();
        GameStateDao gameStateDao = new GameStateDao();

        if (inputView.getUserWantToPlayReset()) {
            janggiGimulDao.deleteAllPieces();
            janggiGimulDao.insertHanPieces(hanPieces);
            janggiGimulDao.insertChoPieces(choPieces);
            gameStateDao.deleteAllGameState();
            gameStateDao.insertGameState();
        }

        while (!janggiGameState.isGameOver()) {
            try{
                outputView.printScore(janggiGimulDao.selectAlivePiece("HAN").calculateSum(), janggiGimulDao.selectAlivePiece("CHO").calculateSum());
                outputView.printJanggiPan(janggiGimulDao.findHanAllGimul(), janggiGimulDao.findChoAllGimul());
                Position presentPosition = inputView.getPresentPosition(gameStateDao.getCurrentTurn());
                Position destination = inputView.getDestination();

                Nation attackNation = gameStateDao.getCurrentTurn();

                janggiGimulDao.updateAttackGimul(presentPosition, destination, attackNation);
                janggiGimulDao.updateDefenceGimul(destination, attackNation.getDefenseNation());
                janggiGameState.movePiece(attackNation, presentPosition, destination);
                gameStateDao.changeAttackNation(attackNation.getDefenseNation());

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }

        outputView.printResult(hanPlayer.isJanggunDie());
    }
}

