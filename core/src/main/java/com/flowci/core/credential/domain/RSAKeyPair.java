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

package com.flowci.core.credential.domain;

import com.flowci.util.CipherHelper.StringKeyPair;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author yang
 */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "rsa_keypair")
public final class RSAKeyPair extends Credential {

    private String publicKey;

    private String privateKey;

    public RSAKeyPair(StringKeyPair pair) {
        this.publicKey = pair.getPublicKey();
        this.privateKey = pair.getPrivateKey();
    }
}
