package repository.converter;

import janggi.piece.Team;

public record TurnConverter(
        String turn
) {

    public static TurnConverter toEntity(Team team) {
        if(team==Team.CHO)return new TurnConverter("CHO");
        return new TurnConverter("HAN");
    }
}
