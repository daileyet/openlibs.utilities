package com.openthinks.libs.utilities;

import java.util.Optional;
import java.util.function.Consumer;

public final class Result<T> {
	private T value;

	public Result(T value) {
		super();
		this.value = value;
	}
	
	public Result() {
		super();
	}

	public T get() {
		return value;
	}
	
	public Optional<T> getOptional(){
		return Optional.ofNullable(value);
	}

	public void set(T value) {
		this.value = value;
	}

	public boolean isNull() {
		return this.value==null;
	}
	
	public void ifPresent(Consumer<T> consumer) {
		consumer.accept(value);
	}
	
	public static final <T> Result<T> valueOf(T value) {
		return new Result<>(value);
	}

}