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

package com.flowci.core.plugin.domain;

import com.flowci.domain.Variable;
import com.flowci.domain.Variable.ValueType;
import com.flowci.domain.Version;
import com.flowci.util.YamlHelper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.yaml.snakeyaml.Yaml;

/**
 * @author yang
 */
public class PluginParser {

    public static Plugin parse(InputStream is) {
        Yaml yaml = YamlHelper.create(PluginWrapper.class);
        PluginWrapper load = yaml.load(is);
        return load.toPlugin();
    }

    @NoArgsConstructor
    private static class PluginWrapper {

        @NonNull
        public String name;

        @NonNull
        public String version;

        public List<VariableWrapper> inputs;

        public Boolean allow_failure;

        @NonNull
        public String script;

        public Plugin toPlugin() {
            Plugin plugin = new Plugin(name, Version.parse(version));
            plugin.setScript(script);

            if (!Objects.isNull(allow_failure)) {
                plugin.setAllowFailure(allow_failure);
            }

            if (Objects.isNull(inputs)) {
                return plugin;
            }

            plugin.setInputs(new ArrayList<>(inputs.size()));
            for (VariableWrapper wrapper : inputs) {
                plugin.getInputs().add(wrapper.toVariable());
            }

            return plugin;
        }
    }

    @NoArgsConstructor
    private static class VariableWrapper {

        @NonNull
        public String name;

        public String alias;

        @NonNull
        public String type;

        @NonNull
        public Boolean required;

        public Variable toVariable() {
            Variable var = new Variable(name, ValueType.valueOf(type.toUpperCase()));
            var.setRequired(required);
            var.setAlias(alias);
            return var;
        }
    }

}
