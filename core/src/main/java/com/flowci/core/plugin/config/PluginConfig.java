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

package com.flowci.core.plugin.config;

import com.flowci.core.config.ConfigProperties;
import com.flowci.core.helper.ThreadHelper;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author yang
 */
@Log4j2
@Configuration
public class PluginConfig {

    @Autowired
    private ConfigProperties appProperties;

    @Bean("repoCloneExecutor")
    public ThreadPoolTaskExecutor repoCloneExecutor() {
        return ThreadHelper.createTaskExecutor(2, 2, 100, "plugin-repo-clone-");
    }

    @Bean("pluginDir")
    public Path pluginDir() throws IOException {
        String workspace = appProperties.getWorkspace();
        Path pluginDir = Paths.get(workspace, "plugins");

        try {
            return Files.createDirectory(pluginDir);
        } catch (FileAlreadyExistsException ignore) {
            return pluginDir;
        }
    }
}
