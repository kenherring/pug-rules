package com.pug.rules;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.rule.RuleKey;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pug.rules.DoNotLogPifi;

public class DoNotLogPifiTest extends AbstractTest {
  private RuleKey ruleKey;

  @BeforeTest
  public void init() {
    ruleKey = RuleKey.parse("acme-main:NoOpRule");
  }

  @Test
  public void test1() {
    InputFile inputFile = getInputFile("DoNotLogPifi.p");
    DoNotLogPifi rule = new DoNotLogPifi();
    rule.setContext(ruleKey, context, null);
    rule.initialize();
    rule.sensorExecute(inputFile, getParseUnit(inputFile));

    List<Integer> lines = context.allIssues().stream().map(
        issue -> issue.primaryLocation().textRange().start().line()).sorted().collect(Collectors.toList());
    System.out.println(lines);
    // Assert.assertEquals(lines, Arrays.asList(3));
  }

}
