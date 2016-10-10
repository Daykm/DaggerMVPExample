package com.example.daykm.daggerexample;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.daykm.daggerexample.data.CityRepository;
import com.example.daykm.daggerexample.data.remote.City;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.daykm.daggerexample", appContext.getPackageName());
    }

    @Test
    public void testParseCities() throws Exception {

        for(City city : new CityRepository(InstrumentationRegistry.getTargetContext()).getCities()) {
            System.out.println(city.name + " " + city.id);
        }

    }
}
