package org.vaadin.stefan.dnd.drag;

public class DragStartEvent<T> {

		private final T component;

		public DragStartEvent(T component) {
			this.component = component;
		}
		public T getComponent() {
			return component;
		}

	}
