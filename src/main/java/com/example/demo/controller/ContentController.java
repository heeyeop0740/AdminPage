package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ContentsService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="컨텐츠", description = "컨텐츠 관리 API")
public class ContentController {
	
	@Autowired
	ContentsService contentsService;
	
	@GetMapping("contents/count")
	@SecurityRequirement(name = "bearerAuth")
	public Map<String, Object> getContentsNum() {
		
		Map<String, Object> result = contentsService.getContentsInfo();
		result.put("status", "success");
		
		return result;
	}
}