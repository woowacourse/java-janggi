package janggi.team;

public class Teams {
    private final Team teamCho;
    private final Team teamHan;
    private Team currentTeam;

    public Teams(Team teamCho, Team teamHan) {
        this.teamCho = teamCho;
        this.teamHan = teamHan;
        this.currentTeam = this.teamCho;
    }

    public void switchTurn() {
        if (currentTeam.equals(teamCho)) {
            currentTeam = this.teamHan;
            return;
        }
        if (currentTeam.equals(teamHan)) {
            currentTeam = this.teamCho;
        }
    }

    public double checkTeamChoScore() {
        return teamCho.checkTeamScore();
    }

    public double checkTeamHanScore() {
        return teamHan.checkTeamScore();
    }

    public Team getOpponentTeam() {
        if (currentTeam.equals(teamHan)) {
            return teamCho;
        }
        return teamHan;
    }

    public Team getCurrentTeam() {
        return currentTeam;
    }

    public Team getTeamCho() {
        return teamCho;
    }

    public Team getTeamHan() {
        return teamHan;
    }
}
