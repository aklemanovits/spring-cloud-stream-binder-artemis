/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
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

package me.snowdrop.stream.binder.artemis.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:gytis@redhat.com">Gytis Trikleris</a>
 */
@EnableBinding(Source.class)
public class Sender {

    private final Logger logger = Logger.getLogger(Sender.class.getSimpleName());

    private final Source source;

    @Autowired
    public Sender(Source source) {
        this.source = source;
    }

    public void send(Object payload) {
        send(payload, Collections.emptyMap());
    }

    public void send(Object payload, Map<String, Object> headers) {
        logger.info(String.format("send payload='%s' with headers='%s'", payload, headers));

        MessageBuilder<Object> messageBuilder = MessageBuilder.withPayload(payload);
        headers.forEach(messageBuilder::setHeader);
        Message message = messageBuilder.build();
        source.output()
                .send(message);
    }

}
