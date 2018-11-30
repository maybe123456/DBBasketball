package com.miracle.base.http.model.bean;

import java.util.concurrent.atomic.AtomicInteger;

public class Feature<T> {

	public static AtomicInteger index = new AtomicInteger(1000);

	private int id;

	private T data;

	private int status;

	private int  type; //用户自己控制

	public int  getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Feature() {
		this.id = index.getAndIncrement();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isFlag(int id) {
		return id == this.getId();
	}

}
