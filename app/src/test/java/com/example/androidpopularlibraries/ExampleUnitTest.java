package com.example.androidpopularlibraries;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.androidpopularlibraries.dagger.DaggerNetModule;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {

    private TestNetworkComponent component;
    private Context context;

    @Before
    public void prepare() {
        context = Mockito.mock(Context.class);
        component = DaggerTestNetworkComponent.builder()
                .daggerNetModule(new DaggerNetModule(context)).build();
    }

    @Test
    public void getNetwork_isSuccess() {
        ConnectivityManager connectivityManager = Mockito.mock(ConnectivityManager.class);

        Mockito.when(context.getSystemService(Mockito.anyString()))
                .thenReturn(connectivityManager);

        NetworkInfo infoExpected = Mockito.mock(NetworkInfo.class);
        Mockito.when(connectivityManager.getActiveNetworkInfo()).thenReturn(infoExpected);

        NetworkInfo networkInfo = component.getNetwork();
        assertNotEquals(null, networkInfo);
        assertEquals(infoExpected, networkInfo);

        Mockito.verify(context).getSystemService(Mockito.anyString());
        Mockito.verify(connectivityManager).getActiveNetworkInfo();
    }

}