package janggi.domain.board;

import janggi.domain.board.initializer.BoardInitializer;
import janggi.domain.piece.Camp;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public class Board implements BoardChecker {

    private static final String INVALID_CAMP_PIECE = "[ERROR] 상대 진영의 기물은 이동할 수 없습니다.";
    private static final String SOURCE_NOT_EXISTS = "[ERROR] 출발지에 기물이 존재하지 않습니다.";
    private final Map<Position, Piece> board = new HashMap<>();

    public Board(BoardInitializer boardInitializer) {
        board.putAll(boardInitializer.initialize());
    }

    @Override
    public boolean hasPieceAt(Position position) {
        return board.containsKey(position);
    }

    @Override
    public boolean isSameCampPieceAt(Position position, Camp camp) {
        if (board.containsKey(position)) {
            return board.get(position).isSameCamp(camp);
        }
        return false;
    }

    @Override
    public boolean hasSamePieceTypeAt(Position position, PieceType pieceType) {
        if (!board.containsKey(position)) {
            return false;
        }
        Piece foundPiece = board.get(position);
        return foundPiece.isSamePieceType(pieceType);
    }

    public boolean movePieceAndCheckGameEnd(Position source, Position destination, Camp turn) {
        validateCampTurn(source, turn);
        Piece movingPiece = board.get(source);
        movingPiece.validateMove(source, destination, this);

        boolean isDestinationGeneral = isGeneralAt(destination);
        board.remove(source);
        board.put(destination, movingPiece);
        return isDestinationGeneral;
    }

    public void validateCampTurn(Position source, Camp turn) {
        validateSource(source);
        Piece piece = board.get(source);
        if (!piece.isSameCamp(turn)) {
            throw new IllegalArgumentException(INVALID_CAMP_PIECE);
        }
    }

    public Map<Camp, Double> calculateScore() {
        Map<Camp, Double> resultScore = new HashMap<>();

        Camp.getAllCamp().forEach(camp -> {
            double totalScore = board.values().stream()
                    .filter(piece -> piece.isSameCamp(camp))
                    .mapToDouble(Piece::score)
                    .sum();

            totalScore += camp.getBonusScoreForSecondPlayer();
            resultScore.put(camp, totalScore);
        });

        return resultScore;
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }

    private boolean isGeneralAt(Position position) {
        if (!board.containsKey(position)) {
            return false;
        }
        return board.get(position).isGeneral();
    }

    private void validateSource(Position source) {
        if (!board.containsKey(source)) {
            throw new IllegalArgumentException(SOURCE_NOT_EXISTS);
        }
    }
}
