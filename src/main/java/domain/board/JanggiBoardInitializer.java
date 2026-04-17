package domain.board;

import static domain.Index.BOARD_COLUMNS;
import static domain.Index.BOARD_ROWS;

import domain.Team;
import domain.piece.Blank;
import domain.piece.Cannon;
import domain.piece.Car;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.position.Position;
import java.util.HashMap;
import java.util.Map;

public class JanggiBoardInitializer implements BoardInitializer {
    @Override
    public Map<Position, Piece> init() {
        Map<Position, Piece> boardSetting = new HashMap<>();

        for (int row = 0; row < BOARD_ROWS.getIndex(); row++) {
            for (int col = 0; col < BOARD_COLUMNS.getIndex(); col++) {
                boardSetting.put(new Position(row, col), new Blank());
            }
        }

        setupTeamPieces(boardSetting, Team.HAN, 0, 1, 2, 3); // 한나라 기물 배치 (0~3행 위주)
        setupTeamPieces(boardSetting, Team.CHO, 9, 8, 7, 6); // 초나라 기물 배치 (9~6행 위주)

        return boardSetting;
    }

    private void setupTeamPieces(Map<Position, Piece> boardSetting, Team team, int baseRow, int kingRow, int cannonRow,
                                 int pawnRow) {
        // 차
        boardSetting.put(new Position(baseRow, 0), new Car(team));
        boardSetting.put(new Position(baseRow, 8), new Car(team));
        // 마
        boardSetting.put(new Position(baseRow, 1), new Horse(team));
        boardSetting.put(new Position(baseRow, 6), new Horse(team));
        // 상
        boardSetting.put(new Position(baseRow, 2), new Elephant(team));
        boardSetting.put(new Position(baseRow, 7), new Elephant(team));
        // 사
        boardSetting.put(new Position(baseRow, 3), new Guard(team));
        boardSetting.put(new Position(baseRow, 5), new Guard(team));
        // 궁
        boardSetting.put(new Position(kingRow, 4), new King(team));
        // 포
        boardSetting.put(new Position(cannonRow, 1), new Cannon(team));
        boardSetting.put(new Position(cannonRow, 7), new Cannon(team));
        // 졸/병
        for (int col = 0; col < BOARD_COLUMNS.getIndex(); col += 2) {
            boardSetting.put(new Position(pawnRow, col), new Pawn(team));
        }
    }

}
