package janggi.board;

import janggi.piece.Elephant;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.team.TeamName;
import java.util.Arrays;
import java.util.List;

public enum BoardSetup {
    CHO_EHEH(TeamName.CHO, "EHEH",
            List.of(
                    new Elephant(TeamName.CHO, new Position(1, 0)),
                    new Horse(TeamName.CHO, new Position(2, 0)),
                    new Elephant(TeamName.CHO, new Position(6, 0)),
                    new Horse(TeamName.CHO, new Position(7, 0))
            )),
    CHO_HEHE(TeamName.CHO, "HEHE",
            List.of(
                    new Horse(TeamName.CHO, new Position(1, 0)),
                    new Elephant(TeamName.CHO, new Position(2, 0)),
                    new Horse(TeamName.CHO, new Position(6, 0)),
                    new Elephant(TeamName.CHO, new Position(7, 0))
            )),
    CHO_HEEH(TeamName.CHO, "HEEH",
            List.of(
                    new Horse(TeamName.CHO, new Position(1, 0)),
                    new Elephant(TeamName.CHO, new Position(2, 0)),
                    new Elephant(TeamName.CHO, new Position(6, 0)),
                    new Horse(TeamName.CHO, new Position(7, 0))
            )),
    CHO_EHHE(TeamName.CHO, "EHHE",
            List.of(
                    new Elephant(TeamName.CHO, new Position(1, 0)),
                    new Horse(TeamName.CHO, new Position(2, 0)),
                    new Horse(TeamName.CHO, new Position(6, 0)),
                    new Elephant(TeamName.CHO, new Position(7, 0))
            )),

    HAN_EHEH(TeamName.HAN, "EHEH",
            List.of(
                    new Elephant(TeamName.HAN, new Position(1, 9)),
                    new Horse(TeamName.HAN, new Position(2, 9)),
                    new Elephant(TeamName.HAN, new Position(6, 9)),
                    new Horse(TeamName.HAN, new Position(7, 9))
            )),
    HAN_HEHE(TeamName.HAN, "HEHE",
            List.of(
                    new Horse(TeamName.HAN, new Position(1, 9)),
                    new Elephant(TeamName.HAN, new Position(2, 9)),
                    new Horse(TeamName.HAN, new Position(6, 9)),
                    new Elephant(TeamName.HAN, new Position(7, 9))
            )),
    HAN_HEEH(TeamName.HAN, "HEEH",
            List.of(
                    new Horse(TeamName.HAN, new Position(1, 9)),
                    new Elephant(TeamName.HAN, new Position(2, 9)),
                    new Elephant(TeamName.HAN, new Position(6, 9)),
                    new Horse(TeamName.HAN, new Position(7, 9))
            )),
    HAN_EHHE(TeamName.HAN, "EHHE",
            List.of(
                    new Elephant(TeamName.HAN, new Position(1, 9)),
                    new Horse(TeamName.HAN, new Position(2, 9)),
                    new Horse(TeamName.HAN, new Position(6, 9)),
                    new Elephant(TeamName.HAN, new Position(7, 9))
            ));

    private static final String INVALID_OPTION = "선택한 옵션은 존재하지 않습니다.";

    private final TeamName teamName;
    private final String option;
    private final List<Piece> pieces;

    BoardSetup(TeamName teamName, String option, List<Piece> pieces) {
        this.teamName = teamName;
        this.option = option;
        this.pieces = pieces;
    }

    public static BoardSetup of(List<String> option) {
        return Arrays.stream(BoardSetup.values())
                .filter(value -> value.teamName.matchTeamName(option.getFirst()))
                .filter(value -> value.option.equalsIgnoreCase(option.getLast()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_OPTION));
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public TeamName getTeamName() {
        return teamName;
    }
}
