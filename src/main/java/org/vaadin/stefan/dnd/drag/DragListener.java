package org.vaadin.stefan.dnd.drag;


/**
 * This listener instance will be informed, while a component is dragged around.
 * @param <T> component type
 */
@FunctionalInterface
public interface DragListener<T> {

	/**
	 * This listener instance will be informed, while a component is dragged around.
	 * @param event event
	 */
	void onDrag(DragEvent<T> event);
}

