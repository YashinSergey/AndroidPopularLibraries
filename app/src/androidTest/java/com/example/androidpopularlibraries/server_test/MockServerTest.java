package com.example.androidpopularlibraries.server_test;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.androidpopularlibraries.dagger.DaggerNetModule;
import com.example.androidpopularlibraries.retrofit.UserModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscription;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.HttpException;

@RunWith(AndroidJUnit4.class)
public class MockServerTest {

    private MockWebServer server;

    private static final String LOGIN = "mojombo";
    private static final String AVATAR_URL = "test_url";
    private static final int ID = 1;

    @Inject
    Single<List<UserModel>> request;

    @Before
    public void onStart() throws IOException {
        server = new MockWebServer();
        server.start();
        DaggerUserModelTestComp.builder()
                .daggerNetModule(new DaggerNetModule(InstrumentationRegistry.getInstrumentation().getTargetContext()){
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
        TestObserver<Boolean> observer = TestObserver.create();

        request.subscribeWith(new DisposableSingleObserver<List<UserModel>>() {
            @Override
            public void onSuccess(List<UserModel> retrofitModels) {
                observer.onNext(checkModel(retrofitModels));
                observer.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                observer.onError(e);
            }
        });
        observer.awaitTerminalEvent();
        observer.assertValue(true);
        observer.dispose();
    }

    private Boolean checkModel(List<UserModel> retrofitModels){
        if(retrofitModels.size() == 0){
            return false;
        }
        UserModel model = retrofitModels.get(0);
        return  model.getLogin().equals(LOGIN) &&
                model.getAvatarUrl().equals(AVATAR_URL) &&
                model.getId().equals(ID);
    }

    @Test
    public void getUsers_with_error_code() {
        server.enqueue(createErrorResponse(404, "page not found"));
        TestSubscriber<Integer> subscriber = TestSubscriber.create();

        subscriber.onSubscribe(createSubscription());

        request.subscribeWith(new DisposableSingleObserver<List<UserModel>>() {
            @Override
            public void onSuccess(List<UserModel> userModels) {
                subscriber.onNext(200);
                subscriber.onComplete();
            }
            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }
        });
        subscriber.awaitTerminalEvent();
        subscriber.assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                return (throwable instanceof HttpException) && ((HttpException) throwable).code() == 404;
            }
        });
        subscriber.dispose();
    }

    @Test
    public void getUsers_with_invalid_json(){
        server.enqueue(createErrorResponse(200, "{ \"invalid Json\" : }"));
        TestSubscriber<Integer> subscriber = TestSubscriber.create();

        subscriber.onSubscribe(createSubscription());

        request.subscribeWith(new DisposableSingleObserver<List<UserModel>>() {
            @Override
            public void onSuccess(List<UserModel> retrofitModels) {
                subscriber.onNext(200);
                subscriber.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }
        });
        subscriber.awaitTerminalEvent();
        subscriber.assertError(new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable e) throws Exception {
                return e instanceof IllegalStateException;
            }
        });
        subscriber.dispose();
    }

    private MockResponse createErrorResponse(int responseCode, String message) {
        return new MockResponse().setResponseCode(responseCode).setBody(message);
    }

    private MockResponse createMockResponse(String login, String avatarUrl, int id) {
        return new MockResponse().setBody("[{\n" +
                "\"login\": \"" + login + "\",\n" +
                "\"avatar_url\": \"" + avatarUrl + "\",\n" +
                "\"id\": \"" + id + "\"\n" +
                "}]");
    }

    private Subscription createSubscription(){
        return new Subscription() {
            @Override
            public void request(long n) {}
            @Override
            public void cancel() {}
        };
    }

    @After
    public void onFinish() {
        try {
            server.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
