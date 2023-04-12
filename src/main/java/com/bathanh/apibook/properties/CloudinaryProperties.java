package com.bathanh.apibook.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryProperties {

    private String name;
    private String key;
    private String secret;
}