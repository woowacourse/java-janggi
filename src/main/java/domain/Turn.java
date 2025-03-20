package domain;

public class Turn {
    private int value = 1;

    public Team getCurrnetTeam() {
        if (value % 2 == 0) {
            return Team.RED;
        }
        return Team.BLUE;
    }

    public void increaseRound() {
        ++value;
    }
}
