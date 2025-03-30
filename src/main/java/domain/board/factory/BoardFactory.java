package domain.board.factory;

import domain.board.Board;
import domain.board.Point;
import domain.board.factory.elephantLocators.ElephantLocator;
import domain.board.factory.elephantLocators.InnerElephantLocator;
import domain.board.factory.elephantLocators.LeftElephantLocator;
import domain.board.factory.elephantLocators.OuterElephantLocator;
import domain.board.factory.elephantLocators.RightElephantLocator;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import domain.player.Player;
import domain.player.Team;
import dto.Choice;
import exceptions.JanggiGameRuleWarningException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class BoardFactory {
    private static final int MAX_SOLDIER_COUNT = 5;

    public static Board generateBoard(final Map<Player, Choice> setups) {
        final Map<Point, Piece> locations = new HashMap<>();
        for (final Entry<Player, Choice> setup : setups.entrySet()) {
            final Player player = setup.getKey();
            final ElephantLocator locator = createFromChoice(setup.getValue(), player);
            locations.putAll(setupSoldierLocations(player));
            locations.putAll(setupDefaultLocations(player));
            locations.putAll(locator.setupHorse(player));
            locations.putAll(locator.setupElephant(player));
        }
        return new Board(locations);
    }

    private static ElephantLocator createFromChoice(final Choice choice, final Player player) {
        return switch (choice.value()) {
            case 1 -> new OuterElephantLocator();
            case 2 -> new InnerElephantLocator();
            case 3 -> new LeftElephantLocator();
            case 4 -> new RightElephantLocator();
            default -> throw new JanggiGameRuleWarningException(player.getTeam() + "가 등록되지 않은 배치를 선택했습니다: " + choice);
        };
    }

    private static Map<Point, Piece> setupDefaultLocations(final Player player) {
        final Map<Point, Piece> locations = new HashMap<>();
        final Team team = player.getTeam();
        locations.put(new Point(team.calculateRowForPiece(0), 0), new Chariot(player));
        locations.put(new Point(team.calculateRowForPiece(0), 8), new Chariot(player));
        locations.put(new Point(team.calculateRowForPiece(2), 1), new Cannon(player));
        locations.put(new Point(team.calculateRowForPiece(2), 7), new Cannon(player));
        locations.put(new Point(team.calculateRowForPiece(0), 3), new Guard(player));
        locations.put(new Point(team.calculateRowForPiece(0), 5), new Guard(player));
        locations.put(new Point(team.calculateRowForPiece(1), 4), new General(player));
        return locations;
    }

    private static Map<Point, Piece> setupSoldierLocations(final Player player) {
        final Team team = player.getTeam();
        return IntStream.range(0, MAX_SOLDIER_COUNT)
                .boxed()
                .collect(Collectors.toMap(
                        column -> new Point(team.calculateRowForPiece(3), column * 2),
                        column -> new Soldier(player)
                ));
    }
}
