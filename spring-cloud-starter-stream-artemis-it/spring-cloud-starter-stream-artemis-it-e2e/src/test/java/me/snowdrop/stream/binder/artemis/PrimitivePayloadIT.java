/*
 * Copyright 2016-2018 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.snowdrop.stream.binder.artemis;

import me.snowdrop.stream.binder.artemis.application.StreamApplication;
import me.snowdrop.stream.binder.artemis.listeners.IntegerStreamListener;
import me.snowdrop.stream.binder.artemis.sources.IntegerStreamSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = StreamApplication.class,
        properties = {
                "spring.cloud.stream.bindings.output.destination=primitive-destination",
                "spring.cloud.stream.bindings.input.destination=primitive-destination"
        }
)
@Import({ IntegerStreamSource.class, IntegerStreamListener.class })
public class PrimitivePayloadIT {

    @Autowired
    private IntegerStreamSource source;

    @Autowired
    private IntegerStreamListener listener;

    @Test
    public void shouldSupportPrimitivePayload() {
        source.send(10);

        await().atMost(5, SECONDS)
                .until(() -> listener.getPayload() == 10);
    }
}
