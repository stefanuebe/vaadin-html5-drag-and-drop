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
public class DragAndDropView extends VerticalLayout {
	public DragAndDropView() {
		TestComponent d1 = new TestComponent(1);
		DragSourceExtension<TestComponent> extend1 = DragSourceExtension.extend(d1);
		TestComponent d2 = new TestComponent(2);
		DragSourceExtension<TestComponent> extend2 = DragSourceExtension.extend(d2);

		extend1.addDragStartListener(event -> System.out.println("drag start for 1"));
		extend2.addDragStartListener(event -> System.out.println("drag start for 2"));

		VerticalLayout outerDrop = new VerticalLayout();

		DropTargetExtension<VerticalLayout> outerDropExtend = DropTargetExtension.extend(outerDrop);
		outerDropExtend.addDragEnterListener(event -> System.out.println(event.getComponent() + " enter " + outerDropExtend));
		outerDropExtend.addDropListener(event -> System.out.println(event.getComponent() + " drops on " + outerDropExtend));

		d1.getStyle().set("border", "1px solid gray");
		d2.getStyle().set("border", "1px solid gray");
		outerDrop.getStyle().set("border", "1px solid black");
		outerDrop.setHeight("500px");
		outerDrop.setWidth("500px");
		add(d1, d2, outerDrop);

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
