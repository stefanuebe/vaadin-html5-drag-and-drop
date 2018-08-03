package org.vaadin.stefan.dnd.drop;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.shared.Registration;

import java.util.LinkedHashSet;

public class DropTargetExtension<T extends Component> {
	private LinkedHashSet<DropListener<T>> dropListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragEnterListener<T>> dragEnterListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragOverListener<T>> dragOverListeners = new LinkedHashSet<>();
	private LinkedHashSet<DragLeaveListener<T>> dragLeaveListeners = new LinkedHashSet<>();

	public static <T extends Component> DropTargetExtension<T> extend(T component) {
		return new DropTargetExtension<>(component);
	}

	public DropTargetExtension(T component) {
		Element element = component.getElement();

		element.getNode().runWhenAttached(ui -> {
			Page page = ui.getPage();
			page.executeJavaScript("$0.addEventListener('dragover', e => e.preventDefault())", component);
			page.executeJavaScript("$0.addEventListener('drop', e => {e.preventDefault(); e.target.appendChild(document.getElementById(e.dataTransfer.getData('text/plain')))})", component);
		});

		component.getElement().addEventListener("drop", x -> dropListeners.forEach(l -> l.onDrop(new DropEvent<>(component))));
		component.getElement().addEventListener("dragenter", x -> dragEnterListeners.forEach(l -> l.onDragEnter(new DragEnterEvent<>(component))));
		component.getElement().addEventListener("dragover", x -> dragOverListeners.forEach(l -> l.onDragOver(new DragOverEvent<>(component))));
		component.getElement().addEventListener("dragleave", x -> dragLeaveListeners.forEach(l -> l.onDragLeave(new DragLeaveEvent<>(component))));
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
