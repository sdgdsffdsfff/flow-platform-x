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

package com.flowci.core.credential.service;

import com.flowci.core.credential.dao.CredentialDao;
import com.flowci.core.credential.domain.Credential;
import com.flowci.core.credential.domain.RSAKeyPair;
import com.flowci.core.user.CurrentUserHelper;
import com.flowci.exception.DuplicateException;
import com.flowci.exception.StatusException;
import com.flowci.util.CipherHelper.RSA;
import com.flowci.util.CipherHelper.StringKeyPair;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @author yang
 */
@Log4j2
@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    private CredentialDao credentialDao;

    @Autowired
    private CurrentUserHelper currentUserHelper;

    @Override
    public List<Credential> list() {
        return credentialDao.findAllByCreatedByOrderByCreatedAt(currentUserHelper.getUserId());
    }

    @Override
    public Credential get(String name) {
        return credentialDao.findByNameAndCreatedBy(name, currentUserHelper.getUserId());
    }

    @Override
    public Credential createRSA(String name) {
        try {
            String email = currentUserHelper.get().getEmail();

            KeyPair keyPair = RSA.buildKeyPair(RSA.SIZE_1024);
            StringKeyPair stringKeyPair = RSA.encodeAsOpenSSH(keyPair, email);

            return createRSA(name, stringKeyPair);
        } catch (NoSuchAlgorithmException | IOException e) {
            log.error(e.getMessage());
            throw new StatusException("Unable to generate RSA key pair");
        }
    }

    @Override
    public Credential createRSA(String name, StringKeyPair rasKeyPair) {
        try {
            Date now = Date.from(Instant.now());
            RSAKeyPair rsaKeyPair = new RSAKeyPair(rasKeyPair);
            rsaKeyPair.setName(name);
            rsaKeyPair.setUpdatedAt(now);
            rsaKeyPair.setCreatedAt(now);
            rsaKeyPair.setCreatedBy(currentUserHelper.getUserId());
            return credentialDao.insert(rsaKeyPair);
        } catch (DuplicateKeyException e) {
            throw new DuplicateException("Credential name {0} is already defined", name);
        }
    }
}
