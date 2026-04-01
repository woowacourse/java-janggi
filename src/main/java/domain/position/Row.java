package domain.position;

public record Row(int value) {

    public Row add(int value) {
        return new Row(this.value + value);
    }

    public boolean isRowInRange(int start, int end) {
        return value >= start && value <= end;
    }

    public Row getUpper(Row row) {
        if (value > row.value) {
            return this;
        }
        return row;
    }

    public Row getLowerValue(Row row) {
        if (value < row.value) {
            return this;
        }
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Row row = (Row) o;
        return value == row.value;
    }

}
