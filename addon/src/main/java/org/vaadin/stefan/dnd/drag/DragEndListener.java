package org.vaadin.stefan.dnd.drag;

/**
 * This listener instance will be informed, when the dragging of a component is finished.
 * @param <T> component type
 */
@FunctionalInterface
public interface DragEndListener<T> {

	/**
	 * This listener instance will be informed, when the dragging of a component is finished.
	 * @param event event
	 */
	void onDragEnd(DragEndEvent<T> event);
}
