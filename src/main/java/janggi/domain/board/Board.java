package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.position.Position;
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
            choFormation.put(Position.of(3, i), new Soldier(cho, new SoldierStrategy(cho.direction())));
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
            choFormation.put(Position.of(6, i), new Soldier(han, new SoldierStrategy(han.direction())));
        }
        return choFormation;
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
