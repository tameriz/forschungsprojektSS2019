/*
 * ExampleInstrumentedTest.java
 *
 * Created on 2019-07-04
 *
 * Copyright (C) 2019 Volkswagen AG, All rights reserved.
 */

package hs.f.forschungsprojektss2019;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest{
    @Test
    public void useAppContext(){
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("hs.f.forschungsprojektss2019", appContext.getPackageName());
    }
}
