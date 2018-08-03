package org.vaadin.stefan.dnd.drag;

public class DragEndEvent<T> {
	private final T component;

	public DragEndEvent(T component) {
		this.component = component;
	}

	public T getComponent() {
		return component;
	}
}
