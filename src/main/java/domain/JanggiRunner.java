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

    public void run() {
        JanggiGame janggiGame = initializeGame();
        showInitializedBoardResult(janggiGame);
        startGame(janggiGame);
    }

    private void showInitializedBoardResult(JanggiGame janggiGame) {
        List<Piece> alivePieces = janggiGame.getAlivePieces();
        outputView.printBoard(alivePieces);
    }

    private void startGame(JanggiGame janggiGame) {
        executeGame(janggiGame);
        showWinner(janggiGame);
    }

    private void executeGame(JanggiGame janggiGame) {
        TeamType nowTurn = TeamType.CHO;
        while (isGameInProgress(janggiGame)) {
            Player nowPlayer = janggiGame.findPlayerByTeam(nowTurn);
            Position startPosition = inputView.getStartPosition(nowPlayer);
            Position endPosition = inputView.getEndPosition(nowPlayer);
            janggiGame.movePiece(startPosition, endPosition, nowTurn);
            outputView.printBoard(janggiGame.getAlivePieces());
            nowTurn = findNextTurn(nowTurn);
        }
    }

    private void showWinner(JanggiGame janggiGame) {
        Player winner = janggiGame.findWinner();
        outputView.printWinner(winner);
    }

    private JanggiGame initializeGame() {
        Players players = createPlayers();
        HorseElephantSetupStrategy firstPlayerStrategy = chooseStrategy(players.getChoPlayerName());
        HorseElephantSetupStrategy secondPlayerStrategy = chooseStrategy(players.getHanPlayerName());
        List<Piece> allPieces = createAllPieces(firstPlayerStrategy, secondPlayerStrategy);
        return new JanggiGame(players, allPieces);
    }

    private List<Piece> createAllPieces(HorseElephantSetupStrategy firstPlayerStrategy,
                                        HorseElephantSetupStrategy secondPlayerStrategy) {
        PieceFactory factory = new PieceFactory();
        return factory.createAllPieces(firstPlayerStrategy, secondPlayerStrategy);
    }

    private HorseElephantSetupStrategy chooseStrategy(String players) {
        String firstPlayerOption = inputView.getSetupNumber(players);
        return SetupOption.findSetupStrategy(firstPlayerOption);
    }

    private Players createPlayers() {
        Usernames usernames = createUsernames();
        String startPlayerName = inputView.getStartPlayerName();
        return Players.createFrom(usernames, startPlayerName);
    }

    private Usernames createUsernames() {
        String firstPlayerName = inputView.getFirstPlayerName();
        String secondPlayerName = inputView.getSecondPlayerName();
        return new Usernames(firstPlayerName, secondPlayerName);
    }

    private boolean isGameInProgress(JanggiGame janggiGame) {
        return !janggiGame.isFinished();
    }

    private TeamType findNextTurn(TeamType nowTurn) {
        if (nowTurn == TeamType.CHO) {
            return TeamType.HAN;
        }
        return TeamType.CHO;
    }
}
