package janggi.team;

public class Teams {
    Team teamCho;
    Team teamHan;

    public Teams(Team teamCho, Team teamHan) {
        this.teamCho = teamCho;
        this.teamHan = teamHan;
    }

    public Team switchTurn(Team oldTeam) {
        if (oldTeam.equals(teamHan)) {
            return teamCho;
        }
        return teamHan;
    }

    public Team checkOpponent(Team currentTeam) {
        if (currentTeam.equals(teamHan)) {
            return teamCho;
        }
        return teamHan;
    }

    public Team getTeamCho() {
        return teamCho;
    }

    public Team getTeamHan() {
        return teamHan;
    }
}
