package org.vaadin.stefan.dnd.drop;

@FunctionalInterface
	public interface DragOverListener<T> {
		void onDragOver(DragOverEvent<T> event);
	}

