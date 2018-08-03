package org.vaadin.stefan.dnd.drag;

/**
 * This event is fired while a draggable source is dragged and moved around.
 * @param <T> dragged component type
 */
public class DragEvent<T> {

	private final T component;

	/**
	 * New instance. Expects the component to be dragged.
	 *
	 * @param component component
	 */
	public DragEvent(T component) {
		this.component = component;
	}

	/**
	 * Returns the dragged component.
	 *
	 * @return dragged component
	 */
	public T getComponent() {
		return component;
	}

}
