package janggi.game;

import janggi.team.Team;
import janggi.team.TeamName;
import janggi.view.Input;
import janggi.view.Output;

public class GameTracker {
    private static final String ANSWER_POSITIVE = "Y";

    private final Input input;
    private final Output output;

    public GameTracker(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public void displayBoard(Team teamHan, Team teamCho) {
        output.printBoard(teamHan, teamCho);
    }

    public Team switchTurn(Team oldTeam, Team teamHan, Team teamCho) {
        if (oldTeam.equals(teamHan)) {
            return teamCho;
        }
        return teamHan;
    }

    public Team checkOpponent(Team currentTeam, Team teamHan, Team teamCho) {
        if (currentTeam.equals(teamHan)) {
            return teamCho;
        }
        return teamHan;
    }

    public void trackScore(Team teamCho, Team teamHan) {
        teamCho.trackTeamScore(TeamName.CHO);
        teamHan.trackTeamScore(TeamName.HAN);
        output.printTeamScore(teamHan, teamCho);
    }

    public boolean isContinue(Team teamHan, Team teamCho) {
        return !isGameOver(teamHan, teamCho) && input.readGameContinue().equalsIgnoreCase(ANSWER_POSITIVE);
    }

    public boolean isGameOver(Team teamHan, Team teamCho) {
        return teamHan.isKingCaught() || teamCho.isKingCaught();
    }
}
