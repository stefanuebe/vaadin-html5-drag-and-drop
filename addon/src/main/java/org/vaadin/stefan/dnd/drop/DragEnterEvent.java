package org.vaadin.stefan.dnd.drop;

/**
 * This event is fired when a dragged sources is moved into the area of a drop target.
 * @param <T> dragged component type
 */
public class DragEnterEvent<T> {

	private final T component;

	/**
	 * New instance. Expects the dragged component.
	 *
	 * @param component component
	 */
	public DragEnterEvent(T component) {
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
