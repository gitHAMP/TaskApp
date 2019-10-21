package com.hampcode.model.entity;

import java.util.stream.Stream;

public enum Status {
	OPEN("open"), CLOSED("closed"), REOPENED("reopened");

	private String typeOfStatus;

	Status(String typeOfStatus) {
		this.typeOfStatus = typeOfStatus;
	}

	public String getTypeOfStatus() {
		return typeOfStatus;
	}

	public static Stream<Status> stream() {
		return Stream.of(Status.values());
	}
}