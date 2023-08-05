package com.example.allyclient.Models.Model;

import com.example.allyclient.Models.ModelDtos.ModelDtos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {

    List<Model> findByName(String  name);
    List<Model> findByPhone(String  phone);


}
