package janggi;

import janggi.coordinate.Position;

import java.util.List;

public class Palaces {

    private final List<Palace> palaces;

    public Palaces(final List<Palace> palaces) {
        this.palaces = palaces;
    }

    public static Palaces create() {
        return new Palaces(
                List.of(Palace.from(Team.CHO),
                        Palace.from(Team.HAN)));
    }

    public boolean isPalace(Position position) {
        return getPalace(Team.CHO).isPalace(position) || getPalace(Team.HAN).isPalace(position);
    }

    public boolean isCenter(Position position) {
        return getPalace(Team.CHO).isCenter(position) || getPalace(Team.HAN).isCenter(position);
    }

    public Palace getPalace(Team team) {
        return palaces.stream()
                .filter(palace -> palace.isSameTeam(team))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

}
