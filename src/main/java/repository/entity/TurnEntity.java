package repository.entity;

import janggi.piece.Team;

public record TurnEntity(
        String turn
) {

    public static TurnEntity toEntity(Team team) {
        if(team==Team.CHO)return new TurnEntity("CHO");
        return new TurnEntity("HAN");
    }
}
