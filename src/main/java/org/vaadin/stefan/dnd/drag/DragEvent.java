package org.vaadin.stefan.dnd.drag;

public class DragEvent<T> {

		private final T component;

		public DragEvent(T component) {
			this.component = component;
		}
		public T getComponent() {
			return component;
		}

	}
