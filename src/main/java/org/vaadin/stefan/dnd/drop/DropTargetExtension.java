package org.vaadin.stefan.dnd.drop;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.shared.Registration;

import java.util.LinkedHashSet;
import java.util.Optional;


public class DropTargetExtension<T extends Component> {
	private LinkedHashSet<DropListener<T>> dropListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragEnterListener<T>> dragEnterListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragOverListener<T>> dragOverListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragLeaveListener<T>> dragLeaveListeners = new LinkedHashSet<>();

	public DropTargetExtension(T component) {
		Element element = component.getElement();

		element.getNode().runWhenAttached(ui -> {
			Page page = ui.getPage();
			createClientSideDragOverEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('dragover', " + s + ")", component));
			createClientSideDragEnterEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('dragenter', " + s + ")", component));
			createClientSideDragLeaveEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('dragleave', " + s + ")", component));
			createClientSideDropEventListener().ifPresent(s -> page.executeJavaScript("$0.addEventListener('drop', " + s + ")", component));
		});

		element.addEventListener("drop", x -> dropListeners.forEach(l -> l.onDrop(new DropEvent<>(component))));
		element.addEventListener("dragenter", x -> dragEnterListeners.forEach(l -> l.onDragEnter(new DragEnterEvent<>(component))));
		element.addEventListener("dragover", x -> dragOverListeners.forEach(l -> l.onDragOver(new DragOverEvent<>(component))));
		element.addEventListener("dragleave", x -> dragLeaveListeners.forEach(l -> l.onDragLeave(new DragLeaveEvent<>(component))));
	}

	public static <T extends Component> DropTargetExtension<T> extend(T component) {
		return new DropTargetExtension<>(component);
	}

	protected Optional<String> createClientSideDropEventListener() {
		return Optional.of("e => {e.preventDefault(); e.target.appendChild(document.getElementById(e.dataTransfer.getData('text/plain')))}");
	}

	protected Optional<String> createClientSideDragOverEventListener() {
		return Optional.of("e => e.preventDefault()");
	}

	protected Optional<String> createClientSideDragEnterEventListener() {
		return Optional.empty();
	}

	protected Optional<String> createClientSideDragLeaveEventListener() {
		return Optional.empty();
	}

	public Registration addDropListener(DropListener<T> listener) {
		dropListeners.add(listener);
		return () -> dropListeners.remove(listener);
	}

	public Registration addDragOverListener(DragOverListener<T> listener) {
		dragOverListeners.add(listener);
		return () -> dragOverListeners.remove(listener);
	}

	public Registration addDragEnterListener(DragEnterListener<T> listener) {
		dragEnterListeners.add(listener);
		return () -> dragEnterListeners.remove(listener);
	}

	public Registration addDragLeaveListener(DragLeaveListener<T> listener) {
		dragLeaveListeners.add(listener);
		return () -> dragLeaveListeners.remove(listener);
	}
}
