/*
 *  Copyright 2019 Qameta Software OÜ
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
package io.qameta.allure.testng.samples;

import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.IConfigurationListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * @author charlie (Dmitry Baev).
 */
@Listeners(AttachmentsOnFailedConfigurationTest.class)
public class AttachmentsOnFailedConfigurationTest implements IConfigurationListener {


    @BeforeTest(description = "failed configuration")
    public void setUp() {
        throw new RuntimeException("fail");
    }

    @Test
    public void someTest() {
        Assert.assertTrue(true);
    }

    @Override
    public void onConfigurationFailure(ITestResult iTestResult) {
        Allure.addAttachment("String attachment", "text/plain", "<p>HELLO</p>");
    }

    @Override
    public void onConfigurationSuccess(ITestResult iTestResult) {
        //do nothing
    }

    @Override
    public void onConfigurationSkip(ITestResult iTestResult) {
        //do nothing
    }
}
