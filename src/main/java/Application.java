import static player.Nation.CHO;
import static player.Nation.HAN;

import java.util.Map;
import pieceProperty.Position;
import pieceProperty.JanggiPieceInitializer;
import player.Pieces;
import player.Player;
import player.Players;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {

        JanggiPieceInitializer janggiPieceInitializer = new JanggiPieceInitializer();
        Pieces choPieces = janggiPieceInitializer.choInit();
        Pieces hanPieces = janggiPieceInitializer.hanInit();
        Player hanPlayer = new Player(hanPieces);
        Player choPlayer = new Player(choPieces);
        Players players = new Players(Map.of(HAN, hanPlayer, CHO, choPlayer));
        JanggiGameState janggiGameState = new JanggiGameState(players);

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        while (!janggiGameState.isGameOver()) {
            try{
                outputView.printJanggiPan(hanPieces, choPieces);
                Position presentPosition = inputView.getPresentPosition(janggiGameState.getAttackNation());
                Position destination = inputView.getDestination();

                janggiGameState.movePiece(presentPosition, destination);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

        }

        outputView.printResult(hanPlayer.isJanggunDie());
    }
}

