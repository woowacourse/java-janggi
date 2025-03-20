package janggi.board;

import janggi.team.Team;
import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.position.Position;

import java.util.Arrays;
import java.util.List;

public enum BoardOption {
    CHO_EHEH(Team.CHO,"상마상마",List.of(new Elephant(Team.CHO, new Position(1,0)), new Horse(Team.CHO, new Position(2,0)), new Elephant(Team.CHO, new Position(6,0)), new Horse(Team.CHO, new Position(7,0)))),
    CHO_HEHE(Team.CHO,"마상마상",List.of(new Horse(Team.CHO, new Position(1,0)), new Elephant(Team.CHO, new Position(2,0)), new Horse(Team.CHO, new Position(6,0)), new Elephant(Team.CHO, new Position(7,0)))),
    CHO_HEEH(Team.CHO,"마상상마",List.of(new Horse(Team.CHO, new Position(1,0)), new Elephant(Team.CHO, new Position(2,0)), new Elephant(Team.CHO, new Position(6,0)), new Horse(Team.CHO, new Position(7,0)))),
    CHO_EHHE(Team.CHO,"상마마상",List.of(new Elephant(Team.CHO, new Position(1,0)), new Horse(Team.CHO, new Position(2,0)), new Horse(Team.CHO, new Position(6,0)), new Elephant(Team.CHO, new Position(7,0)))),

    HAN_EHEH(Team.HAN,"상마상마",List.of(new Elephant(Team.HAN, new Position(1,9)), new Horse(Team.HAN, new Position(2,9)), new Elephant(Team.HAN, new Position(6,9)), new Horse(Team.HAN, new Position(7,9)))),
    HAN_HEHE(Team.HAN,"마상마상",List.of(new Horse(Team.HAN, new Position(1,9)), new Elephant(Team.HAN, new Position(2,9)), new Horse(Team.HAN, new Position(6,9)), new Elephant(Team.HAN, new Position(7,9)))),
    HAN_HEEH(Team.HAN,"마상상마",List.of(new Horse(Team.HAN, new Position(1,9)), new Elephant(Team.HAN, new Position(2,9)), new Elephant(Team.HAN, new Position(6,9)), new Horse(Team.HAN, new Position(7,9)))),
    HAN_EHHE(Team.HAN,"상마마상",List.of(new Elephant(Team.HAN, new Position(1,9)), new Horse(Team.HAN, new Position(2,9)), new Horse(Team.HAN, new Position(6,9)), new Elephant(Team.HAN, new Position(7,9))));

    private final Team team;
    private final String option;
    private final List<Piece> pieces;

    BoardOption(Team team, String option ,List<Piece> pieces) {
        this.team = team;
        this.option = option;
        this.pieces = pieces;
    }

    public static BoardOption of(Team team, String option) {
        return Arrays.stream(BoardOption.values())
                .filter(value -> value.team == team && value.option.equals(option))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("선택한 옵션은 존재하지 않습니다."));
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
