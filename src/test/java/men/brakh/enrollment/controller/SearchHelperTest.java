package men.brakh.enrollment.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.*;
import men.brakh.enrollment.model.Dto;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SearchHelperTest {

    private final Gson gson = new GsonBuilder().serializeNulls().create();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class InnerClass {
        private String ia;
        private int ib;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TestClass implements Dto {
        private Integer id;
        private String a;
        private int b;
        private double c;
        private InnerClass innerClass = new InnerClass();
    }

    @Test
    public void availableFields() {
        SearchHelper<TestClass> searchHelper = new SearchHelper<>();

        List<String> expected = Arrays.asList(
                "id",
                "a",
                "b",
                "c",
                "innerClass.ia",
                "innerClass.ib"
        );

        List<String> acutal = searchHelper.availableFields(Arrays.asList(new TestClass()));

        assertEquals(expected, acutal);
    }

    @Test
    public void isFieldEquals() {
        SearchHelper<TestClass> searchHelper = new SearchHelper<>();


        TestClass testClass = TestClass.builder()
                .a("AAA")
                .b(12)
                .c(1.2)
                .id(123)
                .innerClass(InnerClass.builder()
                        .ia("DDD")
                        .ib(123)
                        .build())
                .build();

        JsonObject jsonObject = (JsonObject) gson.toJsonTree(testClass);

        assertTrue(searchHelper.isFieldEquals(jsonObject, "a", "AAA"));
        assertTrue(searchHelper.isFieldEquals(jsonObject, "b", "12"));
        assertFalse(searchHelper.isFieldEquals(jsonObject, "b", "13"));
        assertTrue(searchHelper.isFieldEquals(jsonObject, "innerClass.ia", "DDD"));



    }

    @Test
    public void search() {
        SearchHelper<TestClass> searchHelper = new SearchHelper<>();

        List<TestClass> list = Arrays.asList(
                TestClass.builder()
                        .a("AAA")
                        .b(12)
                        .c(1.2)
                        .id(123)
                        .innerClass(InnerClass.builder()
                                .ia("DDD")
                                .ib(123)
                                .build())
                        .build()
                ,
                TestClass.builder()
                        .a("BBB")
                        .b(13)
                        .c(2.2)
                        .id(144)
                        .innerClass(InnerClass.builder()
                                .ia("DDDE")
                                .ib(1)
                                .build())
                        .build(),
                TestClass.builder()
                        .a("AAA")
                        .b(1241)
                        .c(133.3)
                        .id(1488)
                        .innerClass(InnerClass.builder()
                                .ia("KEK")
                                .ib(123)
                                .build())
                        .build()
        );
        Map<String, String> filters = new HashMap<String, String>() {{
            put("a", "AAA");
            put("innerClass.ib", "123");
        }};


        assertEquals(searchHelper.search(list, filters, TestClass.class, "a").size(), 2);
    }
}