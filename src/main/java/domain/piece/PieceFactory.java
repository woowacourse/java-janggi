package domain.piece;

import domain.Position;
import domain.TeamType;
import domain.piece.strategy.HorseElephantSetupStrategy;
import java.util.ArrayList;
import java.util.List;

public class PieceFactory {

    private static final List<Piece> HAN_DEFAULT_PIECE;
    private static final List<Piece> CHO_DEFAULT_PIECE;

    static {
        CHO_DEFAULT_PIECE = List.of(
                new Soldier(Position.of(3, 0), TeamType.CHO),
                new Soldier(Position.of(3, 2), TeamType.CHO),
                new Soldier(Position.of(3, 4), TeamType.CHO),
                new Soldier(Position.of(3, 6), TeamType.CHO),
                new Soldier(Position.of(3, 8), TeamType.CHO),
                new Cannon(Position.of(2, 1), TeamType.CHO),
                new Cannon(Position.of(2, 7), TeamType.CHO),
                new King(Position.of(1, 4), TeamType.CHO),
                new Guard(Position.of(0, 3), TeamType.CHO),
                new Guard(Position.of(0, 5), TeamType.CHO),
                new Chariot(Position.of(0, 0), TeamType.CHO),
                new Chariot(Position.of(0, 8), TeamType.CHO)
        );

        HAN_DEFAULT_PIECE = List.of(
                new Soldier(Position.of(6, 0), TeamType.HAN),
                new Soldier(Position.of(6, 2), TeamType.HAN),
                new Soldier(Position.of(6, 4), TeamType.HAN),
                new Soldier(Position.of(6, 6), TeamType.HAN),
                new Soldier(Position.of(6, 8), TeamType.HAN),
                new Cannon(Position.of(7, 1), TeamType.HAN),
                new Cannon(Position.of(7, 7), TeamType.HAN),
                new King(Position.of(8, 4), TeamType.HAN),
                new Guard(Position.of(9, 3), TeamType.HAN),
                new Guard(Position.of(9, 5), TeamType.HAN),
                new Chariot(Position.of(9, 0), TeamType.HAN),
                new Chariot(Position.of(9, 8), TeamType.HAN)
        );
    }

    public List<Piece> createAllPieces(HorseElephantSetupStrategy choStrategy,
                                       HorseElephantSetupStrategy hanStrategy) {
        List<Piece> choElephantHorse = choStrategy.createElephantHorse(TeamType.CHO);
        List<Piece> hanElephantHorse = hanStrategy.createElephantHorse(TeamType.HAN);
        List<Piece> allPieces = new ArrayList<>();
        allPieces.addAll(choElephantHorse);
        allPieces.addAll(hanElephantHorse);
        allPieces.addAll(new ArrayList<>(HAN_DEFAULT_PIECE));
        allPieces.addAll(new ArrayList<>(CHO_DEFAULT_PIECE));
        return allPieces;
    }
}
