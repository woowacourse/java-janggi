package domain.board.factory;

import domain.Team;
import domain.board.Board;
import domain.board.Point;
import domain.board.factory.elephantLocator.ElephantLocator;
import domain.board.factory.elephantLocator.InnerElephantLocator;
import domain.board.factory.elephantLocator.LeftElephantLocator;
import domain.board.factory.elephantLocator.OuterElephantLocator;
import domain.board.factory.elephantLocator.RightElephantLocator;
import domain.pieces.Cannon;
import domain.pieces.Chariot;
import domain.pieces.Empty;
import domain.pieces.General;
import domain.pieces.Guard;
import domain.pieces.Piece;
import domain.pieces.Soldier;
import execptions.JanggiArgumentException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class BoardFactory {

  private static final int BOARD_ROW_MAX = 10;
  private static final int BOARD_COLUMN_MAX = 9;
  private static final int MAX_SOLDIER_COUNT = 5;


  public static Board generateBoard(final Map<Team, Integer> setupsByTeam) {
    final Map<Point, Piece> locations = generateEmptyBoard();
    for (final Entry<Team, Integer> setup : setupsByTeam.entrySet()) {
      final Team team = setup.getKey();
      final ElephantLocator locator = createFromChoice(setup.getValue());
      locations.putAll(setupLocationsOnBoard(team));
      locations.putAll(locator.setupHorse(team));
      locations.putAll(locator.setupElephant(team));
    }
    return new Board(locations);
  }

  private static Map<Point, Piece> generateEmptyBoard() {
    final Map<Point, Piece> locations = new HashMap<>();
    for (int row = 0; row < BOARD_ROW_MAX; row++) {
      for (int column = 0; column < BOARD_COLUMN_MAX; column++) {
        locations.put(new Point(row, column), Empty.getInstance());
      }
    }
    return locations;
  }

  private static ElephantLocator createFromChoice(int choice) {
    return switch (choice) {
      case 1 -> new OuterElephantLocator();
      case 2 -> new InnerElephantLocator();
      case 3 -> new LeftElephantLocator();
      case 4 -> new RightElephantLocator();
      default -> throw new JanggiArgumentException("등록되지 않은 배치입니다: " + choice);
    };
  }

  private static Map<Point, Piece> setupLocationsOnBoard(final Team team) {
    final Map<Point, Piece> locations = new HashMap<>();
    putSoldiersOnLocations(team, locations);

    locations.put(new Point(team.calculateRowForPiece(0), 0), new Chariot(team));
    locations.put(new Point(team.calculateRowForPiece(0), 8), new Chariot(team));

    locations.put(new Point(team.calculateRowForPiece(2), 1), new Cannon(team));
    locations.put(new Point(team.calculateRowForPiece(2), 7), new Cannon(team));

    locations.put(new Point(team.calculateRowForPiece(0), 3), new Guard(team));
    locations.put(new Point(team.calculateRowForPiece(0), 5), new Guard(team));

    locations.put(new Point(team.calculateRowForPiece(1), 4), new General(team));

    return locations;
  }

  private static void putSoldiersOnLocations(final Team team, final Map<Point, Piece> locations) {
    for (int column = 0; column < MAX_SOLDIER_COUNT; column++) {
      final int row = team.calculateRowForPiece(3);
      locations.put(new Point(row, column * 2), new Soldier(team));
    }
  }
}
