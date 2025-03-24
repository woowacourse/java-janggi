package fixture;

import static game.domain.piece.Team.CHO;
import static game.domain.piece.Team.HAN;

import game.domain.board.BoardLocation;
import game.domain.piece.Cannon;
import game.domain.piece.Chariot;
import game.domain.piece.Elephant;
import game.domain.piece.Horse;
import game.domain.piece.King;
import game.domain.piece.Pawn;
import game.domain.piece.Piece;
import game.domain.piece.Scholar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFixture {
    public static Map<BoardLocation, Piece> createTeamBoard( ) {
        Map<BoardLocation, Piece> pieces = new HashMap<>();
        pieces.put(new BoardLocation(1, 1), new Chariot(HAN));
        pieces.put(new BoardLocation(4, 1), new Scholar(HAN));
        pieces.put(new BoardLocation(6, 1), new Scholar(HAN));
        pieces.put(new BoardLocation(9, 1), new Chariot(HAN));
        pieces.put(new BoardLocation(5, 2), new King(HAN));
        pieces.put(new BoardLocation(2, 3), new Cannon(HAN));
        pieces.put(new BoardLocation(8, 3), new Cannon(HAN));
        pieces.put(new BoardLocation(1, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(3, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(5, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(7, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(9, 4), new Pawn(HAN));
        return pieces;
    }


    public static Map<BoardLocation, Piece> createTeamPieces(
            List<BoardLocation> hanHorseBoardLocations,
            List<BoardLocation> hanElephantBoardLocations,
            List<BoardLocation> choHorseBoardLocations,
            List<BoardLocation> choElephantBoardLocations) {
        Map<BoardLocation, Piece> pieces = new HashMap<>();
        hanHorseBoardLocations.forEach(horseLocation -> pieces.put(horseLocation, new Horse(HAN)));
        hanElephantBoardLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Elephant(HAN)));
        pieces.put(new BoardLocation(1, 1), new Chariot(HAN));
        pieces.put(new BoardLocation(4, 1), new Scholar(HAN));
        pieces.put(new BoardLocation(6, 1), new Scholar(HAN));
        pieces.put(new BoardLocation(9, 1), new Chariot(HAN));
        pieces.put(new BoardLocation(5, 2), new King(HAN));
        pieces.put(new BoardLocation(2, 3), new Cannon(HAN));
        pieces.put(new BoardLocation(8, 3), new Cannon(HAN));
        pieces.put(new BoardLocation(1, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(3, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(5, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(7, 4), new Pawn(HAN));
        pieces.put(new BoardLocation(9, 4), new Pawn(HAN));

        choHorseBoardLocations.forEach(horseLocation -> pieces.put(horseLocation, new Horse(CHO)));
        choElephantBoardLocations.forEach(elephantLocation -> pieces.put(elephantLocation, new Elephant(CHO)));
        pieces.put(new BoardLocation(1, 10), new Chariot(CHO));
        pieces.put(new BoardLocation(4, 10), new Scholar(CHO));
        pieces.put(new BoardLocation(6, 10), new Scholar(CHO));
        pieces.put(new BoardLocation(9, 10), new Chariot(CHO));
        pieces.put(new BoardLocation(5, 9), new King(CHO));
        pieces.put(new BoardLocation(2, 8), new Cannon(CHO));
        pieces.put(new BoardLocation(8, 8), new Cannon(CHO));
        pieces.put(new BoardLocation(1, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(3, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(5, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(7, 7), new Pawn(CHO));
        pieces.put(new BoardLocation(9, 7), new Pawn(CHO));

        return pieces;
    }
}
