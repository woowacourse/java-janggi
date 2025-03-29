package janggi.view;

import janggi.domain.Country;
import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import janggi.domain.position.PositionFile;
import janggi.domain.position.PositionRank;

import java.util.List;
import java.util.Map;

public class OutputView {

    private final ViewUtil viewUtil;

    public OutputView(final ViewUtil viewUtil) {
        this.viewUtil = viewUtil;
    }

    public void outputBoard(final Map<Country, List<Piece>> board) {
        final String[][] output = new String[11][10];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 10; j++) {
                output[i][j] = "\t";
            }
        }
        for (PositionRank rank : PositionRank.values()) {
            output[rank.amount][0] = rank.amount + "\t";
        }
        for (PositionFile file : PositionFile.values()) {
            output[0][file.amount] = file.amount + "\t";
        }
        for (PositionRank rank : PositionRank.values()) {
            for (PositionFile file : PositionFile.values()) {
                Position position = new Position(file, rank);
                output[rank.amount][file.amount] = viewUtil.parsePieceOf(board, position);
            }
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 10; i >= 0; i--) {
            for (int j = 0; j < 10; j++) {
                sb.append(output[i][j]);
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    public void outputWinner(final Country winner, final int winnerScore, final int looserScore) {
        System.out.printf("%s 승리!! ( %d : %d )\n", viewUtil.convertCountry(winner), winnerScore, looserScore);
    }
}
