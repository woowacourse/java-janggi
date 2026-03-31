package view;

import model.Team;
import model.coordinate.Position;
import model.formation.JanggiFormation;
import model.piece.Piece;
import view.parser.InputParser;

import java.util.List;
import java.util.Scanner;

import static view.formater.BoardFormatter.formatSymbol;

public class InputView {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final InputParser PARSER = new InputParser();

    public JanggiFormation readFormationNumber(Team team, List<JanggiFormation> janggiFormations) {
        System.out.printf("%n%s의 상차림을 선택해주세요.%n", team.getName());
        for (JanggiFormation formation : janggiFormations) {
            System.out.printf("%d. %s%n", formation.getOrder(), formation.getFormation());
        }

        String input = SCANNER.nextLine();
        int order = PARSER.parseNumber(input);
        return JanggiFormation.from(order);
    }

    public Position readSource(Team turn) {
        System.out.println();
        System.out.printf("[%s] 이동할 기물을 선택해주세요. (쉼표 기준으로 분리)%n", turn.getName());
        System.out.print("기물: ");
        return extractPosition();
    }

    private Position extractPosition() {
        List<String> tokens = PARSER.parseToken(SCANNER.nextLine(), ",");
        int row = PARSER.parseNumber(tokens.get(0));
        int col = PARSER.parseNumber(tokens.get(1));
        return new Position(row, col);
    }

    public Position readDestination(Team turn, Piece piece) {
        System.out.println();
        System.out.printf("[%s] 기물 %s의 다음 위치를 선택해주세요. (쉼표 기준으로 분리)%n", turn.getName(), formatSymbol(piece));
        System.out.print("기물: ");
        return extractPosition();
    }
}
