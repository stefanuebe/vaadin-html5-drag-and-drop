package org.vaadin.stefan.dnd.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.dnd.DndActivator;
import org.vaadin.stefan.dnd.drag.DragSourceExtension;
import org.vaadin.stefan.dnd.drop.DropTargetExtension;

/**
 * The main view contains a button and a template element.
 */
@Route("")
@HtmlImport("frontend://styles/shared-styles.html")
public class DragAndDropView extends VerticalLayout {

	public static final int DURATION = 1000;
	public static final Notification.Position POSITION = Notification.Position.TOP_END;
	private final Checkbox showEvents;

	public DragAndDropView() {
		showEvents = new Checkbox("Show events", true);
		add(showEvents);

		// --- activate polyfill
		DndActivator.activateMobileDnd();

		// --- define dnd components
		Component draggable1 = new DragTestComponent(1);
		Component draggable2 = new DragTestComponent(2);

		VerticalLayout dropTarget = new VerticalLayout();
		dropTarget.add(new Span("Outer drop target"));
		dropTarget.addClassName("outer-drop");

		add(draggable1, draggable2, dropTarget);

		// --- define drag extension
		DragSourceExtension<Component> extend1 = DragSourceExtension.extend(draggable1);
		DragSourceExtension<Component> extend2 = DragSourceExtension.extend(draggable2);

		extend1.addDragStartListener(event -> showEventNotification("Event dragstart for 1"));
		extend2.addDragStartListener(event -> showEventNotification("Event dragstart for 2"));
		extend1.addDragEndListener(event -> showEventNotification("Event dragend for 1"));
		extend2.addDragEndListener(event -> showEventNotification("Event dragend for 2"));

		// --- define drop extension
		DropTargetExtension<VerticalLayout> dropTargetExtension = DropTargetExtension.extend(dropTarget);
		dropTargetExtension.addDragEnterListener(event -> showEventNotification("Event dragenter outer"));
		dropTargetExtension.addDragLeaveListener(event -> showEventNotification("Event dragleave outer"));
		dropTargetExtension.addDropListener(event -> showEventNotification("Event drop outer"));

		VerticalLayout innerDropTarget = new VerticalLayout();
		innerDropTarget.add(new Span("Inner drop target"));
		innerDropTarget.addClassName("inner-drop");

		DropTargetExtension<VerticalLayout> innerDropTargetExtension = DropTargetExtension.extend(innerDropTarget);
		innerDropTargetExtension.addDragEnterListener(event -> showEventNotification("Event dragenter inner"));
		innerDropTargetExtension.addDragLeaveListener(event -> showEventNotification("Event dragleave inner"));
		innerDropTargetExtension.addDropListener(event -> showEventNotification("Event drop inner"));

		dropTarget.add(innerDropTarget);

		VerticalLayout innerInnerDropTarget = new VerticalLayout();
		innerInnerDropTarget.add(new Span("Inner drop target"));
		innerInnerDropTarget.addClassName("inner-drop");

		DropTargetExtension<VerticalLayout> innerInnerDropTargetExtension = DropTargetExtension.extend(innerInnerDropTarget);
		innerInnerDropTargetExtension.addDragEnterListener(event -> showEventNotification("Event dragenter inner inner"));
		innerInnerDropTargetExtension.addDragLeaveListener(event -> showEventNotification("Event dragleave inner inner"));
		innerInnerDropTargetExtension.addDropListener(event -> showEventNotification("Event drop inner inner"));

		innerDropTarget.add(innerInnerDropTarget);

		setClassName("main-layout");
	}

	private void showEventNotification(String s) {
		if(showEvents.getValue()) {
			Notification.show(s, DURATION, POSITION);
		}
	}

	public static class DragTestComponent extends HorizontalLayout {
		public DragTestComponent(int i) {
			addClassName("drag-source");
			add(new Span("Dragabble " + i));
			add(new Button("My Button " + i));
			add(new TextField("my text field " + i, "Some value", "Some placeholder"));
		}
	}

}
