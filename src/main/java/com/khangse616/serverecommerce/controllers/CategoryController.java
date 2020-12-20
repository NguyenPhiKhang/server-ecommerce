package com.khangse616.serverecommerce.controllers;

import com.khangse616.serverecommerce.dto.CategoryScreenDTO;
import com.khangse616.serverecommerce.mapper.CategoryScreenDTOMapper;
import com.khangse616.serverecommerce.messages.ResponseMessage;
import com.khangse616.serverecommerce.models.Category;
import com.khangse616.serverecommerce.models.Image;
import com.khangse616.serverecommerce.services.CategoryService;
import com.khangse616.serverecommerce.services.ImageService;
import com.khangse616.serverecommerce.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ImageController imageController;
    @Autowired
    private ImageService imageService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryScreenDTO>> getCategoriesLevel1(@RequestParam(value = "level", required = false, defaultValue = "0") int level){
        List<CategoryScreenDTO> categoryScreenDTO = level!=0?categoryService.findCategoryByLevel(level).stream().map(value->new CategoryScreenDTOMapper().mapRow(value)).collect(Collectors.toList())
                : categoryService.findAllCategories().stream().map(value->new CategoryScreenDTOMapper().mapRow(value)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryScreenDTO);
    }

    @GetMapping("/categories/{parentId}/sub-categories")
    public ResponseEntity<List<CategoryScreenDTO>> getCategoriesByParentCategory(@PathVariable("parentId") int parentId){
        List<CategoryScreenDTO> categoryScreenDTO = categoryService.findCategoryByParentCategory(parentId).stream().map(value->new CategoryScreenDTOMapper().mapRow(value)).collect(Collectors.toList());
        return ResponseEntity.ok().body(categoryScreenDTO);
    }

    @PutMapping("/categories/{id}/upload-icon")
    public String uploadIconCategories(@PathVariable("id") int id, @RequestParam("file") MultipartFile file) throws IOException {
        ResponseMessage<Image> fileUploaded =  imageController.uploadFile(file).getBody();
        assert fileUploaded != null;
        categoryService.uploadIcon(id, fileUploaded.getData().getId());

        return "done";
    }

    @PutMapping("/categories/upload-icon/all")
    public ResponseEntity<ResponseMessage<List<Image>>> uploadIconAllCategories(@RequestParam("files") MultipartFile[] files){
        List<MultipartFile> multipartFiles = new ArrayList<>();

        Arrays.stream(files).forEach(file -> {
            String name = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0];
            System.out.println(name);
            Category category = categoryService.findCategoryByPath(name);
            if(category!=null) {
                System.out.println("not null");
                String fileName = ImageUtil.fileName().concat(".").concat(Objects.requireNonNull(file.getContentType()).split("/")[1]);
                categoryService.uploadIcon(category.getId(), fileName);
                try {
                    MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, file.getContentType(), file.getInputStream());
                    multipartFiles.add(multipartFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return ImageUtil.uploadImages(imageService, multipartFiles);
    }
}
