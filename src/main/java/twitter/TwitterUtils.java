package twitter;

import org.asynchttpclient.*;
import rx.Observable;

import java.io.ByteArrayOutputStream;

import static java.util.Objects.requireNonNull;

public class TwitterUtils {
    public static Observable<String> streamingRequestToObservable(BoundRequestBuilder request) {
        requireNonNull(request);

        return Observable.create(observable -> {
            observable.onStart();

            request.execute(new AsyncHandler<String>() {
                private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                @Override
                public void onThrowable(Throwable throwable) {
                    System.err.println("Error: " + throwable.toString());
                }

                @Override
                public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) throws Exception {
                    byte[] bytes = httpResponseBodyPart.getBodyPartBytes();
                    for (int i = 0; i < bytes.length - 1; i++) {
                        if (bytes[i] == '\r' && bytes[i + 1] == '\n') {
                            observable.onNext(outputStream.toString());
                            outputStream = new ByteArrayOutputStream();
                            i++;
                        } else {
                            outputStream.write(bytes[i]);
                        }
                    }
                    // Write last byte at the end.
                    outputStream.write(bytes[bytes.length - 1]);

                    return State.CONTINUE;
                }

                @Override
                public State onStatusReceived(HttpResponseStatus httpResponseStatus) throws Exception {
                    System.out.println("Status received: " + httpResponseStatus.getStatusCode());
                    if (httpResponseStatus.getStatusCode() != 200) {
                        return State.ABORT;
                    }
                    return State.CONTINUE;
                }

                @Override
                public State onHeadersReceived(HttpResponseHeaders httpResponseHeaders) throws Exception {
                    return State.CONTINUE;
                }

                @Override
                public String onCompleted() throws Exception {
                    observable.onCompleted();
                    return "Completed!";
                }
            });
        });
    }
}
