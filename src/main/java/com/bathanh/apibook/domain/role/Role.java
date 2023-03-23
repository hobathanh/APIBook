package com.bathanh.apibook.domain.role;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Role {

    private UUID id;

    private String name;
}

