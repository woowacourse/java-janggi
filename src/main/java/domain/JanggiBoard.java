package domain;

import static domain.Index.BOARD_COLUMNS;
import static domain.Index.BOARD_ROWS;

import domain.piece.*;

import domain.position.Position;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class JanggiBoard implements PieceProvider {

    private final Map<Position, Piece> janggiBoard;

    public JanggiBoard() {
        this.janggiBoard = new LinkedHashMap<>();
        initializeBoard();
        setupInitialPieces();
    }

    public Map<Position, Piece> getJanggiBoard() {
        return Collections.unmodifiableMap(janggiBoard);
    }



    public void move(Position from, Position to, Piece currentPiece) {
        janggiBoard.put(to, currentPiece);
        janggiBoard.put(from, new Blank());
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_ROWS.getIndex(); row++) {
            for (int column = 0; column < BOARD_COLUMNS.getIndex(); column++) {
                janggiBoard.put(new Position(row, column), new Blank());
            }
        }
    }

    private void setupInitialPieces() {
        setupTeamPieces(Team.HAN, 0, 1,2, 3); // 한나라 기물 배치 (0~3행 위주)
        setupTeamPieces(Team.CHO, 9, 8,7,6); // 초나라 기물 배치 (9~6행 위주)
    }

    private void setupTeamPieces(Team team, int baseRow, int kingRow, int cannonRow, int pawnRow) {
        // 차
        janggiBoard.put(new Position(baseRow, 0), new Car(team));
        janggiBoard.put(new Position(baseRow, 8), new Car(team));
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

    @Override
    public boolean isBlank(Position position) {
        Piece piece = janggiBoard.get(position);
        return piece instanceof Blank;
    }

    @Override
    public boolean isCannon(Position position) {
        Piece piece = janggiBoard.get(position);
        return piece instanceof Cannon;
    }

    @Override
    public Piece getPiece(Position position) {
        return janggiBoard.get(position);
    }
}
