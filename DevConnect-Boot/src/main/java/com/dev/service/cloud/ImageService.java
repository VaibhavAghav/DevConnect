package com.dev.service.cloud;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ImageService {

	@Autowired
	Cloudinary cloudinary;

	public Map uploaFile(MultipartFile file) throws IOException {
		return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
	}

	public Map deleteFile(String cloudnaryImage) throws IOException {
		return cloudinary.uploader().destroy(cloudnaryImage, ObjectUtils.emptyMap());
	}

}
