package com.example.allyclient.Models.Model;

import com.example.allyclient.Models.ModelDtos.ModelDtos;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;


@CrossOrigin(origins = "http://localhost:8050")
@RestController
//@RequestMapping("/api")
public class ModelController {
    @Autowired
    ModelRepository modelRepository;
    @Autowired
     RestTemplate restTemplate;
    @GetMapping("/getAllbyName")
    public ResponseEntity<List<Model>> getAllName(@RequestParam(required = false)
                                                                     String name) {
        try {
            List<Model> x = new ArrayList<>(modelRepository.findByName(name));

            if (x.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(x, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAllbyPhone")
    public ResponseEntity<List<Model>> getAllNewPhone(@RequestParam(required = false)
                                                       String phone) {
        try {
            List<Model> NewVisite = new ArrayList<Model>(modelRepository.findByPhone(phone));
            if (NewVisite.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(NewVisite, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //when the response is just a simple string from the flask microservices project
    @PostMapping("/createPeople")
    public ResponseEntity<String> people(@RequestBody Model people) throws JsonProcessingException {
        String result = restTemplate.postForObject("http://data-aggregation-service/create", people, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String flaskResponse = objectMapper.readValue(result, String.class);
        return status(HttpStatus.OK).body(flaskResponse);
    }

    //here I m trying to load all data from Flak APi
    @GetMapping("/loardAllPeople")
    public ResponseEntity<List<ModelDtos>> loardAll() {
        try {
            String response = restTemplate.getForObject("http://data-aggregation-service/emp", String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            List<ModelDtos> flaskResponse = objectMapper.readValue(response, new TypeReference<>() {});
            System.out.println(flaskResponse);

            return new ResponseEntity<>(flaskResponse,HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createFlaskUser")
    public ResponseEntity<String> CreateUser(@RequestBody Model model) {
        ModelDtos   modelDtos = restTemplate.getForObject("http://data-aggregation-service/createFlask", ModelDtos.class);
        return status(HttpStatus.OK)
                .body(String.format("Sent the model to the Data Aggregation Service: %s \nAnd got back:\n %s", model.toString(), modelDtos.toString()));
    }

    @PostMapping("/createPeopleOld")
    public ResponseEntity<String> peopleOld(@RequestBody Model people) throws JsonProcessingException {
        String result = restTemplate.postForObject("http://data-aggregation-service/create", people, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ModelDtos modelDto = objectMapper.readValue(result, ModelDtos.class);
        return status(HttpStatus.OK).body(String.format("Sent the people to the Data Aggregation Service: %s \nAnd got back:\n %s", people.toString(), modelDto.toString()));
    }

    }

//return new ResponseEntity<>(_NewVisite, HttpStatus.CREATED);
//return new ResponseEntity<>(_NewVisite, HttpStatus.CREATED);

