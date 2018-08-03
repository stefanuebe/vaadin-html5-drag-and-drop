package org.vaadin.stefan.dnd.drop;
/**
 * This listener instance will be informed, when a dragged sources is dropped into the area of a drop target.
 * @param <T> component type
 */
@FunctionalInterface
public interface DropListener<T> {

	/**
	 * This listener instance will be informed, when a dragged sources is dropped into the area of a drop target.
	 * @param event event
	 */
	void onDrop(DropEvent<T> event);
}

