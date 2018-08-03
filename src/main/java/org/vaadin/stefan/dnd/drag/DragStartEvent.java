package org.vaadin.stefan.dnd.drag;

/**
 * This event is fired when the dragging of a source starts.
 * @param <T> dragged component type
 */
public class DragStartEvent<T> {

	private final T component;

	/**
	 * New instance. Expects the component to be dragged.
	 *
	 * @param component component
	 */
	public DragStartEvent(T component) {
		this.component = component;
	}

	/**
	 * Returns the dragged component.
	 * @return dragged component
	 */
	public T getComponent() {
		return component;
	}

}
