# HTML5 Drag and Drop Extension

This addon allows the activation of HTML5 drag and drop functionality for Vaadin 10 components. 
It is activated via Java and provides several listeners to handle dnd events server side.

This is the first early (and also experimental) version of the addon. 

## Development instructions

The activation of the dnd feature orientates on the Java 8 API by "extending" a given component.

```
// Defining a draggable component and its extension.
TestComponent componentToDrag = new TestComponent(1);

DragSourceExtension<TestComponent> dragSource = DragSourceExtension.extend(componentToDrag);

dragSource.addDragStartListener(event -> System.out.println("drag start for " + event.getComponent()));
dragSource.addDragEndListener(event -> System.out.println("drag end for 2" + event.getComponent()));

// watch out, this one is fired all the time the drag is active and mouse is moved
//dragSource.addDragListener(event -> System.out.println("drag for " + event.getComponent()));

// Defining the drop target and its extension
VerticalLayout dropTarget = new VerticalLayout();

DropTargetExtension<VerticalLayout> dropTargetExtension = DropTargetExtension.extend(dropTarget);
dropTargetExtension.addDragEnterListener(event -> System.out.println(event.getComponent() + " enter " + dropTargetExtension));
dropTargetExtension.addDragOverListener(event -> System.out.println(event.getComponent() + " overflies " + dropTargetExtension));
dropTargetExtension.addDragLeaveListener(event -> System.out.println(event.getComponent() + " leaves " + dropTargetExtension));
dropTargetExtension.addDropListener(event -> System.out.println(event.getComponent() + " drops on " + dropTargetExtension));
```

## Todos

- adding styles to dragged elements and possible drop targets
- extending events to provide additional client side information like Mouse events, drop target, etc.
- disableing dragging, when click and hold a inner component of the draggable component (e.g.so that you might still mark text)
- adding javadocs
