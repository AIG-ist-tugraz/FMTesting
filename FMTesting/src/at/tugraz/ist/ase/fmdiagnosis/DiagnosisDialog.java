package at.tugraz.ist.ase.fmdiagnosis;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.javatuples.Pair;

public class DiagnosisDialog extends Dialog {

	protected DiagnosisDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
        
		List<Pair<String, List<Pair<String, String>>>> diagnosis = Diagnoser.getLatestDiagnosis();
		if (diagnosis == null) {
			return container;
		}
        
		//https://www.eclipse.org/articles/Article-TreeViewer/TreeViewerArticle.htm
		TreeViewer diagnosisTreeViewer = new TreeViewer(parent);
		diagnosisTreeViewer.setContentProvider(new DiagnosisContentProvider());
		diagnosisTreeViewer.setLabelProvider(new DiagnosisLabelProvider());
		diagnosisTreeViewer.setInput(new DiagnosisTree(diagnosis));
		diagnosisTreeViewer.expandAll();

		return container;
    }

    // overriding this methods allows you to set the
    // title of the custom dialog
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("Diagnosis results");
    }

    @Override
    protected Point getInitialSize() {
        return new Point(450, 900);
    }
}
