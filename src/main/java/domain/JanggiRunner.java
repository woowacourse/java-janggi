package domain;

import domain.piece.Piece;
import domain.piece.PieceFactory;
import domain.piece.strategy.HorseElephantSetupStrategy;
import domain.position.Position;
import java.util.Map;
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
        Map<Position, Piece> alivePieces = janggiGame.getAlivePieces();
        outputView.printBoard(alivePieces);
    }

    private void startGame(JanggiGame janggiGame) {
        executeGame(janggiGame);
        showWinner(janggiGame);
    }

    private void executeGame(JanggiGame janggiGame) {
        while (janggiGame.isInProgress()) {
            Player currentPlayer = janggiGame.getCurrentPlayer();
            if (inputCommand(currentPlayer) == CommandOption.UNDO) {
                janggiGame.undo();
                continue;
            }
            playerTurn(janggiGame);
        }
    }

    private void playerTurn(JanggiGame janggiGame) {
        try {
            Player nowPlayer = janggiGame.getCurrentPlayer();
            Position startPosition = inputView.getStartPosition(nowPlayer);
            Position endPosition = inputView.getEndPosition(nowPlayer);
            janggiGame.movePiece(startPosition, endPosition);
            outputView.printBoard(janggiGame.getAlivePieces());
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            playerTurn(janggiGame);
        }
    }

    private CommandOption inputCommand(Player player) {
        try {
            return inputView.getOptionCommand(player);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return inputCommand(player);
        }
    }


    private void showWinner(JanggiGame janggiGame) {
        Player winner = janggiGame.findWinner();
        if (janggiGame.isFinishedByCheckmate()) {
            outputView.printWinner(winner);
            return;
        }
        Map<Player, Double> playerScore = janggiGame.calculatePlayerScore();
        outputView.printScoreWinner(winner, playerScore);
    }

    private JanggiGame initializeGame() {
        Players players = createPlayers();
        HorseElephantSetupStrategy firstPlayerStrategy = chooseStrategy(players.getChoPlayerName());
        HorseElephantSetupStrategy secondPlayerStrategy = chooseStrategy(players.getHanPlayerName());
        Map<Position, Piece> allPieces = createAllPieces(firstPlayerStrategy, secondPlayerStrategy);
        return new JanggiGame(players, allPieces);
    }

    private Map<Position, Piece> createAllPieces(HorseElephantSetupStrategy firstPlayerStrategy,
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
}
