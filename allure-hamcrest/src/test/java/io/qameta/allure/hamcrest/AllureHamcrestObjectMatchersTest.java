/*
 *  Copyright 2016-2024 Qameta Software Inc
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.qameta.allure.hamcrest;

import io.qameta.allure.model.StepResult;
import io.qameta.allure.model.TestResult;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static io.qameta.allure.test.RunUtils.runWithinTestContext;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * All tests should cover http://hamcrest.org/JavaHamcrest/tutorial "Object" section
 * <ui>
 * <li>equalTo</li>
 * <li>hasToString</li>
 * <li>instanceOf</li>
 * <li>isCompatibleType in new version typeCompatibleWith</li>
 * <li>notNullValue</li>
 * <li>nullValue</li>
 * <li>sameInstance</li>
 * </ui>
 */
@SuppressWarnings("all")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AllureHamcrestObjectMatchersTest {

    private static Stream<Arguments> testCases() {
        final String testVal = "test_test";

        return Stream.of(
                Arguments.of(
                        "thebiscuit", is(equalTo("thebiscuit")),
                        "assert \"thebiscuit\" is \"thebiscuit\""
                ),
                Arguments.of(
                        true, hasToString("TRUE"),
                        "assert \"true\" with toString() \"TRUE\""
                ),
                Arguments.of(
                        Arrays.asList("key1", "key2"), instanceOf(Iterable.class),
                        "assert \"[key1, key2]\" an instance of java.lang.Iterable"
                ),
                Arguments.of(
                        Integer.class, typeCompatibleWith(Number.class),
                        "assert \"class java.lang.Integer\" type < java.lang.Number"
                ),
                Arguments.of(
                        "test", is(notNullValue()),
                        "assert \"test\" is not null"
                ),
                Arguments.of(
                        "test", is(not(nullValue())),
                        "assert \"test\" is not null"
                ),
                Arguments.of(
                        testVal, is(sameInstance(testVal)),
                        "assert \"test_test\" is sameInstance(\"test_test\")"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void hamcrestAssertNameForObjectMatchers(Object actual, Matcher matcher, String expectedName) {
        final TestResult testResult = runWithinTestContext(
                () -> assertThat(actual, matcher),
                AllureHamcrestAssert::setLifecycle
        ).getTestResults().get(0);

        Assertions.assertThat(testResult.getSteps())
                .flatExtracting(StepResult::getName)
                .containsExactly(expectedName);
    }
}
