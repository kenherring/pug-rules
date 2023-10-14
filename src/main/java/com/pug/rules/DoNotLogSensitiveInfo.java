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
import org.prorefactor.treeparser.symbols.FieldContainer;

@Rule(priority = Priority.INFO, name = "Do not write sensitive info to log files", tags = {"PugChallenge"})
@SqaleConstantRemediation(value = "5min")
public class DoNotLogSensitiveInfo extends OpenEdgeProparseCheck {

  ArrayList<String> SensitiveMap = new ArrayList<String>();

  private void createSensitiveList () {
    SensitiveMap.add("sp2k.Employee.EmpNum");
    SensitiveMap.add("sp2k.Employee.Name");
    SensitiveMap.add("sp2k.Invoice.InvoiceNum");
    SensitiveMap.add("sp2k.Invoice.Address");
  }

  @Override
  public void execute(InputFile file, ParseUnit unit) {
    createSensitiveList();

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

          //Is the field in our Sensitive Info list?
          //TODO: check if the field is in the SensitiveMap list via field desc in the schema
          if (SensitiveMap.contains(fBuf.toString())) {
            reportIssue(file, errNode, "Do not write sensitive info to log files.  Field= " + fBuf);
          }
        }
    }
  }

}
