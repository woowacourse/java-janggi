package console.view;

import static console.util.BoardFormatter.formatSymbol;
import static console.util.InputParser.parseLong;
import static console.view.ViewMapper.FORMATION_DISPLAY_MAPPER;
import static console.view.ViewMapper.FORMATION_ORDER_MAPPER;
import static console.view.ViewMapper.GAME_STATUS_DISPLAY_MAPPER;

import console.util.InputParser;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import model.GameStatus;
import model.Team;
import model.board.FormationType;
import model.board.TeamFormation;
import model.coordinate.Position;
import model.piece.Piece;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static TeamFormation readFormationByTeam(Team team) {
        int order = readFormationOrder(team);
        FormationType formationType = Optional.ofNullable(FORMATION_ORDER_MAPPER.get(order))
                .orElseThrow(() -> new IllegalArgumentException("올바른 상차림을 선택해주세요."));

        return new TeamFormation(team, formationType);
    }

    public static Position readPiecePositionForMove(Team turn) {
        System.out.println();
        System.out.printf("[%s] 이동할 기물을 선택해주세요. (쉼표 기준으로 분리)%n", turn.getName());
        System.out.print("기물: ");
        return extractPosition();
    }

    public static Position readPiecePositionForArrange(Team turn, Piece piece) {
        System.out.println();
        System.out.printf("[%s] 기물 %s의 다음 위치를 선택해주세요. (쉼표 기준으로 분리)%n", turn.getName(), formatSymbol(piece));
        System.out.print("기물: ");
        return extractPosition();
    }

    public static boolean readBigJangStatus(Team turn) {
        System.out.printf("%n[%s] 빅장입니다! 종료하시겠습니까? (Y, N)%n", turn.getName());
        String input = SCANNER.nextLine();
        InputCommand command = InputCommand.parse(input);
        return command == InputCommand.Y;
    }

    private static Position extractPosition() {
        List<String> tokens = InputParser.parseToken(SCANNER.nextLine(), ",");
        int row = InputParser.parseNumber(tokens.get(0));
        int col = InputParser.parseNumber(tokens.get(1));
        return new Position(row, col);
    }

    private static int readFormationOrder(Team team) {
        System.out.printf("%n%s의 상차림을 선택해주세요.%n", team.getName());
        FORMATION_ORDER_MAPPER.forEach((order, formation) ->
                System.out.printf("%d. %s%n", order, FORMATION_DISPLAY_MAPPER.get(formation)));

        String input = SCANNER.nextLine();
        return InputParser.parseNumber(input);
    }

    public static GameMenuCommand readGameMenu() {
        System.out.println();
        System.out.println("> 장기 게임 메뉴");
        System.out.println();
        Arrays.stream(GameMenuCommand.values())
                .forEach(gameMenu -> System.out.printf("%d. %s%n", gameMenu.getValue(), gameMenu.getDescription()));

        System.out.println();
        System.out.println("메뉴를 선택해주세요:");
        return GameMenuCommand.select(InputParser.parseNumber(SCANNER.nextLine()));
    }

    public static Long readPlayGameId(Map<Long, GameStatus> gameStatusById) {
        System.out.println();
        System.out.println("> 이어서 진행할 게임을 선택해주세요.");
        gameStatusById.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.printf("%d번 게임: %s 상태%n",
                        entry.getKey(), GAME_STATUS_DISPLAY_MAPPER.get(entry.getValue())));

        System.out.println();
        System.out.println("번호를 입력해주세요:");
        return parseLong(SCANNER.nextLine());
    }
}
