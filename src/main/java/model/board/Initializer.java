package model.board;

import java.util.ArrayList;
import java.util.List;

import model.Position;
import model.Team;
import model.piece.Chariot;
import model.piece.Elephant;
import model.piece.Horse;
import model.piece.King;
import model.piece.Pao;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.Soldier;

final class Initializer {

    public List<Piece> generatePiecesOf(Team team, TableSetting tableSetting) {
        List<Piece> pieces = new ArrayList<>();
        pieces.addAll(generatePalace(team));
        pieces.addAll(generateSoldier(team));
        pieces.addAll(generatePao(team));
        pieces.addAll(generatePawn(team));
        pieces.addAll(generateChariot(team));
        pieces.addAll(generateElephant(team, tableSetting));
        pieces.addAll(generateHorse(team, tableSetting));
        return pieces;
    }

    private List<Piece> generatePalace(Team team) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new King(team.onBaseX(4), team.onBaseY(1), team));
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

    private List<Piece> generateElephant(Team team, TableSetting tableSetting) {
        return generatePiecesWithTableSetting(team, tableSetting.getElephant(), Elephant::new);
    }

    private List<Piece> generateHorse(Team team, TableSetting tableSetting) {
        return generatePiecesWithTableSetting(team, tableSetting.getHorse(), Horse::new);
    }

    private List<Piece> generatePiecesWithTableSetting(Team team, List<Position> positions, PieceConstructor constructor) {
        return positions.stream()
            .map(position -> constructor.construct(team.onBaseX(position.x()), team.onBaseY(position.y()), team))
            .toList();
    }
}
