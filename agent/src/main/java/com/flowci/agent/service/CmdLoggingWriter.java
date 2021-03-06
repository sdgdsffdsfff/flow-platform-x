/*
 * Copyright 2018 flow.ci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flowci.agent.service;

import com.flowci.domain.LogItem;
import com.flowci.agent.executor.LoggingListener;
import com.flowci.domain.Cmd;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * @author yang
 */
@Log4j2
public class CmdLoggingWriter implements LoggingListener {

    @Getter
    private final Cmd cmd;

    @Getter
    private final Path file;

    private BufferedWriter writer;

    public CmdLoggingWriter(Cmd cmd, Path file) {
        this.cmd = cmd;
        this.file = file;

        try {
            writer = Files.newBufferedWriter(this.file);
        } catch (IOException ignore) {
        }
    }

    @Override
    public void onLogging(LogItem item) {
        log.debug("Log Received : {}", item);
        try {
            writer.write(item.getContent());
            writer.newLine();
        } catch (IOException ignore) {
        }
    }

    @Override
    public void onFinish(long size) {
        if (Objects.isNull(writer)) {
            return;
        }

        try {
            writer.close();
        } catch (IOException ignore) {
        }
    }
}
