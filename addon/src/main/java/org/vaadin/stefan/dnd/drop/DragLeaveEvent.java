package org.vaadin.stefan.dnd.drop;
/**
 * This event is fired when a dragged sources is moved out of the area of a drop target.
 * @param <T> dragged component type
 */
public class DragLeaveEvent<T> {

	private final T component;

	public DragLeaveEvent(T component) {
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
