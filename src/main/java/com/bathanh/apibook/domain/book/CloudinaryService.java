package com.bathanh.apibook.domain.book;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.util.Collections.emptyMap;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String upload(final byte[] image) throws IOException {
        final var result = cloudinary.uploader().upload(image, emptyMap());
        return result.get("secure_url").toString();
    }
}