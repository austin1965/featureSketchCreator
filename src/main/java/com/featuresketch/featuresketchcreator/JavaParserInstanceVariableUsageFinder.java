package com.featuresketch.featuresketchcreator;

import java.util.HashMap;
import java.util.Map;

public class JavaParserInstanceVariableUsageFinder implements InstanceVariableUsageFinder{
    @Override
    public Map<String, Map<String, Integer>> getInstanceVariableUsageByMethod(String file) {
        Map<String, Integer> variableUsages = new HashMap<>();
        variableUsages.put("name", 1);
        Map<String, Map<String, Integer>> methodUsagesofInstanceVariables = new HashMap<>();
        methodUsagesofInstanceVariables.put("getName", variableUsages);

        return methodUsagesofInstanceVariables;
    }
}
