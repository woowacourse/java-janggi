import java.time.Clock;
import java.util.List;
import java.util.Optional;

import domain.GameDeadline;
import domain.JanggiGame;
import domain.Piece;
import domain.ScoreCalculator;
import domain.TeamColor;
import domain.TeamScores;
import domain.TurnOutcome;
import io.InputView;
import io.OutputView;
import persistence.GameStatePersister;

public class Runner {

    private final InputView inputView;
    private final OutputView outputView;

    public Runner(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run(GameStatePersister persister, Clock clock) {
        outputView.printGameStart();
        GameInitializer initializer = new GameInitializer(inputView, outputView);
        InitializedGame initialized = initializer.resolve(persister, clock);
        JanggiGame game = initialized.game();
        GameDeadline deadline = initialized.deadline();
        outputView.printBoard(game.board());
        persister.saveInProgress(game, deadline);
        runGameLoop(game, deadline, persister, clock);
    }

    private void runGameLoop(JanggiGame game, GameDeadline deadline, GameStatePersister persister, Clock clock) {
        while (playTurn(game, deadline, persister, clock)) {
        }
    }

    private boolean playTurn(JanggiGame game, GameDeadline deadline, GameStatePersister persister, Clock clock) {
        if (deadline.isExpired(clock)) {
            endByScore(game, deadline, persister);
            return false;
        }
        TurnExecutor executor = new TurnExecutor(inputView, outputView);
        TurnOutcome outcome = executor.execute(game);
        return handleOutcome(game, outcome, deadline, persister);
    }

    private boolean handleOutcome(
            JanggiGame game, TurnOutcome outcome, GameDeadline deadline, GameStatePersister persister) {
        if (outcome == TurnOutcome.GAME_OVER) {
            persister.saveEnded(game, game.currentTurn(), deadline);
            return false;
        }
        game.progressTurn();
        persister.saveInProgress(game, deadline);
        return true;
    }

    private void endByScore(JanggiGame game, GameDeadline deadline, GameStatePersister persister) {
        ScoreCalculator calculator = new ScoreCalculator();
        List<Piece> choPieces = game.piecesOfTeam(TeamColor.CHO);
        List<Piece> hanPieces = game.piecesOfTeam(TeamColor.HAN);
        TeamScores scores = calculator.calculate(choPieces, hanPieces);
        Optional<TeamColor> winner = scores.winner();
        printScoreResult(scores, winner);
        persister.saveEnded(game, winner.orElse(null), deadline);
    }

    private void printScoreResult(TeamScores scores, Optional<TeamColor> winner) {
        if (winner.isEmpty()) {
            outputView.printTimeOverDraw(scores);
            return;
        }
        outputView.printTimeOverWinnerByScore(scores, winner.get());
    }
}
