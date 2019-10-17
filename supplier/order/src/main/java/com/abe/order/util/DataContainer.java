package com.abe.order.util;

import java.util.List;

public class DataContainer<T> {
	public DataContainer() {}
	
	public DataContainer(int limit, int offset, int total, List<T> data) {
		this.limit = limit;
		this.offset = offset;
		this.total = total;
		this.count = (data == null) ? 0 : data.size();
		this.data = data;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	private int limit;
	private int offset;
	private int total;
	private int count;
	private List<T> data;
}
