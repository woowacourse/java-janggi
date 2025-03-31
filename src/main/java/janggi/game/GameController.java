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
        Team teamCho = teams.getTeamCho();
        Team teamHan = teams.getTeamHan();
        Team oldTeam = teamHan;
        do {
            output.printBoard(teamHan, teamCho);
            Team currentTeam = teams.switchTurn(oldTeam);
            oldTeam = currentTeam;

            Piece currentPiece = gameRequestValidator.requestAndValidateStartPoint(currentTeam);
            Position destination = gameRequestValidator.requestAndValidateDestination(currentTeam, currentPiece);
            String pieceName = currentPiece.getName();
            Position currentPosition = currentPiece.getPosition();

            currentTeam.move(pieceName, currentPosition, destination);
            Team opponentTeam = teams.checkOpponent(currentTeam);
            opponentTeam.updateStatusIfCaught(destination);
        } while (gameProcessManager.isContinue(teamHan, teamCho));

        output.printTeamScore(teamCho.checkTeamScore(), teamHan.checkTeamScore());
        gameProcessManager.saveTeams(teamCho, teamHan);
    }
}
