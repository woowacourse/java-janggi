package model.board;

import java.util.ArrayList;
import java.util.List;

import model.Team;
import model.piece.Piece;
import model.piece.normal.Elephant;
import model.piece.normal.Horse;

class InsideTableSettingInitializer extends Initializer {

    @Override
    protected final List<Piece> generateElephant(Team team) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Elephant(team.onBaseX(2), team.onBaseY(0), team));
        pieces.add(new Elephant(team.onBaseX(6), team.onBaseY(0), team));
        return pieces;
    }

    @Override
    protected final List<Piece> generateHorse(Team team) {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Horse(team.onBaseX(1), team.onBaseY(0), team));
        pieces.add(new Horse(team.onBaseX(7), team.onBaseY(0), team));
        return pieces;
    }
}
