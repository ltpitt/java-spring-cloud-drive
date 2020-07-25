package it.davidenastri.clouddrive.services;

import it.davidenastri.clouddrive.mapper.FileMapper;
import it.davidenastri.clouddrive.mapper.UserMapper;
import it.davidenastri.clouddrive.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final UserMapper userMapper;
    private final FileMapper fileMapper;

    public FileService(UserMapper userMapper, FileMapper fileMapper) {
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
    }

    public int create(MultipartFile uploadedFile, String username) throws IOException {

        File file = new File();
        file.setContenttype(uploadedFile.getContentType());
        file.setFiledata(uploadedFile.getBytes());
        file.setFilename(uploadedFile.getOriginalFilename());
        file.setFilesize(Long.toString(uploadedFile.getSize()));
        file.setUserid(userMapper.getUser(username).getUserId());
        return fileMapper.create(file);

    }

    public File get(int fileid, String username) {
        return fileMapper.get(fileid, userMapper.getUser(username).getUserId());
    }

    public List<File> getAll(String username) {
        return fileMapper.getAll(userMapper.getUser(username).getUserId());
    }

    public int delete(int fileid) {
        return fileMapper.delete(fileid);
    }

}
