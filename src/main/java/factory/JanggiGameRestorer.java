package factory;

import domain.Board;
import domain.GameStatus;
import domain.JanggiGame;
import domain.Piece;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import dto.GameStateData;
import dto.PieceSnapshot;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JanggiGameRestorer {

        private static final int MIN_ROW = 0;
        private static final int MAX_ROW = 9;
        private static final int MIN_COL = 0;
        private static final int MAX_COL = 8;

        public JanggiGame restore(List<PieceSnapshot> snapshots, GameStateData stateData) {
            Board board = restoreBoard(snapshots);
            GameStatus status = restoreGameStatus(stateData);
            return new JanggiGame(board, status);
        }

        private Board restoreBoard(List<PieceSnapshot> snapshots) {
            Map<Position, Piece> boardMap = createEmptyBoard();

            for (PieceSnapshot snapshot : snapshots) {
                Position pos = Position.of(snapshot.row(), snapshot.col());
                PieceType type = resolvePieceType(snapshot.name(), snapshot.team());

                boardMap.put(pos, new Piece(PieceProperty.of(type, Team.valueOf(snapshot.team())), pos));
            }

            return Board.of(boardMap);
        }

        private Map<Position, Piece> createEmptyBoard() {
            Map<Position, Piece> boardMap = new HashMap<>();
            for (int row = MIN_ROW; row <= MAX_ROW; row++) {
                for (int col = MIN_COL; col <= MAX_COL; col++) {
                    Position pos = Position.of(row, col);
                    boardMap.put(pos, Piece.None(pos));
                }
            }
            return boardMap;
        }

        private GameStatus restoreGameStatus(GameStateData stateData) {
            return GameStatus.of(stateData.currentState());
        }

        private PieceType resolvePieceType(String name, String team) {
            if (name.equals(PieceType.GREEN_SOLDIER.description()) && team.equals(Team.GREEN.name())) {
                return PieceType.GREEN_SOLDIER;
            }
            if (name.equals(PieceType.RED_SOLDIER.description())) {
                return PieceType.RED_SOLDIER;
            }
            return PieceType.fromDescription(name);
        }
}
