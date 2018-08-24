package org.vaadin.stefan.dnd.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
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
		DndActivator.activateMobileDnd(this);

		showEvents = new Checkbox("Show events", true);
		add(showEvents);

		TestComponent d1 = new TestComponent(1);
		TestComponent d2 = new TestComponent(2);

		DragSourceExtension<TestComponent> extend1 = DragSourceExtension.extend(d1);
		DragSourceExtension<TestComponent> extend2 = DragSourceExtension.extend(d2);

		extend1.addDragStartListener(event -> showEventNotification("Event dragstart for 1"));
		extend2.addDragStartListener(event -> showEventNotification("Event dragstart for 2"));

		VerticalLayout dropTarget = new VerticalLayout();
		dropTarget.add(new Span("Outer drop target"));
		dropTarget.addClassName("outer-drop");

		DropTargetExtension<VerticalLayout> dropTargetExtension = DropTargetExtension.extend(dropTarget);
		dropTargetExtension.addDragEnterListener(event -> showEventNotification("Event dragenter outer"));
		dropTargetExtension.addDragLeaveListener(event -> showEventNotification("Event dragleave outer"));
		dropTargetExtension.addDropListener(event -> showEventNotification("Event drop outer"));

		add(d1, d2, dropTarget);

		VerticalLayout innerDropTarget = new VerticalLayout();
		innerDropTarget.add(new Span("Inner drop target"));
		innerDropTarget.addClassName("inner-drop");

		DropTargetExtension<VerticalLayout> innerDropTargetExtension = DropTargetExtension.extend(innerDropTarget);
		innerDropTargetExtension.addDragEnterListener(event -> showEventNotification("Event dragenter inner"));
		innerDropTargetExtension.addDragLeaveListener(event -> showEventNotification("Event dragleave inner"));
		innerDropTargetExtension.addDropListener(event -> showEventNotification("Event drop inner"));

		dropTarget.add(innerDropTarget);

		setClassName("main-layout");
	}

	private void showEventNotification(String s) {
		if(showEvents.getValue()) {
			Notification.show(s, DURATION, POSITION);
		}
	}

	public static class TestComponent extends HorizontalLayout {
		public TestComponent(int i) {
			addClassName("drag-source");
			add(new Span("Dragabble " + i));
		}
	}

}
