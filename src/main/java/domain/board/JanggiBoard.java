package domain.board;

import domain.Position;
import domain.Team;
import domain.piece.Blank;
import domain.piece.Cannon;
import domain.piece.Chariot;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceProvider;
import domain.piece.PieceType;
import domain.strategy.CannonStrategy;
import domain.strategy.ChariotStrategy;
import domain.strategy.ElephantStrategy;
import domain.strategy.HorseStrategy;
import domain.strategy.PalaceStrategy;
import domain.strategy.PawnStrategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoard implements PieceProvider {
    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLUMNS = 9;

    private final Map<Position, Piece> janggiBoard;

    public JanggiBoard(Map<Position, Piece> janggiBoard) {
        this.janggiBoard = new HashMap<>();
        initializeBoard();
        if (janggiBoard.isEmpty()) {
            setupInitialPieces();
            return;
        }
        this.janggiBoard.putAll(janggiBoard);
    }

    public Map<Position, Piece> getJanggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_ROWS; row++) {
            for (int column = 0; column < BOARD_COLUMNS; column++) {
                janggiBoard.put(new Position(row, column), new Blank());
            }
        }
    }

    private void setupInitialPieces() {
        setupTeamPieces(Team.HAN, 0, 1,2, 3); // 한나라 기물 배치
        setupTeamPieces(Team.CHO, 9, 8,7,6); // 초나라 기물 배치
    }

    private void setupTeamPieces(Team team, int baseRow, int kingRow, int cannonRow, int pawnRow) {
        // 차
        janggiBoard.put(new Position(baseRow, 0), new Chariot(team, new ChariotStrategy()));
        janggiBoard.put(new Position(baseRow, 8), new Chariot(team, new ChariotStrategy()));
        // 마
        janggiBoard.put(new Position(baseRow, 1), new Horse(team, new HorseStrategy()));
        janggiBoard.put(new Position(baseRow, 6), new Horse(team, new HorseStrategy()));
        // 상
        janggiBoard.put(new Position(baseRow, 2), new Elephant(team, new ElephantStrategy()));
        janggiBoard.put(new Position(baseRow, 7), new Elephant(team, new ElephantStrategy()));
        // 사
        janggiBoard.put(new Position(baseRow, 3), new Guard(team, new PalaceStrategy()));
        janggiBoard.put(new Position(baseRow, 5), new Guard(team, new PalaceStrategy()));
        // 궁
        janggiBoard.put(new Position(kingRow, 4), new King(team, new PalaceStrategy()));
        // 포
        janggiBoard.put(new Position(cannonRow, 1), new Cannon(team, new CannonStrategy()));
        janggiBoard.put(new Position(cannonRow, 7), new Cannon(team, new CannonStrategy()));

        // 졸/병
        for (int col = 0; col < 9; col += 2) {
            janggiBoard.put(new Position(pawnRow, col), new Pawn(team, new PawnStrategy()));
        }
    }

    public Piece movePiece(Position currentPosition, Position targetPosition) {
        Piece currentPiece = getPiece(currentPosition);

        Piece caughtPiece = getPiece(targetPosition);
        validateMovePiece(currentPosition, targetPosition, currentPiece);

        janggiBoard.put(targetPosition, currentPiece);
        janggiBoard.put(currentPosition, new Blank());

        return caughtPiece;
    }

    public double calculateScore(Team team) {
        double totalScore = janggiBoard.values().stream()
                .filter(piece -> piece.getTeam() == team)
                .map(Piece::getPieceType).mapToDouble(PieceType::getScore)
                .sum();
        if (team == Team.HAN) {
            totalScore += 1.5;
        }
        return totalScore;
    }

    public boolean isKingAlive(Team team) {
        return janggiBoard.values().stream().anyMatch(piece -> piece.getTeam() == team
                && piece.getPieceType() == PieceType.KING);
    }

    private void validateMovePiece(Position currentPosition, Position targetPosition, Piece currentPiece) {
        if (currentPiece.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 선택한 위치에 기물이 비어있습니다.");
        }
        if (!currentPiece.canMove(currentPosition, targetPosition, this)) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 이동할 수 없습니다.");
        }
    }

    @Override
    public boolean isBlank(Position position) {
        return getPiece(position).isBlank();
    }

    @Override
    public Piece getPiece(Position position) {
        return janggiBoard.get(position);
    }
}

