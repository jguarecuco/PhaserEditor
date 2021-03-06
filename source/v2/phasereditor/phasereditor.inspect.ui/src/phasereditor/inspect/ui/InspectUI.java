// The MIT License (MIT)
//
// Copyright (c) 2015, 2018 Arian Fornaris
//
// Permission is hereby granted, free of charge, to any person obtaining a
// copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to permit
// persons to whom the Software is furnished to do so, subject to the
// following conditions: The above copyright notice and this permission
// notice shall be included in all copies or substantial portions of the
// Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
// OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
// NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
// USE OR OTHER DEALINGS IN THE SOFTWARE.
package phasereditor.inspect.ui;

import java.nio.file.Path;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import phasereditor.inspect.core.jsdoc.IPhaserMember;
import phasereditor.inspect.core.jsdoc.PhaserJSDoc;
import phasereditor.inspect.ui.views.JsdocView;
import phasereditor.ui.PhaserEditorUI;

/**
 * @author arian
 *
 */
public class InspectUI {
	public static final String JSDOC_VIEW_ID = "phasereditor.inspect.ui.jsdoc";

	public static void showSourceCode(IPhaserMember member) {
		PhaserJSDoc jsdoc = PhaserJSDoc.getInstance();
		Path file = jsdoc.getMemberPath(member);
		if (file != null) {
			int line = member.getLine();
			int offset = member.getOffset();
			PhaserEditorUI.openJSEditor(line, offset, file);
		}
	}

	public static void showJavaDoc(IPhaserMember member) {
		try {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

			JsdocView view = (JsdocView) page.showView(InspectUI.JSDOC_VIEW_ID, null, IWorkbenchPage.VIEW_CREATE);
			view.showJsdocFor(member);
			page.activate(view);
		} catch (PartInitException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
