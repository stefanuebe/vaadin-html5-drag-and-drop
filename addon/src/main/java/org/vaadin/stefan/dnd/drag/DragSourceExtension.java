package org.vaadin.stefan.dnd.drag;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.shared.Registration;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.UUID;

/**
 * This class enables HTML5 dragging for a specific Vaadin component. It also registers all needed client side
 * event listeners and will inform server side event listeners about drag source related events.
 *
 * @param <T> component type
 */
public class DragSourceExtension<T extends Component> {
	private final T component;
	private LinkedHashSet<DragListener<T>> dragListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragStartListener<T>> dragStartListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragEndListener<T>> dragEndListeners = new LinkedHashSet<>();

	/**
	 * Enables HTML5 dragging for the given component. It also registers all needed client side
	 * event listeners and will inform server side event listeners about drag source related events.
	 *
	 * @param component component to be set draggable
	 */
	public DragSourceExtension(T component) {
		this.component = component;
		if (!this.component.getId().isPresent()) {
			component.setId(UUID.randomUUID().toString());
		}

		Element element = component.getElement();
		element.setProperty("draggable", true);
		element.getNode().runWhenAttached(ui -> {
			Page page = ui.getPage();
			createClientSideDragStartEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('dragstart', " + s + ")", component));
			createClientSideDragEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('drag', " + s + ")", component));
			createClientSideDragEndEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('dragend', " + s + ")", component));
		});

		element.addEventListener("dragstart", x -> dragStartListeners.forEach(l -> l.onDragStart(new DragStartEvent<>(component))));
		element.addEventListener("drag", x -> dragListeners.forEach(l -> l.onDrag(new DragEvent<>(component))));
		element.addEventListener("dragend", x -> dragEndListeners.forEach(l -> l.onDragEnd(new DragEndEvent<>(component))));
	}

	/**
	 * Enables HTML5 dragging for the given component. It also registers all needed client side
	 * event listeners and will inform server side event listeners about drag source related events.
	 *
	 * @param component component to be set draggable
	 * @return extension instance
	 */
	public static <T extends Component> DragSourceExtension<T> extend(T component) {
		return new DragSourceExtension<>(component);
	}

	/**
	 * Returns the dragged component.
	 *
	 * @return component
	 */
	public T getComponent() {
		return component;
	}

	protected String[] createDraggedStyleNames() {
		return new String[]{"dragged", getComponent().getElement().getTag() + "-dragged"};
	}


	/**
	 * Creates the client side event listener for the "dragstart" event.
	 *
	 * @return client side event listener for "dragstart"
	 */
	protected Optional<String> createClientSideDragStartEventListener() {
		return Optional.of("e => {" +
				"if(typeof e.target.classList !== 'undefined') {" +
				"	e.target.classList.add('" + String.join("','", createDraggedStyleNames()) + "');" +
				"	e.dataTransfer.clearData();" +
				"	e.dataTransfer.effectAllowed = 'move';" +
				"	e.dataTransfer.setData('text/plain', e.target.id);" +
				"	}" +
				"}");
	}

	/**
	 * Creates the client side event listener for the "drag" event.
	 *
	 * @return client side event listener for "drag"
	 */
	protected Optional<String> createClientSideDragEventListener() {
		return Optional.empty();
	}

	/**
	 * Creates the client side event listener for the "dragend" event.
	 *
	 * @return client side event listener for "dragend"
	 */
	protected Optional<String> createClientSideDragEndEventListener() {
		return Optional.of("e => {" +
				"if(typeof e.target.classList !== 'undefined') {" +
				"	e.target.classList.remove('" + String.join("','", createDraggedStyleNames()) + "');" +
				"	e.dataTransfer.setData('text/plain', null);" +
				"	}" +
				"}");
	}

	/**
	 * Registers a server side listener that will be informed while the component is dragged around.
	 *
	 * @param listener listener
	 * @return registration instance to remove the listener
	 */
	public Registration addDragListener(DragListener<T> listener) {
		dragListeners.add(listener);
		return () -> dragListeners.remove(listener);
	}

	/**
	 * Registers a server side listener that will be informed when the component drag starts.
	 *
	 * @param listener listener
	 * @return registration instance to remove the listener
	 */
	public Registration addDragEndListener(DragEndListener<T> listener) {
		dragEndListeners.add(listener);
		return () -> dragEndListeners.remove(listener);
	}

	/**
	 * Registers a server side listener that will be informed when the component drag is finished. The component
	 * might be successfully dropped or not.
	 *
	 * @param listener listener
	 * @return registration instance to remove the listener
	 */
	public Registration addDragStartListener(DragStartListener<T> listener) {
		dragStartListeners.add(listener);
		return () -> dragStartListeners.remove(listener);
	}


}
