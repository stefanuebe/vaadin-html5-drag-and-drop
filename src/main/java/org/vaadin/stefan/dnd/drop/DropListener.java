package org.vaadin.stefan.dnd.drop;

@FunctionalInterface
	public interface DropListener<T> {
		void onDrop(DropEvent<T> event);
	}

