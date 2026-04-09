package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.strategy.InitializeStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int WIDTH = 9;
    private static final int HEIGHT = 10;
    private static final int INITIAL_KING_COUNT = 2;
    private static final double CHO_BONUS_SCORE = 1.5;

    private final Map<Position, Space> piecesInfo;

    public Board(InitializeStrategy initializeStrategy) {
        piecesInfo = generateBlankBoard();
        initializeStrategy.basicSetting(piecesInfo);
    }

    private Board(Map<Position, Space> piecesInfo) {
        this.piecesInfo = Map.copyOf(piecesInfo);
    }

    public static Board from(List<PieceInfo> snapshots) {
        Map<Position, Space> board = generateBlankBoard();

        for (PieceInfo snapshot : snapshots) {
            Position position = new Position(snapshot.x(), snapshot.y());
            Piece piece = PieceFactory.createPiece(snapshot.team(), snapshot.pieceType());
            board.put(position, piece);
        }
        return new Board(board);
    }

    private static Map<Position, Space> generateBlankBoard() {
        Map<Position, Space> blankBoard = new HashMap<>();

        for (int y = 0; y < HEIGHT; y++) {
            putHorizontal(blankBoard, y);
        }

        return blankBoard;
    }

    private static void putHorizontal(Map<Position, Space> blankBoard, int y) {
        for (int x = 0; x < WIDTH; x++) {
            blankBoard.put(new Position(x, y), new Blank());
        }
    }

    public void validateFromPiece(Team turn, Position from) {
        Space spaceFrom = piecesInfo.get(from);
        validateBlankSpace(spaceFrom);

        Piece selectedPiece = spaceFrom.asPiece();
        validateTurn(turn, selectedPiece);
    }

    public void move(Team turn, Position from, Position to) {
        validateFromPiece(turn, from);

        Space spaceFrom = piecesInfo.get(from);
        Piece selectedPiece = spaceFrom.asPiece();
        validatePieceRule(from, to, selectedPiece);

        applyMove(from, to, selectedPiece);
    }

    private void validateBlankSpace(Space spaceFrom) {
        if (spaceFrom.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 해당 좌표에 말이 없습니다.");
        }
    }

    private void validateTurn(Team turn, Piece selectedPiece) {
        if (!selectedPiece.isEqualTeam(turn)) {
            throw new IllegalArgumentException(
                String.format("[ERROR] %s팀의 차례입니다.", turn.getName())
            );
        }
    }

    private void validatePieceRule(Position from, Position to, Piece selectedPiece) {
        selectedPiece.validateMove(from, to);

        List<Piece> blockedPiece = getBlockedPiece(from, to, selectedPiece);
        selectedPiece.validateRoutes(blockedPiece);

        Space spaceTo = piecesInfo.get(to);
        selectedPiece.validateArrival(spaceTo);
    }

    private List<Piece> getBlockedPiece(Position from, Position to, Piece selectedPiece) {
        Path path = selectedPiece.getPath(from, to);
        return path.getBlockedPieces(piecesInfo);
    }

    private void applyMove(Position from, Position to, Piece selectedPiece) {
        piecesInfo.put(to, selectedPiece);
        piecesInfo.put(from, new Blank());
    }

    public boolean gameEnd() {
        long kingCount = piecesInfo.values().stream()
            .filter(space -> !space.isBlank())
            .filter(piece -> piece.asPiece().isSameType(PieceType.KING))
            .count();

        return kingCount < INITIAL_KING_COUNT;
    }

    public Space getSpace(Position position) {
        return piecesInfo.get(position);
    }

    public int getBoardWidth() {
        return WIDTH;
    }

    public int getBoardHeight() {
        return HEIGHT;
    }

    public double getChoScore() {
        return calculateScore(Team.CHO) + CHO_BONUS_SCORE;
    }

    public double getHanScore() {
        return calculateScore(Team.HAN);
    }

    private int calculateScore(Team team) {
        return piecesInfo.values().stream()
            .filter(space -> !space.isBlank())
            .map(Space::asPiece)
            .filter(piece -> piece.isEqualTeam(team))
            .mapToInt(Piece::getScore)
            .sum();
    }

    public List<PieceInfo> getPieces() {
        return piecesInfo.entrySet().stream()
            .filter(entry -> !entry.getValue().isBlank())
            .map(entry -> {
                Position position = entry.getKey();
                Piece piece = entry.getValue().asPiece();

                return new PieceInfo(
                    position.x(),
                    position.y(),
                    piece.getTeam(),
                    piece.getPieceType()
                );
            })
            .toList();
    }
}
