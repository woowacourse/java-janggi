package janggi.dto;

public record GameRoom(int id,
                       String turn,
                       boolean finished,
                       double choScore,
                       double hanScore) {
}
