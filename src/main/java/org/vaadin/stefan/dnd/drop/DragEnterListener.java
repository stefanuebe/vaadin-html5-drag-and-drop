package org.vaadin.stefan.dnd.drop;

@FunctionalInterface
	public interface DragEnterListener<T> {
		void onDragEnter(DragEnterEvent<T> event);
	}

