package janggi.piece;

import janggi.board.TableOption;
import janggi.position.Position;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.List;

public class PieceGenerator {

    public List<Piece> generateInitialPieces(TableOption hanTableOption, TableOption choTableOption) {
        List<Piece> allPieces = new ArrayList<>();

        List<Piece> hanTableSettings = Team.HAN.generateTableSetPieces(hanTableOption);

        List<Piece> hanInitialBoardSetting = List.of(
                new Chariot(Team.HAN, new Position(1, 1)), new Chariot(Team.HAN, new Position(1, 9)),
                new Cannon(Team.HAN, new Position(3, 2)), new Cannon(Team.HAN, new Position(3,8)),
                new Soldier(Team.HAN, new Position(4, 1)),
                new Soldier(Team.HAN, new Position(4, 3)),
                new Soldier(Team.HAN, new Position(4, 5)),
                new Soldier(Team.HAN, new Position(4, 7)),
                new Soldier(Team.HAN, new Position(4, 9)),
                new Guard(Team.HAN, new Position(1, 4)), new Guard(Team.HAN, new Position(1, 6)),
                new King(Team.HAN, new Position(2, 5))
        );

        List<Piece> choTableSettings = Team.CHO.generateTableSetPieces(choTableOption);

        List<Piece> choInitialBoardSetting = List.of(
                new Chariot(Team.CHO, new Position(10, 1)), new Chariot(Team.CHO, new Position(10, 9)),
                new Cannon(Team.CHO, new Position(8, 2)), new Cannon(Team.CHO, new Position(8, 8)),
                new Soldier(Team.CHO, new Position(7, 1)),
                new Soldier(Team.CHO, new Position(7, 3)),
                new Soldier(Team.CHO, new Position(7, 5)),
                new Soldier(Team.CHO, new Position(7, 7)),
                new Soldier(Team.CHO, new Position(7, 9)),
                new Guard(Team.CHO, new Position(10, 4)), new Guard(Team.CHO, new Position(10, 6)),
                new King(Team.CHO, new Position(9, 5))
        );

        allPieces.addAll(hanInitialBoardSetting);
        allPieces.addAll(hanTableSettings);
        allPieces.addAll(choInitialBoardSetting);
        allPieces.addAll(choTableSettings);

        return allPieces;
    }
}
