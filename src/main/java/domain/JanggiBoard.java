package domain;

import domain.piece.*;

import java.util.Collections;
import java.util.Map;

public class JanggiBoard implements PieceProvider {
    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLUMNS = 9;

    private final Map<Position, Piece> janggiBoard;

    public JanggiBoard(Map<Position, Piece> janggiBoard) {
        this.janggiBoard = janggiBoard;
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
        setupTeamPieces(Team.HAN, 0, 1,2, 3); // 한나라 기물 배치 (0~3행 위주)
        setupTeamPieces(Team.CHO, 9, 8,7,6); // 초나라 기물 배치 (9~6행 위주)
    }

    private void setupTeamPieces(Team team, int baseRow, int kingRow, int cannonRow, int pawnRow) {
        // 예시: 차(車) 배치
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
        // 예시: 포(包) 배치
        janggiBoard.put(new Position(cannonRow, 1), new Cannon(team));
        janggiBoard.put(new Position(cannonRow, 7), new Cannon(team));

        // 예시: 졸/병 배치
        for (int col = 0; col < 9; col += 2) {
            janggiBoard.put(new Position(pawnRow, col), new Pawn(team));
        }
    }

    @Override
    public boolean isBlank(Position position) {
        Piece piece = janggiBoard.get(position);
        return piece instanceof Blank;
    }

    @Override
    public Piece getPiece(Position position) {
        return janggiBoard.get(position);
    }
}

