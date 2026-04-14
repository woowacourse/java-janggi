package janggi.domain.board;

import janggi.domain.Camp;
import janggi.domain.Path;
import janggi.domain.JanggiPosition;
import janggi.domain.board.strategy.FormationStrategy;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceDisplayName;
import janggi.domain.piece.PieceFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    private final Map<JanggiPosition, Piece> janggiBoard;

    private Board(Map<JanggiPosition, Piece> janggiBoard) {
        this.janggiBoard = janggiBoard;
    }

    public static Board initializeToBoard(FormationStrategy formationStrategyByCho, FormationStrategy formationStrategyByHan) {
        Map<JanggiPosition, Piece> initBoard = new HashMap<>();
        initBoard.putAll(initializeFormation(Camp.CHO, formationStrategyByCho));
        initBoard.putAll(initializeFormation(Camp.HAN, formationStrategyByHan));
        return new Board(initBoard);
    }

    public static Board reconstruct(Map<JanggiPosition, Piece> janggiBoard) {
        return new Board(janggiBoard);
    }

    private static Map<JanggiPosition, Piece> initializeFormation(Camp camp, FormationStrategy formationStrategy) {
        Map<JanggiPosition, Piece> formation = new HashMap<>(formationStrategy.createPieces(camp));
        int initRow = camp.baselineRow();
        int generalRow = camp.calculateRow(1);
        int cannonRow = camp.calculateRow(2);
        int soldierRow = camp.calculateRow(3);
        formation.put(JanggiPosition.of(generalRow, 4), PieceFactory.create("GENERAL", camp));
        formation.put(JanggiPosition.of(initRow, 0), PieceFactory.create("CHARIOT", camp));
        formation.put(JanggiPosition.of(initRow, 8), PieceFactory.create("CHARIOT", camp));
        formation.put(JanggiPosition.of(initRow, 3), PieceFactory.create("ADVISOR", camp));
        formation.put(JanggiPosition.of(initRow, 5), PieceFactory.create("ADVISOR", camp));
        formation.put(JanggiPosition.of(cannonRow, 1), PieceFactory.create("CANNON", camp));
        formation.put(JanggiPosition.of(cannonRow, 7), PieceFactory.create("CANNON", camp));
        for (int i = 0; i <= 8; i += 2) {
            formation.put(JanggiPosition.of(soldierRow, i), PieceFactory.create("SOLDIER", camp));
        }
        return formation;
    }

    public void movePiece(JanggiPosition from, JanggiPosition to) {
        Piece piece = selectPiece(from);
        Path path = findPath(piece, from, to);
        validateMove(piece, path, to);
        executeMove(piece, from, to);
    }

    public Piece selectPiece(JanggiPosition position) {
        return Optional.ofNullable(janggiBoard.get(position))
                .orElseThrow(() -> new IllegalArgumentException("보드에 기물이 존재하지 않습니다."));
    }

    private void validateMove(Piece piece, Path path, JanggiPosition to) {
        validateRoute(piece, path);
        validateDestination(piece, to);
    }

    private Path findPath(Piece piece, JanggiPosition from, JanggiPosition to) {
        return piece.findMovablePaths(from).stream()
                .filter(path -> path.hasDestination(to))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("이동할 수 없는 좌표입니다."));
    }

    private void validateRoute(Piece piece, Path path) {
        Map<JanggiPosition, Piece> piecesOnRoute = findPiecesOnRoute(path);
        if (!piece.canPassRoute(piecesOnRoute)) {
            throw new IllegalArgumentException("경로가 막혀있습니다.");
        }
    }

    private Map<JanggiPosition, Piece> findPiecesOnRoute(Path path) {
        return janggiBoard.entrySet().stream()
                .filter(entry -> path.hasRoute(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void validateDestination(Piece piece, JanggiPosition to) {
        Optional.ofNullable(janggiBoard.get(to))
                .ifPresent(target -> {
                    if (!piece.canCatch(target)) {
                        throw new IllegalArgumentException("목적지의 기물을 잡을 수 없습니다.");
                    }
                });
    }

    private void executeMove(Piece piece, JanggiPosition from, JanggiPosition to) {
        janggiBoard.remove(from);
        janggiBoard.put(to, piece);
    }

    public Map<JanggiPosition, String> displayBoard() {
        Map<JanggiPosition, String> board = new HashMap<>();
        for (JanggiPosition position : janggiBoard.keySet()) {
            Piece piece = janggiBoard.get(position);
            board.put(position, piece.displayName());
        }
        return Collections.unmodifiableMap(board);
    }

    public Camp checkCampOfThePiece(JanggiPosition position) {
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

    public boolean isSameCamp(JanggiPosition position, Camp camp) {
        return janggiBoard.get(position).isSameCamp(camp);
    }

    public int calculateTotalScore(Camp camp) {
        return janggiBoard.values()
                .stream()
                .mapToInt(piece -> piece.getScoreIfCampMatches(camp))
                .sum();
    }

    public Map<JanggiPosition, Piece> getPiecesSnapshot() {
        return Collections.unmodifiableMap(janggiBoard);
    }
}
