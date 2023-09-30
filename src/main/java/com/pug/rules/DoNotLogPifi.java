package com.pug.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.openedge.api.checks.OpenEdgeProparseCheck;
import org.sonar.plugins.openedge.api.model.SqaleConstantRemediation;
import java.util.ArrayList;
import org.prorefactor.core.ABLNodeType;
import org.prorefactor.core.JPNode;
import org.prorefactor.core.nodetypes.FieldRefNode;
import org.prorefactor.treeparser.ParseUnit;
import org.prorefactor.treeparser.symbols.FieldBuffer;

@Rule(priority = Priority.INFO, name = "Do not write PIFI to log files", tags = {"pug-challenge"})
@SqaleConstantRemediation(value = "5min")
public class DoNotLogPifi extends OpenEdgeProparseCheck {

  ArrayList<String> PifiMap = new ArrayList<String>();

  private void createPifiList () {
    PifiMap.add("sp2k.Employee.EmpNum");
    PifiMap.add("sp2k.Employee.Name");
    PifiMap.add("sp2k.Invoice.InvoiceNum");
    PifiMap.add("sp2k.Invoice.Address");
  }

  @Override
  public void execute(InputFile file, ParseUnit unit) {
    createPifiList();

    for (JPNode node : unit.getTopNode().query(ABLNodeType.FIELD_REF)) {
        JPNode errNode = node;
        while (errNode != null && errNode.getLine() == 0) {
          errNode = errNode.getParent();
        }
        if(errNode == null) {
          continue;
        }

        FieldRefNode fRefNode = (FieldRefNode) node;
        if (fRefNode.getSymbol() instanceof FieldBuffer){
          FieldBuffer fBuf = (FieldBuffer) fRefNode.getSymbol();

          //Is the field in our PIFI list?
          //TODO: check if the field is in the PIFI list via field desc in the schema
          if (PifiMap.contains(fBuf.toString())) {
            reportIssue(file, errNode, "Do not write PIFI to log files.  Field= " + fBuf);
          }
        }
    }
  }

}
