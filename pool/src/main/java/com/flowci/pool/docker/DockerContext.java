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

package com.flowci.pool.docker;

import com.flowci.pool.PoolContext;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yang
 */
@Getter
@Setter
public class DockerContext extends PoolContext {

    public static final String STATUS_RUNNING = "running";

    public static final String STATUS_EXITED = "exited";

    private static final String DefaultImage = "flowci/agent:latest";

    private String apiVersion;

    private String host;

    private String image = DefaultImage;

    private String containerId;

    public boolean hasContainer() {
        return !Strings.isNullOrEmpty(containerId);
    }

    public DockerConfig getConfig() {
        return DockerConfig.of(host);
    }
}
