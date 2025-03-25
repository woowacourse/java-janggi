package model.board;

import java.util.ArrayList;
import java.util.List;

import model.Team;
import model.piece.Chariot;
import model.piece.Pao;
import model.piece.Piece;
import model.piece.normal.Palace;
import model.piece.normal.Pawn;
import model.piece.normal.Soldier;

abstract class Initializer {

    public static List<Piece> settingWith(Team team, TableSetting tableSetting) {
        return tableSetting.getInitializer().generatePiecesOf(team);
    }

    protected abstract List<Piece> generateElephant(Team team);

    protected abstract List<Piece> generateHorse(Team team);

    public final List<Piece> generatePiecesOf(Team team) {
        List<Piece> pieces = new ArrayList<>();
        pieces.addAll(generatePalace(team));
        pieces.addAll(generateSoldier(team));
        pieces.addAll(generatePao(team));
        pieces.addAll(generatePawn(team));
        pieces.addAll(generateElephant(team));
        pieces.addAll(generateHorse(team));
        pieces.addAll(generateChariot(team));
        return pieces;
    }

    private List<Piece> generatePalace(Team team) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Palace(team.onBaseX(4), team.onBaseY(1), team));
        return pieces;
    }

    private List<Piece> generateSoldier(Team team) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Soldier(team.onBaseX(3), team.onBaseY(0), team));
        pieces.add(new Soldier(team.onBaseX(5), team.onBaseY(0), team));
        return pieces;
    }

    private List<Piece> generatePao(Team team) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Pao(team.onBaseX(1), team.onBaseY(2), team));
        pieces.add(new Pao(team.onBaseX(7), team.onBaseY(2), team));
        return pieces;
    }

    private List<Piece> generatePawn(Team team) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Pawn(team.onBaseX(0), team.onBaseY(3), team));
        pieces.add(new Pawn(team.onBaseX(2), team.onBaseY(3), team));
        pieces.add(new Pawn(team.onBaseX(4), team.onBaseY(3), team));
        pieces.add(new Pawn(team.onBaseX(6), team.onBaseY(3), team));
        pieces.add(new Pawn(team.onBaseX(8), team.onBaseY(3), team));
        return pieces;
    }

    private List<Piece> generateChariot(Team team) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Chariot(team.onBaseX(0), team.onBaseY(0), team));
        pieces.add(new Chariot(team.onBaseX(8), team.onBaseY(0), team));
        return pieces;
    }
}
