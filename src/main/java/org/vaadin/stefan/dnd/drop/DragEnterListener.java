package org.vaadin.stefan.dnd.drop;

/**
 * This listener instance will be informed, when a dragged sources is moved into the area of a drop target.
 * @param <T> component type
 */
@FunctionalInterface
public interface DragEnterListener<T> {

	/**
	 * This listener instance will be informed, when a dragged sources is moved into the area of a drop target.
	 * @param event event
	 */
	void onDragEnter(DragEnterEvent<T> event);
}

