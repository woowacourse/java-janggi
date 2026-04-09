package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.board.strategy.FormationStrategy;
import janggi.domain.piece.Advisor;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.General;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.position.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardFactory {
    private static final int CHO_ROW = 0;
    private static final int HAN_ROW = 9;
    private static final int CHO_DIR = 1;
    private static final int HAN_DIR = -1;
    private static final List<Integer> FORMATION_COLUMNS = List.of(1, 2, 6, 7);

    private BoardFactory() {
    }

    public static Board create(FormationStrategy choFormation,
                               FormationStrategy hanFormation) {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.putAll(createCampPieces(Camp.CHO, CHO_ROW, CHO_DIR, choFormation));
        pieces.putAll(createCampPieces(Camp.HAN, HAN_ROW, HAN_DIR, hanFormation));
        return new Board(pieces);
    }

    private static Map<Position, Piece> createCampPieces(
            Camp camp, int baseRow, int dir, FormationStrategy formation) {
        Map<Position, Piece> pieces = new HashMap<>(placeFormation(formation, camp, baseRow));
        pieces.put(Position.of(baseRow + dir, 4), new General(camp));
        pieces.put(Position.of(baseRow, 0), new Chariot(camp));
        pieces.put(Position.of(baseRow, 8), new Chariot(camp));
        pieces.put(Position.of(baseRow, 3), new Advisor(camp));
        pieces.put(Position.of(baseRow, 5), new Advisor(camp));
        pieces.put(Position.of(baseRow + dir * 2, 1), new Cannon(camp));
        pieces.put(Position.of(baseRow + dir * 2, 7), new Cannon(camp));
        for (int i = 0; i <= 8; i += 2) {
            pieces.put(Position.of(baseRow + dir * 3, i), new Soldier(camp));
        }
        return pieces;
    }

    public static Map<Position, Piece> placeFormation(
            FormationStrategy formationStrategy, Camp camp, int baseRow) {
        List<Piece> pieces = formationStrategy.createPieces(camp);
        Map<Position, Piece> result = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            result.put(Position.of(baseRow, FORMATION_COLUMNS.get(i)), pieces.get(i));
        }

        return result;
    }

    public static Board load(Map<Position, Piece> pieceMap) {

        return new Board(pieceMap);
    }
}


