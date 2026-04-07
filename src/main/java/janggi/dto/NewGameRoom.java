package janggi.dto;

public record NewGameRoom(String turn,
                          boolean finished,
                          double choScore,
                          double hanScore) {
}
