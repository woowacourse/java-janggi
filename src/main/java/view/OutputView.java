package view;

import domain.board.Board;
import domain.board.Point;
import domain.piece.Piece;
import domain.piece.Team;
import domain.score.Score;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OutputView {

    public void printStart() {
        System.out.println("장기 게임에 오신 것을 환영합니다.");
    }

    public void printBoard(final Map<Point, Piece> board) {
        List<List<String>> boardString = new ArrayList<>();
        for (int row = Board.START_ROW_INDEX; row <= Board.END_ROW_INDEX; row++) {

            List<String> rowString = new ArrayList<>();
            boardString.add(rowString);
            for (int column = Board.START_COLUMN_INDEX; column <= Board.END_COLUMN_INDEX; column++) {
                if (!board.containsKey(Point.of(row, column))) {
                    rowString.add(Painter.paintWhite("ㅁ"));
                    continue;
                }

                Piece piece = board.get(Point.of(row, column));
                if (piece.team() == Team.CHO) {
                    rowString.add(Painter.paintGreen(PieceTypeView.title(piece.type())));
                    continue;
                }
                rowString.add(Painter.paintRed(PieceTypeView.title(piece.type())));
            }
        }

        for (List<String> rowString : boardString) {
            System.out.println(String.join(" ", rowString));
        }

        Painter.clean();
    }

    public void printTurn(final Team team) {
        System.out.printf("%n> 현재 턴: %s나라", TeamView.title(team));
    }

    public void printMatchResult(final Team winTeam) {
        System.out.printf("%s나라의 승리입니다.%n", TeamView.title(winTeam));
    }

    public void printScore(final Map<Team, Score> totalScoreByTeam) {
        System.out.println();
        for (Team team : totalScoreByTeam.keySet()) {
            System.out.printf("%s나라 : %.1f점%n", TeamView.title(team), totalScoreByTeam.get(team).score());
        }
        System.out.println();
    }

    public void printSaveResult() {
        System.out.printf("%n장기 게임을 저장 후 종료합니다.");
    }

    public void printExit() {
        System.out.print("장기 게임을 종료합니다.");
    }
}
