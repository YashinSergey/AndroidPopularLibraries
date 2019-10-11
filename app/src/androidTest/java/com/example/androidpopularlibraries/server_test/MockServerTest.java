package com.example.androidpopularlibraries.server_test;


import android.app.Activity;

import androidx.test.runner.AndroidJUnit4;

import com.example.androidpopularlibraries.MainActivity;
import com.example.androidpopularlibraries.dagger.DaggerNetModule;
import com.example.androidpopularlibraries.retrofit.UserModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(AndroidJUnit4.class)
public class MockServerTest {

    private MockWebServer server;

    private static final String LOGIN = "mojombo";
    private static final String AVATAR_URL = "test_url";
    private static final String ID = "1";

    @Inject
    Single<List<UserModel>> request;

    @Before
    public void prepare() throws IOException {
        server = new MockWebServer();
        server.start();
        DaggerUserModelTestComp.builder()
                .daggerNetModule(new DaggerNetModule(){
                    @Override
                    public String provideEndpoint() {
                        return server.url("/").toString();
                    }})
                .build()
                .inject(this);
    }

    @Test
    public void getUsers_success(){
        server.enqueue(createMockResponse(LOGIN, AVATAR_URL, ID));
        TestSubscriber<Boolean> subscriber = TestSubscriber.create();

        request.subscribeWith(new DisposableSingleObserver<List<UserModel>>() {
            @Override
            public void onSuccess(List<UserModel> retrofitModels) {
                if(retrofitModels.size() == 0){
                    subscriber.onNext(false);
                    subscriber.onComplete();
                    return;
                }
                UserModel model = retrofitModels.get(0);
                boolean equal = model.getLogin().equals(LOGIN) &&
                        model.getAvatarUrl().equals(AVATAR_URL) &&
                        model.getId().equals(ID);
                subscriber.onNext(equal);
                subscriber.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }
        });
        subscriber.awaitTerminalEvent();
        subscriber.assertValue(true);
        subscriber.dispose();
    }

    private MockResponse createMockResponse(String login, String avatarUrl, String id) {
        return new MockResponse().setBody("[{\n" +
                "\"login\": \"" + login + "\",\n" +
                "\"avatar_url\": \"" + avatarUrl + "\",\n" +
                "\"id\": \"" + id + "\"\n" +
                "}]");
    }
}
