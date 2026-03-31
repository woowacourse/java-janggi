package janggi;

import janggi.domain.Board;
import janggi.domain.strategy.BasicPlacementStrategy;

public class Application {
    public static void main(String[] args) {
        Board board = new Board(new BasicPlacementStrategy());
    }
}
