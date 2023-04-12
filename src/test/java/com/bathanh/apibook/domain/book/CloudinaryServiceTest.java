package com.bathanh.apibook.domain.book;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static org.apache.commons.lang3.RandomUtils.nextBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CloudinaryServiceTest {

    @InjectMocks
    private CloudinaryService cloudinaryService;

    @Mock
    private Cloudinary cloudinary;

    @BeforeEach
    void setUp() {
        when(cloudinary.uploader()).thenReturn(mock(Uploader.class));
        cloudinaryService = new CloudinaryService(cloudinary);
    }

    @Test
    void shouldUploadImage_Successfully() throws IOException {
        final var bytes = nextBytes(20);

        final var cloudinaryResponse = Map.of("secure_url", "https://res.cloudinary.com/test/image/upload/test.jpg");
        when(cloudinary.uploader().upload(any(byte[].class), any())).thenReturn(cloudinaryResponse);

        final var result = cloudinaryService.upload(bytes);

        assertEquals(cloudinaryResponse.get("secure_url"), result);
    }
}
