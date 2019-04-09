package org.vaadin.stefan.dnd.drop;

import com.vaadin.flow.component.Component;
import org.vaadin.stefan.dnd.drag.DragSourceExtension;

/**
 * This event is fired when a dragged sources is moved over the area of a drop target.
 *
 * @param <T> dragged component type
 */
public class DragOverEvent<T extends Component> extends DropExtensionEvent<T> {

    /**
     * Creates a new instance. Expects the drop target and the drag source (optional).
     *
     * @param component  drop target component
     * @param dragSource optional drag source
     */
    protected DragOverEvent(T component, DragSourceExtension<? extends Component> dragSource) {
        super(component, dragSource);
    }
}
