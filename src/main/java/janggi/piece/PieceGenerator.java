package janggi.piece;

import janggi.board.TableOption;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.team.Team;

import java.util.ArrayList;
import java.util.List;

public class PieceGenerator {

    public List<Piece> generateInitialPieces(TableOption hanTableOption, TableOption choTableOption) {
        List<Piece> allPieces = new ArrayList<>();

        List<Piece> hanTableSettings = Team.HAN.generateTableSetPieces(hanTableOption);

        List<Piece> hanInitialBoardSetting = List.of(
                new Chariot(Team.HAN, new Position(new Row(1), new Column(1))), new Chariot(Team.HAN, new Position(new Row(1), new Column(9))),
                new Cannon(Team.HAN, new Position(new Row(3), new Column(2))), new Cannon(Team.HAN, new Position(new Row(3), new Column(8))),
                new Soldier(Team.HAN, new Position(new Row(4), new Column(1))),
                new Soldier(Team.HAN, new Position(new Row(4), new Column(3))),
                new Soldier(Team.HAN, new Position(new Row(4), new Column(5))),
                new Soldier(Team.HAN, new Position(new Row(4), new Column(7))),
                new Soldier(Team.HAN, new Position(new Row(4), new Column(9))),
                new Guard(Team.HAN, new Position(new Row(1), new Column(4))), new Guard(Team.HAN, new Position(new Row(1), new Column(6))),
                new King(Team.HAN, new Position(new Row(2), new Column(5)))
        );

        List<Piece> choTableSettings = Team.CHO.generateTableSetPieces(choTableOption);

        List<Piece> choInitialBoardSetting = List.of(
                new Chariot(Team.CHO, new Position(new Row(10), new Column(1))), new Chariot(Team.CHO, new Position(new Row(10), new Column(9))),
                new Cannon(Team.CHO, new Position(new Row(8), new Column(2))), new Cannon(Team.CHO, new Position(new Row(8), new Column(8))),
                new Soldier(Team.CHO, new Position(new Row(7), new Column(1))),
                new Soldier(Team.CHO, new Position(new Row(7), new Column(3))),
                new Soldier(Team.CHO, new Position(new Row(7), new Column(5))),
                new Soldier(Team.CHO, new Position(new Row(7), new Column(7))),
                new Soldier(Team.CHO, new Position(new Row(7), new Column(9))),
                new Guard(Team.CHO, new Position(new Row(10), new Column(4))), new Guard(Team.CHO, new Position(new Row(10), new Column(6))),
                new King(Team.CHO, new Position(new Row(9), new Column(5)))
        );

        allPieces.addAll(hanInitialBoardSetting);
        allPieces.addAll(hanTableSettings);
        allPieces.addAll(choInitialBoardSetting);
        allPieces.addAll(choTableSettings);

        return allPieces;
    }
}
