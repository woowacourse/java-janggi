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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoard implements PieceProvider {
    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLUMNS = 9;

    private final Map<Position, Piece> janggiBoard;

    public JanggiBoard(Map<Position, Piece> janggiBoard) {
        this.janggiBoard = new HashMap<>(janggiBoard);
        initializeBoard();
        setupInitialPieces();
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
        janggiBoard.put(new Position(baseRow, 0), new Chariot(team));
        janggiBoard.put(new Position(baseRow, 8), new Chariot(team));
        // 마
        janggiBoard.put(new Position(baseRow, 1), new Horse(team));
        janggiBoard.put(new Position(baseRow, 6), new Horse(team));
        // 상
        janggiBoard.put(new Position(baseRow, 2), new Elephant(team));
        janggiBoard.put(new Position(baseRow, 7), new Elephant(team));
        // 사
        janggiBoard.put(new Position(baseRow, 3), new Guard(team));
        janggiBoard.put(new Position(baseRow, 5), new Guard(team));
        // 궁
        janggiBoard.put(new Position(kingRow, 4), new King(team));
        // 포
        janggiBoard.put(new Position(cannonRow, 1), new Cannon(team));
        janggiBoard.put(new Position(cannonRow, 7), new Cannon(team));

        // 졸/병
        for (int col = 0; col < 9; col += 2) {
            janggiBoard.put(new Position(pawnRow, col), new Pawn(team));
        }
    }

    public void movePiece(Position currentPosition, Position targetPosition) {
        Piece currentPiece = getPiece(currentPosition);

        validateMovePiece(currentPosition, targetPosition, currentPiece);

        janggiBoard.put(targetPosition, currentPiece);
        janggiBoard.put(currentPosition, new Blank());
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

