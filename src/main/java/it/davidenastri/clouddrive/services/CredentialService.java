package it.davidenastri.clouddrive.services;

import it.davidenastri.clouddrive.mapper.CredentialMapper;
import it.davidenastri.clouddrive.mapper.UserMapper;
import it.davidenastri.clouddrive.model.Credential;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final EncryptionService encryptionService;

    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;
    Logger logger = LoggerFactory.getLogger(CredentialService.class);

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int create(Credential credential, String username) {
        credential = encryptPassword(credential);
        return credentialMapper.create(
                new Credential(null, credential.getUrl(),
                        credential.getUsername(), credential.getKey(), credential.getPassword(),
                        userMapper.getUser(username).getUserId()));
    }

    public int update(Credential credential, String username) {
        credential = encryptPassword(credential);
        return credentialMapper.update(
                new Credential(credential.getCredentialid(), credential.getUrl(),
                        credential.getUsername(), credential.getKey(), credential.getPassword(),
                        userMapper.getUser(username).getUserId()));
    }

    public Credential get(int credentialid, String username) {
        return credentialMapper.get(credentialid, userMapper.getUser(username).getUserId());
    }

    public List<Credential> getAll(String username) {
        return credentialMapper.getAll(userMapper.getUser(username).getUserId());
    }

    public int delete(int credentialid) {
        return credentialMapper.delete(credentialid);
    }

    private Credential encryptPassword(Credential credential) {
        String key = RandomStringUtils.random(16, true, true);
        credential.setKey(key);
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), key));
        return credential;
    }

    public Credential decryptPassword(Credential credential) {
        credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return credential;
    }


}
