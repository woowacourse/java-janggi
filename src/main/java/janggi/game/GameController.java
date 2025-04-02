package janggi.game;

import janggi.board.Position;
import janggi.db.BoardStatus;
import janggi.piece.Piece;
import janggi.team.Team;
import janggi.team.Teams;
import janggi.view.Input;
import janggi.view.Output;

public class GameController {
    private final Output output;
    private final GameProcessManager gameProcessManager;
    private final GameRequestValidator gameRequestValidator;

    public GameController(BoardStatus boardStatus) {
        Input input = new Input();
        this.output = new Output();
        this.gameProcessManager = new GameProcessManager(input, boardStatus);
        this.gameRequestValidator = new GameRequestValidator(input);
    }

    public void run() {
        Teams teams = gameProcessManager.initializeTeams();
        do {
            output.printBoard(teams);
            Team currentTeam = teams.getCurrentTeam();

            Piece currentPiece = gameRequestValidator.requestValidatedStartPoint(currentTeam);
            Position destination = gameRequestValidator.requestValidatedDestination(currentTeam, currentPiece);

            currentTeam.move(currentPiece, destination);
            Team opponentTeam = teams.getOpponentTeam();
            opponentTeam.updateStatusIfCaught(destination);

            teams.switchTurn();
        } while (gameProcessManager.isContinue(teams));

        output.printTeamScore(teams);
        gameProcessManager.saveTeams(teams);
    }
}
