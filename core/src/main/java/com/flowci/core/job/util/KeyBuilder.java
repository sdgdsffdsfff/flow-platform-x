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

package com.flowci.core.job.util;

import com.flowci.core.flow.domain.Flow;
import com.flowci.core.job.domain.Job;
import com.flowci.tree.Node;
import java.text.MessageFormat;

/**
 * @author yang
 */
public class KeyBuilder {

    private final static char Splitter = '-';

    public static String buildJobKey(Flow flow, Long buildNumber) {
        return flow.getId() + Splitter + buildNumber;
    }

    public static String buildCmdKey(Job job, Node node) {
        return MessageFormat.format("{0}-{1}", job.getId(), node.getPath().getPathInStr());
    }

    private KeyBuilder() {

    }
}