package org.vaadin.stefan.dnd.drop;

public class DropEvent<T> {

		private final T component;

		public DropEvent(T component) {
			this.component = component;
		}
		public T getComponent() {
			return component;
		}

	}
