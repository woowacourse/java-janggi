package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.board.strategy.FormationStrategy;
import janggi.domain.piece.*;
import janggi.domain.piece.strategy.*;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> janggiBoard;

    private Board(Map<Position, Piece> janggiBoard) {
        this.janggiBoard = janggiBoard;
    }

    public static Board initializeToBoard(FormationStrategy formationStrategyByCho, FormationStrategy formationStrategyByHan) {
        Map<Position, Piece> initBoard = new HashMap<>();
        initBoard.putAll(initializeToCho(formationStrategyByCho));
        initBoard.putAll(initializeToHan(formationStrategyByHan));
        return new Board(initBoard);
    }

    private static Map<Position, Piece> initializeToCho(FormationStrategy formationStrategyByCho) {
        Camp cho = Camp.CHO;
        Map<Position, Piece> choFormation = new HashMap<>(formationStrategyByCho.createPieces(cho));
        choFormation.put(Position.of(1, 4), new General(cho, new GeneralStrategy()));
        choFormation.put(Position.of(0, 0), new Chariot(cho, new ChariotStrategy()));
        choFormation.put(Position.of(0, 8), new Chariot(cho, new ChariotStrategy()));
        choFormation.put(Position.of(0, 3), new Advisor(cho, new AdvisorStrategy()));
        choFormation.put(Position.of(0, 5), new Advisor(cho, new AdvisorStrategy()));
        choFormation.put(Position.of(2, 1), new Cannon(cho, new CannonStrategy()));
        choFormation.put(Position.of(2, 7), new Cannon(cho, new CannonStrategy()));
        for (int i = 0; i <= 8; i += 2) {
            choFormation.put(Position.of(3, i), new Soldier(cho, new SoldierStrategy()));
        }
        return choFormation;
    }

    private static Map<Position, Piece> initializeToHan(FormationStrategy formationStrategyByHan) {
        Camp han = Camp.HAN;
        Map<Position, Piece> choFormation = new HashMap<>(formationStrategyByHan.createPieces(han));
        choFormation.put(Position.of(8, 4), new General(han, new GeneralStrategy()));
        choFormation.put(Position.of(9, 0), new Chariot(han, new ChariotStrategy()));
        choFormation.put(Position.of(9, 8), new Chariot(han, new ChariotStrategy()));
        choFormation.put(Position.of(9, 3), new Advisor(han, new AdvisorStrategy()));
        choFormation.put(Position.of(9, 5), new Advisor(han, new AdvisorStrategy()));
        choFormation.put(Position.of(7, 1), new Cannon(han, new CannonStrategy()));
        choFormation.put(Position.of(7, 7), new Cannon(han, new CannonStrategy()));
        for (int i = 0; i <= 8; i += 2) {
            choFormation.put(Position.of(6, i), new Soldier(han, new SoldierStrategy()));
        }
        return choFormation;
    }

    public Map<Position, Piece> janggiBoard() {
        return janggiBoard;
    }
}
