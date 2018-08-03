package org.vaadin.stefan.dnd.drop;

@FunctionalInterface
	public interface DragLeaveListener<T> {
		void onDragLeave(DragLeaveEvent<T> event);
	}

