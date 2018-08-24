# HTML5 Drag and Drop Extension

This addon allows the activation of HTML5 drag and drop functionality for Vaadin 10 components. 
It is activated via Java and provides several listeners to handle dnd events server side.

## Development instructions

The activation of the dnd feature orientates on the Java 8 API by "extending" a given component.

```
// Activate DnD support for mobile devices (like iOS). Activates Polyfill support.
DndActivator.activateMobileDnd(this);

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

## Styling

Dragged elements css classes are extended with a `dragged` class name, that can be used for styling the source element, when the dragging start. Also there will be a element tag specific class like `div-dragged` if the draggable is a Div element.
These classes are removed as soon, as the dragging stops.

For the drop target there will be the permanent classes `droptarget` and element-tagname + `-droptarget` (like `droptarget div-droptarget`). As soon as a dragged element enters the drop area, the classes `dragover` and element-tag + `-dragover` will be added to the drop target.

Other class names might be added by yourself as needed.

## Todos

- extending events to provide additional client side information like Mouse events, drop target, etc.
- disabling dragging, when click and hold a inner component of the draggable component (e.g.so that you might still mark text)
