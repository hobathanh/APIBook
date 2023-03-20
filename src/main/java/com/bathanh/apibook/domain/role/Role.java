package com.bathanh.apibook.domain.role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.util.UUID;

@Getter
@With
@Setter
@Builder
public class Role {

    private UUID id;

    private String name;
}

