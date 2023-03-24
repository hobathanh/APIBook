package com.bathanh.apibook.domain.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Builder
@Setter
public class Role {

    private UUID id;

    private String name;
}

