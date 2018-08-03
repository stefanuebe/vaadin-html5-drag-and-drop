package org.vaadin.stefan.dnd.drag;

@FunctionalInterface
public interface DragEndListener<T> {
	void onDragEnd(DragEndEvent<T> event);
}
