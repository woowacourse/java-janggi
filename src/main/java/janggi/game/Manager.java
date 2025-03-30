package janggi.game;

import janggi.board.Position;
import janggi.db.BoardStatus;
import janggi.team.Team;
import janggi.view.Input;
import janggi.view.Output;
import java.util.List;
import java.util.Map;

public class Manager {
    private final GameSetup gameSetup;
    private final GameValidator gameValidator;
    private final GameTracker gameTracker;

    public Manager(BoardStatus boardStatus) {
        Input input = new Input();
        Output output = new Output();
        this.gameSetup = new GameSetup(input, boardStatus);
        this.gameValidator = new GameValidator(input);
        this.gameTracker = new GameTracker(input, output);
    }

    public void run() {
        List<Team> teams = gameSetup.initializeTeams();
        Team teamCho = teams.getFirst();
        Team teamHan = teams.getLast();
        Team oldTeam = teamHan;
        do {
            gameTracker.displayBoard(teamHan, teamCho);
            Team currentTeam = gameTracker.switchTurn(oldTeam, teamHan, teamCho);
            oldTeam = currentTeam;

            Map<String, Position> startingPieceInfo = gameValidator.validateStartPoint(currentTeam);
            String pieceName = startingPieceInfo.keySet().iterator().next();
            Position currentPosition = startingPieceInfo.get(pieceName);
            Position destination = gameValidator.validateDestination(currentTeam, pieceName, currentPosition);

            currentTeam.move(pieceName, currentPosition, destination);
            Team opponentTeam = gameTracker.checkOpponent(currentTeam, teamHan, teamCho);
            opponentTeam.updateStatusIfCaught(destination);
        } while (gameTracker.isContinue(teamHan, teamCho));

        gameTracker.trackScore(teamCho, teamHan);
        gameSetup.saveTeams(teamCho, teamHan);
    }
}
