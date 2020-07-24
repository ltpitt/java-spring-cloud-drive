package it.davidenastri.clouddrive.services;

import it.davidenastri.clouddrive.mapper.CredentialMapper;
import it.davidenastri.clouddrive.mapper.UserMapper;
import it.davidenastri.clouddrive.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final UserMapper userMapper;
    private final CredentialMapper credentialMapper;

    public CredentialService(UserMapper userMapper, CredentialMapper credentialMapper) {
        this.userMapper = userMapper;
        this.credentialMapper = credentialMapper;
    }

    public int create(Credential credential, String username) {
        return credentialMapper.create(
                new Credential(null, credential.getUrl(),
                        credential.getUsername(), credential.getKey(), credential.getPassword(),
                        userMapper.getUser(username).getUserId()));
    }

    public int update(Credential credential, String username) {
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

}
