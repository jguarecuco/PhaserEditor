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
package phasereditor.inspect.ui.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.part.ViewPart;

import phasereditor.inspect.core.InspectCore;
import phasereditor.inspect.core.jsdoc.IPhaserMember;
import phasereditor.inspect.ui.InspectUI;

/**
 * @author arian
 *
 */
public class PhaserTypesView extends ViewPart {

	public static final String ID = "phasereditor.inspect.ui.views.PhaserTypesView"; //$NON-NLS-1$
	private FilteredTree _filteredTree;

	public PhaserTypesView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout());

		PatternFilter filter = new PatternFilter();
		filter.setIncludeLeadingWildcard(true);

		_filteredTree = new FilteredTree(container, SWT.NONE, filter, true);

		TreeViewer treeViewer = _filteredTree.getViewer();
		treeViewer.setContentProvider(new PhaserTypesContentProvider());
		treeViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IPhaserMember) element).getName();
			}
		});

		TreeViewerColumn viewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		viewerColumn.setLabelProvider(new PhaserTypesColumnLabelProvider());
		TreeColumn column = viewerColumn.getColumn();
		column.setWidth(1000);

		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				showSourceCodeOfSelectedItem();
			}
		});

		afterCreateWidgets();

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	void showSourceCodeOfSelectedItem() {
		Object elem = _filteredTree.getViewer().getStructuredSelection().getFirstElement();
		if (elem == null) {
			return;
		}

		InspectUI.showSourceCode((IPhaserMember) elem);
	}

	private void afterCreateWidgets() {
		getViewSite().setSelectionProvider(_filteredTree.getViewer());

		_filteredTree.getViewer().setInput(InspectCore.getPhaserHelp());
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	@SuppressWarnings("unused")
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	@SuppressWarnings("unused")
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		_filteredTree.getFilterControl().setFocus();
	}

}
