package janggi.domain;

import janggi.domain.dto.BoardPieceSnapshot;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.piece.PieceType;
import janggi.domain.strategy.InitializeStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int HORIZONTAL_LENGTH = 10;
    private static final int VERTICAL_LENGTH = 9;
    private static final int INITIAL_KING_COUNT = 2;

    private final Map<Position, Space> piecesInfo;

    public Board(InitializeStrategy initializeStrategy) {
        piecesInfo = generateBlankBoard();
        initializeStrategy.basicSetting(piecesInfo);
    }

    private Board(Map<Position, Space> piecesInfo) {
        this.piecesInfo = piecesInfo;
    }

    public static Board from(List<BoardPieceSnapshot> snapshots) {
        Map<Position, Space> board = generateBlankBoard();

        for (BoardPieceSnapshot snapshot : snapshots) {
            Position position = new Position(snapshot.x(), snapshot.y());
            Piece piece = PieceFactory.createPiece(snapshot.team(), snapshot.pieceType());
            board.put(position, piece);
        }
        return new Board(board);
    }

    private static Map<Position, Space> generateBlankBoard() {
        Map<Position, Space> blankBoard = new HashMap<>();

        for (int y = 0; y < HORIZONTAL_LENGTH; y++) {
            putHorizontal(blankBoard, y);
        }

        return blankBoard;
    }

    private static void putHorizontal(Map<Position, Space> blankBoard, int y) {
        for (int x = 0; x < VERTICAL_LENGTH; x++) {
            blankBoard.put(new Position(x, y), new Blank());
        }
    }

    public void move(Position from, Position to) {
        Space spaceFrom = piecesInfo.get(from);
        validateBlankSpace(spaceFrom);

        Piece selectedPiece = spaceFrom.asPiece();
        validatePieceRule(from, to, selectedPiece);

        applyMove(from, to, selectedPiece);
    }

    private void validatePieceRule(Position from, Position to, Piece selectedPiece) {
        selectedPiece.validateMove(from, to);

        List<Piece> blockedPiece = getBlockedPiece(from, to, selectedPiece);
        selectedPiece.validateRoutes(blockedPiece);

        Space spaceTo = piecesInfo.get(to);
        selectedPiece.validateArrival(spaceTo);
    }

    private void validateBlankSpace(Space spaceFrom) {
        if (spaceFrom.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 해당 좌표에 말이 없습니다.");
        }
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
        return HORIZONTAL_LENGTH;
    }

    public int getBoardHeight() {
        return VERTICAL_LENGTH;
    }

    public int getChoScore() {
        return calculateScore(Team.CHO);
    }

    public int getHanScore() {
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

    public List<BoardPieceSnapshot> getPieces() {
        return piecesInfo.entrySet().stream()
            .filter(entry -> !entry.getValue().isBlank())
            .map(entry -> {
                Position position = entry.getKey();
                Piece piece = entry.getValue().asPiece();

                return new BoardPieceSnapshot(
                    position.x(),
                    position.y(),
                    piece.getTeam(),
                    piece.getPieceType()
                );
            })
            .toList();
    }
}
