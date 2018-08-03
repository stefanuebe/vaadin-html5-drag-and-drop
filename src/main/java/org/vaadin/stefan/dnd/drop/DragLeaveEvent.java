package org.vaadin.stefan.dnd.drop;

public class DragLeaveEvent<T> {

		private final T component;

		public DragLeaveEvent(T component) {
			this.component = component;
		}
		public T getComponent() {
			return component;
		}

	}
