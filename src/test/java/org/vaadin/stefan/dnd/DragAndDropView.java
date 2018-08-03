package org.vaadin.stefan.dnd;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.dnd.drag.DragSourceExtension;
import org.vaadin.stefan.dnd.drop.DropTargetExtension;

/**
 * The main view contains a button and a template element.
 */
@Route("")
@HtmlImport("frontend://styles/shared-styles.html")
public class DragAndDropView extends VerticalLayout {
	public DragAndDropView() {
		TestComponent d1 = new TestComponent(1);
		DragSourceExtension<TestComponent> extend1 = DragSourceExtension.extend(d1);
		TestComponent d2 = new TestComponent(2);
		DragSourceExtension<TestComponent> extend2 = DragSourceExtension.extend(d2);

		extend1.addDragStartListener(event -> System.out.println("drag start for 1"));
		extend2.addDragStartListener(event -> System.out.println("drag start for 2"));

		VerticalLayout dropTarget = new VerticalLayout();

		DropTargetExtension<VerticalLayout> dropTargetExtension = DropTargetExtension.extend(dropTarget);
		dropTargetExtension.addDragEnterListener(event -> System.out.println(event.getComponent() + " enter " + dropTargetExtension));
		dropTargetExtension.addDragOverListener(event -> System.out.println(event.getComponent() + " overflies " + dropTargetExtension));
		dropTargetExtension.addDragLeaveListener(event -> System.out.println(event.getComponent() + " leaves " + dropTargetExtension));
		dropTargetExtension.addDropListener(event -> System.out.println(event.getComponent() + " drops on " + dropTargetExtension));

		d1.getStyle().set("border", "1px solid gray");
		d2.getStyle().set("border", "1px solid gray");
		dropTarget.getStyle().set("border", "1px solid black");
		dropTarget.setHeight("500px");
		dropTarget.setWidth("500px");
		add(d1, d2, dropTarget);

		VerticalLayout innerDropTarget = new VerticalLayout();

		DropTargetExtension<VerticalLayout> innerDropTargetExtension = DropTargetExtension.extend(innerDropTarget);
		innerDropTargetExtension.addDragEnterListener(event -> System.out.println(event.getComponent() + " enter inner " + innerDropTargetExtension));
		innerDropTargetExtension.addDragOverListener(event -> System.out.println(event.getComponent() + " overflies inner " + innerDropTargetExtension));
		innerDropTargetExtension.addDragLeaveListener(event -> System.out.println(event.getComponent() + " leaves inner " + innerDropTargetExtension));
		innerDropTargetExtension.addDropListener(event -> System.out.println(event.getComponent() + " drops on inner " + innerDropTargetExtension));

		innerDropTarget.getStyle().set("border", "1px dotted black");
		innerDropTarget.setHeight("300px");
		innerDropTarget.setWidth("300px");

		dropTarget.add(innerDropTarget);

		setClassName("main-layout");
	}

	public static class TestComponent extends HorizontalLayout {
		public TestComponent(int i) {
			add(new Span("Hello"));
			add(new Button("World", event -> Notification.show("CLICK")));
			add(new TextField("Its me, " + i));
		}
	}

}
