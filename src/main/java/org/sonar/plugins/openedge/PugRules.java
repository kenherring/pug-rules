package org.sonar.plugins.openedge;

import org.sonar.api.Plugin;

public class PugRules implements Plugin {

  @Override
  public void define(Context context) {
    // Rules
    context.addExtension(PugRulesDefinition.class);
  }

}
