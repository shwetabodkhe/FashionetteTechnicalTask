package com.ciklum.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class JasonDataProviders {

    @DataProvider(name = "DataProvider")
    public Object[][] jasonDataProvider(Method testCase){
        String testcaseName = testCase.getName();
        String testDataFileName = "src/test/resources/testData/" + testcaseName + ".json";
        List<Map<String, String>> testData;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            testData = objectMapper.readValue(new File(testDataFileName), new TypeReference<List<Map<String, String>>>() {});
        }
        catch (IOException e) {
            throw new RuntimeException(String.format("Getting exception while reading a file(%s)", testDataFileName), e);
        }

        int rowSize = testData.size();
        Object[][] objArr = new Object[rowSize][1];
        for (int i = 0; i < rowSize ; i++) {
            objArr[i][0] = testData.get(i);
        }

        return objArr;
    }

}
