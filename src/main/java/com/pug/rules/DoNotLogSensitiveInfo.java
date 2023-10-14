package com.pug.rules;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.openedge.api.checks.OpenEdgeProparseCheck;
import org.sonar.plugins.openedge.api.model.SqaleConstantRemediation;
import org.prorefactor.core.ABLNodeType;
import org.prorefactor.core.JPNode;
import org.prorefactor.core.nodetypes.FieldRefNode;
import org.prorefactor.treeparser.ParseUnit;
import org.prorefactor.treeparser.symbols.FieldContainer;

@Rule(priority = Priority.INFO, name = "Do not write sensitive info to log files", tags = {"PugChallenge"})
@SqaleConstantRemediation(value = "5min")
public class DoNotLogSensitiveInfo extends OpenEdgeProparseCheck {

  @Override
  public void execute(InputFile file, ParseUnit unit) {

    for (JPNode node : unit.getTopNode().query(ABLNodeType.FIELD_REF)) {
        System.out.println(node);

        FieldRefNode fRefNode = (FieldRefNode) node;
        System.out.println(node);

        FieldContainer fCont = fRefNode.getFieldContainer();
        System.out.println(fCont);
    }
  }

}
