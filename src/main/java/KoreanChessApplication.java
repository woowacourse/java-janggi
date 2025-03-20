import java.util.Map;
import piece.Board;
import piece.Position;
import piece.Team;

public class KoreanChessApplication {

    private static final Map<Integer, Team> turnTable;

    static {
        turnTable = Map.of(0, Team.RED, 1, Team.BLUE);
    }

    public static void main(String[] args) {
        GameView gameView = new GameView();
        Board board = new Board("/initialPieces.txt");
        gameView.printChangePieceNotImplement();

        int turn = 0;
        while (!board.isKingDead()) {
            try {
                gameView.playerBoard(board.playerBoard());
                Team team = turnTable.get(turn);
                gameView.printTurn(team);
                Position selectPiecePosition = gameView.inputSelectPiece();
                Position selectPosition = gameView.inputPiecePosition();
                board.move(team, selectPiecePosition, selectPosition);
                turn++;
            } catch (IllegalArgumentException e) {
                gameView.printError(e.getMessage());
            }
        }
    }
}
