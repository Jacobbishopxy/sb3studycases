package com.github.jacobbishopxy.simplegridfs.controller;

import com.github.jacobbishopxy.simplegridfs.model.LoadFile;
import com.github.jacobbishopxy.simplegridfs.service.FileService;
import com.mongodb.client.gridfs.model.GridFSFile;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class FileController {

  @Autowired private FileService fileService;

  @GetMapping("/files")
  public List<GridFSFile> getAllFilesData(
      @RequestParam("page") Integer page, @RequestParam("size") Integer size,
      @RequestParam(value = "regex", required = false) String regex) {

    if (page == null || size == null) {
      throw new IllegalArgumentException("page and size must be provided");
    }
    if (regex != null) {
      return fileService.checkFileList(PageRequest.of(page, size), regex);
    } else {
      return fileService.checkFileList(PageRequest.of(page, size));
    }
  }

  @PostMapping("/upload")
  public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file)
      throws IOException {
    return new ResponseEntity<>(fileService.addFile(file), HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public void delete(@PathVariable("id") String id) {
    fileService.deleteFile(id);
  }

  @GetMapping("/download/{id}")
  public ResponseEntity<ByteArrayResource> download(@PathVariable String id)
      throws IOException {
    LoadFile loadFile = fileService.downloadFile(id);
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(loadFile.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + loadFile.getFilename() + "\"")
        .body(new ByteArrayResource(loadFile.getFile()));
  }

  @GetMapping(value = "/image/{id}",
              produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
                          MediaType.IMAGE_GIF_VALUE})
  public @ResponseBody byte[] image(@PathVariable String id) throws Exception {
    return fileService.viewImage(id).getFile();
  }
}
