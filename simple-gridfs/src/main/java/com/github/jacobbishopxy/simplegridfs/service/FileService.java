package com.github.jacobbishopxy.simplegridfs.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.github.jacobbishopxy.simplegridfs.model.LoadFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  @Autowired
  private GridFsTemplate template;

  @Autowired
  private GridFsOperations operations;

  public String addFile(MultipartFile upload) throws IOException {
    DBObject metadata = new BasicDBObject();
    // we can add more metadata here
    metadata.put("fileSize", upload.getSize());
    Object fileID = template.store(upload.getInputStream(), upload.getOriginalFilename(),
        upload.getContentType(), metadata);

    return fileID.toString();
  }

  public List<GridFSFile> checkFileList(PageRequest page) {
    Query query = new Query();
    if (page != null) {
      query.with(page);
    }
    GridFSFindIterable gridFSIter = template.find(query);

    List<GridFSFile> res = new ArrayList<>();

    gridFSIter.forEach(f -> res.add(f));

    return res;
  }

  public List<GridFSFile> checkFileList(PageRequest page, String regex) {
    Query query = new Query(Criteria.where("filename").regex(regex));
    if (page != null) {
      query.with(page);
    }
    GridFSFindIterable gridFSIter = template.find(query);

    List<GridFSFile> res = new ArrayList<>();

    gridFSIter.forEach(f -> res.add(f));

    return res;
  }

  public void deleteFile(String id) {
    GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));
    if (gridFSFile == null) {
      throw new IllegalArgumentException("File not found");
    }
    template.delete(new Query(Criteria.where("_id").is(id)));
  }

  public LoadFile downloadFile(String id) throws IOException {
    GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));
    LoadFile loadFile = new LoadFile();

    if (gridFSFile != null && gridFSFile.getMetadata() != null) {
      loadFile.setFilename(gridFSFile.getFilename());
      loadFile.setFileType(gridFSFile.getMetadata().get("_contentType").toString());
      loadFile.setFileSize(Integer.parseInt(gridFSFile.getMetadata().get("fileSize").toString()));
      loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
    }

    return loadFile;
  }

  public LoadFile viewImage(String id) throws Exception {
    GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(id)));
    LoadFile loadFile = new LoadFile();

    if (gridFSFile != null && gridFSFile.getMetadata() != null) {
      loadFile.setFilename(gridFSFile.getFilename());
      String fileType = gridFSFile.getMetadata().get("_contentType").toString();
      if (!fileType.startsWith("image")) {
        throw new Exception("File is not an image");
      }
      loadFile.setFileType(fileType);
      loadFile.setFileSize(Integer.parseInt(gridFSFile.getMetadata().get("fileSize").toString()));
      loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));
    }

    return loadFile;
  }

}
