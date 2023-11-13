package com.featuresketch.featuresketchcreator;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class InstanceVariableUsageFinderTest {

    @Test
    void shouldRetrieveOneUsage() {
        //given
        String file = "src/main/java/org/javaparser/samples/BasicClass.java";
        String instanceVariableName = "name";
        String methodName = "getName";

        InstanceVariableUsageFinder classUnderTest = new JavaParserInstanceVariableUsageFinder();

        Map<String, Integer> expectedVariableUsage = new HashMap<>();
        expectedVariableUsage.put(instanceVariableName, 1);
        Map<String, Map<String, Integer>> expectedUsages = new HashMap<>();
        expectedUsages.put(methodName, expectedVariableUsage);

        //when
        Map<String, Map<String, Integer>> actualUsages = classUnderTest.getInstanceVariableUsageByMethod(file);

        //then
        assertEquals(actualUsages.keySet(), expectedUsages.keySet());
        assertEquals(actualUsages.get(methodName).keySet(), expectedUsages.get(methodName).keySet());
        assertEquals(actualUsages.get(methodName).get(instanceVariableName), expectedUsages.get(methodName).get(instanceVariableName));

    }

}
