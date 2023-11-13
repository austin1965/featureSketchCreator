package com.featuresketch.featuresketchcreator;

import java.util.Map;

public interface InstanceVariableUsageFinder {

    Map<String, Map<String, Integer>> getInstanceVariableUsageByMethod(String file);
}
