package org.grozeille.pricer;

public class DateOffset {
    private int offset;

    private DateOffsetType type;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public DateOffsetType getType() {
        return type;
    }

    public void setType(DateOffsetType type) {
        this.type = type;
    }
}
