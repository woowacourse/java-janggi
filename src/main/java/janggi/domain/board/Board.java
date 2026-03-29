package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.board.strategy.FormationStrategy;
import janggi.domain.piece.*;
import janggi.domain.piece.strategy.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {

    private final Map<Position, Piece> janggiBoard;

    private Board(Map<Position, Piece> janggiBoard) {
        this.janggiBoard = janggiBoard;
    }

    public static Board initializeToBoard(FormationStrategy formationStrategyByCho, FormationStrategy formationStrategyByHan) {
        Map<Position, Piece> initBoard = new HashMap<>();
        initBoard.putAll(initializeFormation(Camp.CHO, formationStrategyByCho));
        initBoard.putAll(initializeFormation(Camp.HAN, formationStrategyByHan));
        return new Board(initBoard);
    }

    private static Map<Position, Piece> initializeFormation(Camp camp, FormationStrategy formationStrategy) {
        Map<Position, Piece> formation = new HashMap<>(formationStrategy.createPieces(camp));
        int initRow = camp.initRowPosition();
        int generalRow = initRow + camp.direction();
        int cannonRow = generalRow + camp.direction();
        int soldierRow = cannonRow + camp.direction();
        formation.put(Position.of(generalRow, 4), new General(camp, new GeneralStrategy()));
        formation.put(Position.of(initRow, 0), new Chariot(camp, new ChariotStrategy()));
        formation.put(Position.of(initRow, 8), new Chariot(camp, new ChariotStrategy()));
        formation.put(Position.of(initRow, 3), new Advisor(camp, new AdvisorStrategy()));
        formation.put(Position.of(initRow, 5), new Advisor(camp, new AdvisorStrategy()));
        formation.put(Position.of(cannonRow, 1), new Cannon(camp, new CannonStrategy()));
        formation.put(Position.of(cannonRow, 7), new Cannon(camp, new CannonStrategy()));
        for (int i = 0; i <= 8; i += 2) {
            formation.put(Position.of(soldierRow, i), new Soldier(camp, new SoldierStrategy(camp.direction())));
        }
        return formation;
    }

    public Piece selectPiece(int row, int col) {
        Position position = Position.of(row, col);
        Optional<Piece> piece = Optional.ofNullable(janggiBoard.get(position));
        return piece.orElseThrow(
                () -> new IllegalArgumentException("보드에 기물이 존재하지 않습니다.")
        );
    }

    public void movePiece(int row, int col, Position from) {
        Position to = Position.of(row, col);
        Piece piece = selectPiece(from);
        Path path = findPath(piece, from, to);
        validateRoute(piece, path);
        validateDestination(piece, to);
        executeMove(piece, from, to);
    }

    private Piece selectPiece(Position position) {
        return Optional.ofNullable(janggiBoard.get(position))
                .orElseThrow(() -> new IllegalArgumentException("보드에 기물이 존재하지 않습니다."));
    }

    private Path findPath(Piece piece, Position from, Position to) {
        return piece.findMovablePaths(from).stream()
                .filter(path -> path.hasDestination(to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 좌표입니다."));
    }

    private void validateRoute(Piece piece, Path path) {
        Map<Position, Piece> piecesOnRoute = findPiecesOnRoute(path);
        if (!piece.canPassRoute(piecesOnRoute)) {
            throw new IllegalArgumentException("경로가 막혀있습니다.");
        }
    }

    private Map<Position, Piece> findPiecesOnRoute(Path path) {
        return janggiBoard.entrySet().stream()
                .filter(entry -> path.hasRoute(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void validateDestination(Piece piece, Position to) {
        Optional.ofNullable(janggiBoard.get(to))
                .ifPresent(target -> {
                    if (!piece.canCatch(target)) {
                        throw new IllegalArgumentException("목적지의 기물을 잡을 수 없습니다.");
                    }
                });
    }

    private void executeMove(Piece piece, Position from, Position to) {
        janggiBoard.remove(from);
        janggiBoard.put(to, piece);
    }

    public Map<Position, Piece> janggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }
}
