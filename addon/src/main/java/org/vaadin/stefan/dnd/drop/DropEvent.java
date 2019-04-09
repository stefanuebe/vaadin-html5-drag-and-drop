package org.vaadin.stefan.dnd.drop;

import com.vaadin.flow.component.Component;
import org.vaadin.stefan.dnd.drag.DragSourceExtension;

import java.util.Optional;

/**
 * This event is fired when a dragSource element is dropped into the area of a drop target.
 * @param <T> drop target type
 */
public class DropEvent<T extends Component> extends DropExtensionEvent<T> {

	/**
	 * Creates a new instance. Expects the drop target and the drag source (optional).
	 * @param component drop target component
	 * @param dragSource optional drag source
	 */
	public DropEvent(T component, DragSourceExtension<? extends Component> dragSource) {
		super(component, dragSource);
	}

}
