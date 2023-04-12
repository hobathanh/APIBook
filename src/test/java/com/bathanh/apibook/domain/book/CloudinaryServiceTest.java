package com.bathanh.apibook.domain.book;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Map;

import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.RandomUtils.nextBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CloudinaryServiceTest {

    @InjectMocks
    private CloudinaryService cloudinaryService;

    @Mock
    private Cloudinary cloudinary;

    @Test
    void shouldUploadImage_Ok() throws IOException {
        final var bytes = nextBytes(20);
        final var cloudinaryResponse = Map.of("secure_url", "https://res.cloudinary.com/test/image/upload/test.jpg");
        final var uploader = mock(Uploader.class);

        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(bytes, emptyMap())).thenReturn(cloudinaryResponse);

        final var result = cloudinaryService.upload(bytes);

        assertEquals(cloudinaryResponse.get("secure_url"), result);

        verify(cloudinary).uploader();
        verify(uploader).upload(bytes, emptyMap());
    }
}
