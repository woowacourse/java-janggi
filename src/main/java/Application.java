import static player.Nation.CHO;
import static player.Nation.HAN;

import Dao.GameStateDao;
import Dao.JanggiGimulDao;
import java.util.Map;
import pieceProperty.Position;
import pieceProperty.JanggiPieceInitializer;
import player.JanggiPan;
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
        gameStateDao.insertGameState();

        while (!janggiGameState.isGameOver()) {
            try{
                outputView.printJanggiPan(janggiGimulDao.findHanAllGimul(), janggiGimulDao.findChoAllGimul());
                Position presentPosition = inputView.getPresentPosition(gameStateDao.getCurrentTurn());
                Position destination = inputView.getDestination();

                if (janggiGameState.getAttackNation().equals(HAN)) {
                    janggiGimulDao.updateAttackGimul(presentPosition, destination,  1);
                    janggiGimulDao.updateDefenceGimul(destination, 2);
                    gameStateDao.changeAttackNation(janggiGameState.getAttackNation().getDefenseNation());
                }

                if (janggiGameState.getAttackNation().equals(CHO)) {
                    janggiGimulDao.updateAttackGimul(presentPosition, destination, 2);
                    janggiGimulDao.updateDefenceGimul(destination, 1);
                    gameStateDao.changeAttackNation(janggiGameState.getAttackNation().getDefenseNation());
                }

                janggiGameState.movePiece(presentPosition, destination);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }

        outputView.printResult(hanPlayer.isJanggunDie());
    }
}

