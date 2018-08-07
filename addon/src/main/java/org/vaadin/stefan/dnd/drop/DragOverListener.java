package org.vaadin.stefan.dnd.drop;
/**
 * This listener instance will be informed, when a dragged sources is moved over the area of a drop target.
 * @param <T> component type
 */
@FunctionalInterface
public interface DragOverListener<T> {

	/**
	 * This listener instance will be informed, when a dragged sources is moved over the area of a drop target.
	 * @param event event
	 */
	void onDragOver(DragOverEvent<T> event);
}

