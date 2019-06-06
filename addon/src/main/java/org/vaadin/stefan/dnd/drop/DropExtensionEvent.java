package org.vaadin.stefan.dnd.drop;

import com.vaadin.flow.component.Component;
import org.vaadin.stefan.dnd.drag.DragSourceExtension;

import java.util.Optional;

/**
 * Basic abstract class for drop extension events, that provide information about the drop target and the
 * drag source.
 * @param <T> drop target type
 */
abstract class DropExtensionEvent<T extends Component> {
    private final T component;
    private final DragSourceExtension<? extends Component> dragSource;

    /**
     * Basic abstract class for drop extension events, that provide information about the drop target and the
     * drag source.
     * @param <T> drop target type
     */
    protected DropExtensionEvent(T component, DragSourceExtension<? extends Component> dragSource) {
        this.component = component;
        this.dragSource = dragSource;
    }

    /**
     * Returns the drop target .
     * @return component drop target
     */
    public T getComponent() {
        return component;
    }

    /**
     * Returns the dragSource element. Might be empty, when no drag source could be obtained (e.g. when elements
     * from outside of the UI has been dragged into the application.
     * @return dragSource current active drag source
     */
    public Optional<DragSourceExtension<? extends Component>> getDragSource() {
        return Optional.ofNullable(dragSource);
    }
}
