package org.vaadin.stefan.dnd.drop;
/**
 * This event is fired when a dragged sources is moved over the area of a drop target.
 * @param <T> dragged component type
 */
public class DragOverEvent<T> {

	private final T component;

	public DragOverEvent(T component) {
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
