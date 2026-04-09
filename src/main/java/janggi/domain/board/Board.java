package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.Position;
import janggi.domain.board.strategy.FormationStrategy;
import janggi.domain.piece.*;
import janggi.domain.piece.strategy.*;

import java.util.*;
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
        int initRow = camp.baselineRow();
        int generalRow = camp.calculateRow(1);
        int cannonRow = camp.calculateRow(2);
        int soldierRow = camp.calculateRow(3);
        formation.put(Position.of(generalRow, 4), new General(camp, new GeneralStrategy()));
        formation.put(Position.of(initRow, 0), new Chariot(camp, new ChariotStrategy()));
        formation.put(Position.of(initRow, 8), new Chariot(camp, new ChariotStrategy()));
        formation.put(Position.of(initRow, 3), new Advisor(camp, new AdvisorStrategy()));
        formation.put(Position.of(initRow, 5), new Advisor(camp, new AdvisorStrategy()));
        formation.put(Position.of(cannonRow, 1), new Cannon(camp, new CannonStrategy()));
        formation.put(Position.of(cannonRow, 7), new Cannon(camp, new CannonStrategy()));
        for (int i = 0; i <= 8; i += 2) {
            formation.put(Position.of(soldierRow, i), new Soldier(camp, new SoldierStrategy(camp.forward())));
        }
        return formation;
    }

    public void movePiece(Position from, Position to) {
        Piece piece = selectPiece(from);
        Path path = findPath(piece, from, to);
        validateMove(piece, path, to);
        executeMove(piece, from, to);
    }

    public Piece selectPiece(Position position) {
        return Optional.ofNullable(janggiBoard.get(position))
                .orElseThrow(() -> new IllegalArgumentException("보드에 기물이 존재하지 않습니다."));
    }

    private void validateMove(Piece piece, Path path, Position to) {
        validateRoute(piece, path);
        validateDestination(piece, to);
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

    public Map<Position, String> displayBoard() {
        Map<Position, String> board = new HashMap<>();
        for (Position position : janggiBoard.keySet()) {
            Piece piece = janggiBoard.get(position);
            board.put(position, piece.displayName());
        }
        return Collections.unmodifiableMap(board);
    }

    public Camp checkCampOfThePiece(Position position) {
        return janggiBoard.get(position)
                .getCamp();
    }

    public boolean isOnlyGeneralOfCampAlive(Camp camp) {
        List<Piece> generals = janggiBoard.values()
                .stream()
                .filter(piece -> PieceDisplayName.isGeneral(piece.displayName()))
                .toList();
        if (generals.size() != 1) {
            return false;
        }
        return generals.getFirst().isSameCamp(camp);
    }
}
