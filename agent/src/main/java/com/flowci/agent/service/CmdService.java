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

import com.flowci.agent.domain.AgentExecutedCmd;
import com.flowci.agent.domain.AgentReceivedCmd;
import com.flowci.domain.Cmd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author yang
 */
public interface CmdService {

    Cmd get(String id);

    Page<String> getLogs(String id, Pageable pageable);

    Page<AgentReceivedCmd> listReceivedCmd(int page, int size);

    Page<AgentExecutedCmd> listExecutedCmd(int page, int size);

    void execute(Cmd cmd);

    void onCmdReceived(Cmd cmd);

}
