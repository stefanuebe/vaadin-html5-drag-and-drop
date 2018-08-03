package org.vaadin.stefan.dnd.drop;

public class DragEnterEvent<T> {

		private final T component;

		public DragEnterEvent(T component) {
			this.component = component;
		}
		public T getComponent() {
			return component;
		}

	}
