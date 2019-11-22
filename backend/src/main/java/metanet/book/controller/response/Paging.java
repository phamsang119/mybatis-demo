package metanet.book.controller.response;

public class Paging {
    private int offset;
    private int limit;
    private long total;

    public Paging(int offset, int limit, long total) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
    }

    public Paging() {
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
