package domain;

public enum Team {
  HAN(false),
  CHO(true),
  NONE(false);

  private final boolean isFirst;

  Team(final boolean isFirst) {
    this.isFirst = isFirst;
  }

  public boolean isFirst() {
    return isFirst;
  }
}
