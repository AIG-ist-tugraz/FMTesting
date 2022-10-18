package at.tugraz.ist.ase.fmdiagnosis;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import static at.tugraz.ist.ase.fmdiagnosis.debug.DebugInfo.*;

import java.util.EnumSet;

import at.tugraz.ist.ase.fma.anomaly.AnomalyType;
import at.tugraz.ist.ase.fmdiagnosis.debug.DebugInfo.State;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DiagnosisView extends ViewPart {
	private Composite parent;

	private Button startDiagButton;
	private Label instructionLabel;
	private Label numFeaturesLabel;
	
	private Button checkVoid;
	private Button checkDead;
	private Button checkFullMand;
	private Button checkCondDead;
	private Button checkFalseOpt;
	private Button checkRedundant;
	
	private TreeViewer diagnosisTreeViewer;
	
	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		
//		RowLayout rowLayout = new RowLayout();
//        rowLayout.wrap = false;
//        rowLayout.pack = false;
//        rowLayout.justify = true;
//        rowLayout.type = SWT.VERTICAL;
//        rowLayout.marginLeft = 5;
//        rowLayout.marginTop = 5;
//        rowLayout.marginRight = 5;
//        rowLayout.marginBottom = 5;
//        rowLayout.spacing = 0;
		GridLayout gridLayout = new GridLayout(2, false);
        gridLayout.marginLeft = 10;
        gridLayout.marginTop = 10;
        gridLayout.marginRight = 10;
        gridLayout.marginBottom = 10;
        gridLayout.horizontalSpacing = 10;
        gridLayout.verticalSpacing = 10;
        
        parent.setLayout(gridLayout);
        
//      https://www.eclipse.org/articles/Article-Understanding-Layouts/Understanding-Layouts.htm
        GridData gridData = new GridData();
        gridData.verticalSpan = 10;
        gridData.grabExcessVerticalSpace = true; // TODO Is this what I want or should it be false?
        
		
		startDiagButton = new Button(parent, SWT.WRAP);
		startDiagButton.setText("Start Diagnosis");
		startDiagButton.addMouseListener(new DiagnosisMouseListener(this));

		instructionLabel = new Label(parent, SWT.WRAP);
		instructionLabel.setText("Check the analysis operations to be performed on the FM!");
		
		checkVoid = new Button(parent, SWT.WRAP | SWT.CHECK);
		checkVoid.setText("Void");
		checkDead = new Button(parent, SWT.WRAP | SWT.CHECK);
		checkDead.setText("Dead features");
		checkFullMand = new Button(parent, SWT.WRAP | SWT.CHECK);
		checkFullMand.setText("Full mandatory");
		checkCondDead = new Button(parent, SWT.WRAP | SWT.CHECK);
		checkCondDead.setText("Conditionally dead");
		checkFalseOpt = new Button(parent, SWT.WRAP | SWT.CHECK);
		checkFalseOpt.setText("False optional");
		checkRedundant = new Button(parent, SWT.WRAP | SWT.CHECK);
		checkRedundant.setText("Redundant constraints");
	}
	
	private void setNumFeatures(Integer newNum) {
		numFeaturesLabel.setText(newNum.toString() + " Features");
		debugInfo(State.INFO, "Changed number of features");
	}
	
	public EnumSet<AnomalyType> getChosenAnalysisTypes() {
		EnumSet<AnomalyType> chosenOptions = EnumSet.noneOf(AnomalyType.class);
		if (checkVoid.getSelection()) {
			chosenOptions.add(AnomalyType.VOID);
		}
		if (checkDead.getSelection()) {
			chosenOptions.add(AnomalyType.DEAD);
		}
		if (checkFullMand.getSelection()) {
			chosenOptions.add(AnomalyType.FULLMANDATORY);
		}
		if (checkCondDead.getSelection()) {
			chosenOptions.add(AnomalyType.CONDITIONALLYDEAD);
		}
		if (checkFalseOpt.getSelection()) {
			chosenOptions.add(AnomalyType.FALSEOPTIONAL);
		}
		if (checkRedundant.getSelection()) {
			chosenOptions.add(AnomalyType.REDUNDANT);
		}
		
		debugInfo(State.INFO, "Options: " + chosenOptions.toString());
		
		if (chosenOptions.isEmpty()) {
			throw new UnsupportedOperationException("Please choose at least one analysis!");
		}
		
		return chosenOptions;
	}
	
	public void setDiagnosis() {
		//diagnosisTreeViewer.setInput(new DiagnosisTree(Diagnoser.getLatestDiagnosis()));
		DiagnosisDialog dialog = new DiagnosisDialog(parent.getShell());
		dialog.open();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub	
	}
	
	public Shell getParentShell() {
		return parent.getShell();
	}

}
