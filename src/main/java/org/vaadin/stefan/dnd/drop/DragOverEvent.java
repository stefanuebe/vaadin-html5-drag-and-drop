package org.vaadin.stefan.dnd.drop;

public class DragOverEvent<T> {

		private final T component;

		public DragOverEvent(T component) {
			this.component = component;
		}
		public T getComponent() {
			return component;
		}

	}
