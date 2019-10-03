package com.example.androidpopularlibraries;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.androidpopularlibraries.room.IRoomModelDao;
import com.example.androidpopularlibraries.room.RoomDB;
import com.example.androidpopularlibraries.room.RoomModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static RoomDB database;
    private IRoomModelDao usersDao;


    @Before
    public void createDb() throws Exception {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry
                .getInstrumentation().getContext(), RoomDB.class).build();
        usersDao = database.dao();
    }

    @Test
    public void whenInsetUserThenReadTheSameOne() throws Exception {
        List<RoomModel> users = RoomTestHelper.createListOfUsers(1);
        usersDao.insertAll(users);

        List<RoomModel> roomDbList = usersDao.getAll();

        assertEquals(1, roomDbList.size());
        assertTrue(RoomTestHelper.listsAreIdentical(users, roomDbList));
    }

    @Test
    public void whenInsertUsersThenReadThem() throws Exception {
        List<RoomModel> users = RoomTestHelper.createListOfUsers(5);
        usersDao.insertAll(users);
        assertEquals(5, usersDao.getAll().size());
    }

    @Test
    public void whenDeleteAllUsersThenReadThem() throws Exception {
        List<RoomModel> users = RoomTestHelper.createListOfUsers(5);
        usersDao.insertAll(users);
        usersDao.deleteAll();
        assertTrue(usersDao.getAll().isEmpty());
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.androidpopularlibraries", appContext.getPackageName());
    }
}
