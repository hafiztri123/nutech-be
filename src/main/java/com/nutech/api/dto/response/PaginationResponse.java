package com.nutech.api.dto.response;

import java.util.List;

public class PaginationResponse<T> {
    private Integer offset;
    private Integer limit;
    private List<T> records;

    public PaginationResponse(Integer offset, Integer limit, List<T> records) {
        this.offset = offset;
        this.limit = limit;
        this.records = records;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
