package org.vaadin.stefan.dnd.drag;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.shared.Registration;

import java.util.LinkedHashSet;
import java.util.UUID;

public class DragSourceExtension<T extends Component> {
	private LinkedHashSet<DragListener<T>> dragListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragStartListener<T>> dragStartListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragEndListener<T>> dragEndListeners = new LinkedHashSet<>();

	public DragSourceExtension(T component) {

		if (!component.getId().isPresent()) {
			component.setId(UUID.randomUUID().toString());
		}
		component.getElement().setProperty("draggable", true);
		component.getElement().getNode().runWhenAttached(ui -> {
			Page page = ui.getPage();
			page.executeJavaScript("$0.addEventListener('dragstart', e => {" +
					"e.dataTransfer.effectAllowed = 'move';" +
					"e.dataTransfer.setData('text/plain', e.target.id);" +
					"})", component);
			page.executeJavaScript("$0.addEventListener('dragend', e => {" +
					"e.dataTransfer.setData('text/plain', null);" +
					"})", component);
		});

		component.getElement().addEventListener("dragstart", x -> dragStartListeners.forEach(l -> l.onDragStart(new DragStartEvent<>(component))));
		component.getElement().addEventListener("drag", x -> dragListeners.forEach(l -> l.onDrag(new DragEvent<>(component))));
		component.getElement().addEventListener("dragend", x -> dragEndListeners.forEach(l -> l.onDragEnd(new DragEndEvent<>(component))));
	}

	public static <T extends Component> DragSourceExtension<T> extend(T component) {
		return new DragSourceExtension<>(component);
	}

	public Registration addDragListener(DragListener<T> listener) {
		dragListeners.add(listener);
		return () -> dragListeners.remove(listener);
	}

	public Registration addDragEndListener(DragEndListener<T> listener) {
		dragEndListeners.add(listener);
		return () -> dragEndListeners.remove(listener);
	}

	public Registration addDragStartListener(DragStartListener<T> listener) {
		dragStartListeners.add(listener);
		return () -> dragStartListeners.remove(listener);
	}


}
