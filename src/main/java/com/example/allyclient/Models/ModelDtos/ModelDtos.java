package com.example.allyclient.Models.ModelDtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ModelDtos {
        private long id;
        private String name;
        private String email;
        private String phone;
        private String address;
}
