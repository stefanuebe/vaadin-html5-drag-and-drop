package org.vaadin.stefan.dnd.drop;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.shared.Registration;
import org.vaadin.stefan.dnd.drag.DragSourceExtension;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * This class marks a Vaadin component as a valid HTML5 drop target. The given component's element should be able to
 * have child elements. It also registers all needed client side event listeners and will inform server side event
 * listeners about drag source related events.
 *
 * @param <T> component type
 */
public class DropTargetExtension<T extends Component> {
    private final T component;
    private LinkedHashSet<DropListener<T>> dropListeners = new LinkedHashSet<>();
    private LinkedHashSet<DragEnterListener<T>> dragEnterListeners = new LinkedHashSet<>();
    private LinkedHashSet<DragOverListener<T>> dragOverListeners = new LinkedHashSet<>();
    private LinkedHashSet<DragLeaveListener<T>> dragLeaveListeners = new LinkedHashSet<>();

    /**
     * Marks a Vaadin component as a valid HTML5 drop target. The given component's element should be able to
     * have child elements. It also registers all needed client side event listeners and will inform server side event
     * listeners about drag source related events.
     *
     * @param component component
     */
    public DropTargetExtension(T component) {
        this.component = component;

        Element element = component.getElement();
        String property = element.getAttribute("class");
        String dropTargetStyles = String.join(" ", createDropTargetStyleNames());
        if (property != null) {
            element.setAttribute("class", property + " " + dropTargetStyles);
        } else {
            element.setAttribute("class", dropTargetStyles);
        }

        element.getNode().runWhenAttached(ui -> {
            Page page = ui.getPage();
            createClientSideDragOverEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('dragover', " + s + ")", component));
            createClientSideDragEnterEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('dragenter', " + s + ")", component));
            createClientSideDragLeaveEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('dragleave', " + s + ")", component));
            createClientSideDropEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('drop', " + s + ")", component));
        });

        element.addEventListener("drop", x -> consumeCurrentDragSource(d -> dropListeners.forEach(l -> l.onDrop(new DropEvent<>(component, d)))));
        element.addEventListener("dragenter", x -> consumeCurrentDragSource(d -> dragEnterListeners.forEach(l -> l.onDragEnter(new DragEnterEvent<>(component, d)))));
        element.addEventListener("dragover", x -> consumeCurrentDragSource(d -> dragOverListeners.forEach(l -> l.onDragOver(new DragOverEvent<>(component, d)))));
        element.addEventListener("dragleave", x -> consumeCurrentDragSource(d -> dragLeaveListeners.forEach(l -> l.onDragLeave(new DragLeaveEvent<>(component, d)))));
    }

    /**
     * Reads the current drag source and passes it to the given consumer. The consumer can for instance call listeners.
     * @param dragSourceConsumer consumer for drag source
     */
    private void consumeCurrentDragSource(Consumer<DragSourceExtension<? extends Component>> dragSourceConsumer) {
        component.getUI().ifPresent(ui -> {
            DragSourceExtension<? extends Component> dragSource = (DragSourceExtension<? extends Component>) ui.getSession().getAttribute("current_dragged_" + ui.hashCode());
            dragSourceConsumer.accept(dragSource);
        });
    }

    /**
     * Marks a Vaadin component as a valid HTML5 drop target. The given component's element should be able to
     * have child elements. It also registers all needed client side event listeners and will inform server side event
     * listeners about drag source related events.
     *
     * @param component component
     * @return extension instance
     */
    public static <T extends Component> DropTargetExtension<T> extend(T component) {
        return new DropTargetExtension<>(component);
    }

    /**
     * Creates the client side event listener for the "drop" event.
     *
     * @return client side event listener for "drop"
     */
    protected Optional<String> createClientSideDropEventListener() {
        return Optional.of("e => {" +
                "e.stopPropagation(); " +
                "e.preventDefault();" +
                "if(typeof e.target.classList !== 'undefined' && e.target.classList.contains('droptarget') && e.target.classList.contains('dragover')) {" +
                "   e.target.classList.remove('" + String.join("','", createDragOverStyleNames()) + "');" +

                // done on server side, not automatically on client side
//                "   var draggedElement = document.getElementById(e.dataTransfer.getData('text/plain'));" +
//                "   e.target.appendChild(draggedElement);" +
                "   }" +
                "}");
    }

    /**
     * Creates the client side event listener for the "dragover" event.
     *
     * @return client side event listener for "dragover"
     */
    protected Optional<String> createClientSideDragOverEventListener() {
        return Optional.of("e => {" +
                "e.stopPropagation(); " +
                "e.preventDefault();" +
                "}");
    }

    /**
     * Creates the client side event listener for the "dragenter" event.
     *
     * @return client side event listener for "dragenter"
     */
    protected Optional<String> createClientSideDragEnterEventListener() {
        return Optional.of("e => {" +
                "e.stopPropagation(); " +
                "e.preventDefault();" +
                "if(typeof e.target.classList !== 'undefined' && e.target.classList.contains('droptarget')) {" +
                "	e.target.classList.add('" + String.join("','", createDragOverStyleNames()) + "');" +
                "}" +
                "}");
    }

    /**
     * Creates the client side event listener for the "dragleave" event.
     *
     * @return client side event listener for "dragleave"
     */
    protected Optional<String> createClientSideDragLeaveEventListener() {
        return Optional.of("e => {" +
                "e.stopPropagation(); " +
                "e.preventDefault();" +
                "if(typeof e.target.classList !== 'undefined' && e.target.classList.contains('droptarget')) {" +
                "	e.target.classList.remove('" + String.join("','", createDragOverStyleNames()) + "');" +
                "}" +
                "}");
    }

    /**
     * Registers a server side listener that will be informed, whan a draggable will be dropped inside
     * this component.
     *
     * @param listener listener
     * @return registration instance to remove the listener
     */
    public Registration addDropListener(DropListener<T> listener) {
        dropListeners.add(listener);
        return () -> dropListeners.remove(listener);
    }

    /**
     * Registers a server side listener that will be informed, when a draggable is moved over the drop area.
     *
     * @param listener listener
     * @return registration instance to remove the listener
     */
    public Registration addDragOverListener(DragOverListener<T> listener) {
        dragOverListeners.add(listener);
        return () -> dragOverListeners.remove(listener);
    }

    /**
     * Registers a server side listener that will be informed, when a dragged component is moved into the drop area.
     *
     * @param listener listener
     * @return registration instance to remove the listener
     */
    public Registration addDragEnterListener(DragEnterListener<T> listener) {
        dragEnterListeners.add(listener);
        return () -> dragEnterListeners.remove(listener);
    }

    /**
     * Registers a server side listener that will be informed, when a dragged component is moved out of the drop area.
     *
     * @param listener listener
     * @return registration instance to remove the listener
     */
    public Registration addDragLeaveListener(DragLeaveListener<T> listener) {
        dragLeaveListeners.add(listener);
        return () -> dragLeaveListeners.remove(listener);
    }

    protected String[] createDragOverStyleNames() {
        return new String[]{"dragover", getComponent().getElement().getTag() + "-dragover"};
    }

    protected String[] createDropTargetStyleNames() {
        return new String[]{"droptarget", getComponent().getElement().getTag() + "-droptarget"};
    }

    public T getComponent() {
        return component;
    }
}
