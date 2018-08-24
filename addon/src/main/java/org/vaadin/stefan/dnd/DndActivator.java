package org.vaadin.stefan.dnd;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.Page;

public class DndActivator {
	public static final void activateMobileDnd(HasElement component) {
		component.getElement().getNode().runWhenAttached(DndActivator::activateMobileDnd);
	}
	public static final void activateMobileDnd(UI ui) {
		Page page = ui.getPage();
		page.addStyleSheet("frontend://bower_components/mobile-drag-drop/release/default.css");
		page.addJavaScript("frontend://bower_components/mobile-drag-drop/release/index.min.js");
		page.addJavaScript("frontend://bower_components/mobile-drag-drop/release/scroll-behaviour.min.js");

		page.executeJavaScript("var polyfillApplied = MobileDragDrop.polyfill({" +
				"            dragImageTranslateOverride: MobileDragDrop.scrollBehaviourDragImageTranslateOverride" +
				"});" +
				"window.addEventListener( 'touchmove', function() {});" +
				"if (polyfillApplied) {" +
				"   alert('polyfill applied');" +
				"}");


	}
}
