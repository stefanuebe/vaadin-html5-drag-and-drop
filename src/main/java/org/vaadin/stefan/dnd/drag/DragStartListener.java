package org.vaadin.stefan.dnd.drag;


/**
 * This listener instance will be informed, when the dragging of a component starts.
 * @param <T> component type
 */
@FunctionalInterface
public interface DragStartListener<T> {

	/**
	 * This listener instance will be informed, when the dragging of a component starts.
	 * @param event event
	 */
	void onDragStart(DragStartEvent<T> event);
}

