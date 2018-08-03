package org.vaadin.stefan.dnd.drag;

@FunctionalInterface
	public interface DragListener<T> {
		void onDrag(DragEvent<T> event);
	}

