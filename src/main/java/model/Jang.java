package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Jang {
    private String team;

    public Jang(String team) {
        this.team = team;
    }

    public String getTeam(){
        return team;
    }

    public boolean canMove(int beforeX, int beforeY, int afterX, int afterY) {
        List<Integer> horizontal = List.of(0, 0, -1, 1);
        List<Integer> vertical = List.of(1, -1, 0, 0);

        return IntStream.range(0, horizontal.size())
                .anyMatch(i -> horizontal.get(i) + beforeX == afterX && vertical.get(i) + beforeY == afterY);
    }
}
