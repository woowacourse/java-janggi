package domain;

import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.strategy.HorseElephantSetupStrategy;
import java.util.List;
import view.InputView;
import view.OutputView;

public class JanggiRunner {
    private final InputView inputView;
    private final OutputView outputView;

    public JanggiRunner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run(){
        JanggiGame janggiGame = initializeGame();
        List<Piece> alivePieces = janggiGame.getAlivePieces();
        outputView.printBoard(alivePieces);
    }

    public JanggiGame initializeGame() {
        String firstPlayerName = inputView.getFirstPlayerName();
        String secondPlayerName = inputView.getSecondPlayerName();

        String startPlayerName = inputView.getStartPlayerName();

        Players players;
        if (startPlayerName.equals(firstPlayerName)) {
            players = new Players(new Player(firstPlayerName, TeamType.CHO),
                    new Player(secondPlayerName, TeamType.HAN));
        } else {
            players = new Players(new Player(secondPlayerName, TeamType.CHO),
                    new Player(firstPlayerName, TeamType.HAN));
        }

        String firstPlayerOption = inputView.getSetupNumber(players.getChoPlayerName());
        String secondPlayerOption = inputView.getSetupNumber(players.getHanPlayerName());

        HorseElephantSetupStrategy firstPlayerStrategy = SetupOption.findSetupStrategy(firstPlayerOption);
        HorseElephantSetupStrategy secondPlayerStrategy = SetupOption.findSetupStrategy(secondPlayerOption);

        PieceFactory factory = new PieceFactory();

        return new JanggiGame(players, factory.createAllPieces(firstPlayerStrategy, secondPlayerStrategy));
    }

    public void startGame() {

    }
}
