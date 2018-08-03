package org.vaadin.stefan.dnd.drag;

@FunctionalInterface
	public interface DragStartListener<T> {
		void onDragStart(DragStartEvent<T> event);
	}

