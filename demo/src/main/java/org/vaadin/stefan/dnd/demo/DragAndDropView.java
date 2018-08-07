package org.vaadin.stefan.dnd.demo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
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

	public DragAndDropView() {
		TestComponent d1 = new TestComponent(1);
		TestComponent d2 = new TestComponent(2);

		DragSourceExtension<TestComponent> extend1 = DragSourceExtension.extend(d1);
		DragSourceExtension<TestComponent> extend2 = DragSourceExtension.extend(d2);

		extend1.addDragStartListener(event -> Notification.show("Event dragstart for 1", DURATION, POSITION));
		extend2.addDragStartListener(event -> Notification.show("Event dragstart for 2", DURATION, POSITION));

		VerticalLayout dropTarget = new VerticalLayout();
		dropTarget.add(new Span("Outer drop target"));
		dropTarget.addClassName("outer-drop");

		DropTargetExtension<VerticalLayout> dropTargetExtension = DropTargetExtension.extend(dropTarget);
		dropTargetExtension.addDragEnterListener(event -> Notification.show("Event dragenter outer", DURATION, POSITION));
		dropTargetExtension.addDragLeaveListener(event -> Notification.show("Event dragleave outer", DURATION, POSITION));
		dropTargetExtension.addDropListener(event -> Notification.show("Event drop outer", DURATION, POSITION));

		add(d1, d2, dropTarget);

		VerticalLayout innerDropTarget = new VerticalLayout();
		innerDropTarget.add(new Span("Inner drop target"));
		innerDropTarget.addClassName("inner-drop");

		DropTargetExtension<VerticalLayout> innerDropTargetExtension = DropTargetExtension.extend(innerDropTarget);
		innerDropTargetExtension.addDragEnterListener(event -> Notification.show("Event dragenter inner", DURATION, POSITION));
		innerDropTargetExtension.addDragLeaveListener(event -> Notification.show("Event dragleave inner", DURATION, POSITION));
		innerDropTargetExtension.addDropListener(event -> Notification.show("Event drop inner", DURATION, POSITION));

		dropTarget.add(innerDropTarget);

		setClassName("main-layout");
	}

	public static class TestComponent extends HorizontalLayout {
		public TestComponent(int i) {
			addClassName("drag-source");
			add(new Span("Dragabble " + i));
		}
	}

}
