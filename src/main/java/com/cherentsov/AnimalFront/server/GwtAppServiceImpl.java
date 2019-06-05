package com.cherentsov.AnimalFront.server;

import com.cherentsov.AnimalFront.shared.*;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@PropertySource("classpath:application.properties")
public class GwtAppServiceImpl {
    private String port;
    private String ip;
    private static final Log logger = LogFactory.getLog(GwtAppServiceImpl.class);

    @Bean
    public RestTemplate restTemplateBean() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    public void setPort(@Value("${backend.port}")String port) {
        this.port = port;
    }
    @Autowired
    public void setIp(@Value("${backend.ip}")String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    @RequestMapping(value = "/gwtApp/getAll", method = GET)
    public ResponseEntity<List<Pet>> getTodoItems() {

        StringBuilder uri = new StringBuilder("http://");
        uri.append(ip).append(":").append(port).append("/animals");

        //RestTemplate restTemplate = new RestTemplate();
        List<Pet> result = restTemplate.getForObject(uri.toString(), List.class);
        logger.debug("Результат запроса к бэкэнду: " + result);
        logger.debug("Количество сущностей в ответе от бэкенда: " + result.size());

        return new ResponseEntity<List<Pet>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/gwtApp/getId", method = PUT)
    public ResponseEntity<List<Pet>> getId(@RequestBody final Long lId) {
        StringBuilder uri = new StringBuilder("http://");
        uri.append(ip).append(":").append(port).append("/animals/").append(lId);

        //RestTemplate restTemplate = new RestTemplate();
        Pet result = restTemplate.getForObject(uri.toString(), Pet.class);
        ArrayList<Pet> lResult = new ArrayList<>();
        if (result != null) {
            lResult.add(result);
        }
        logger.debug("Результат запроса к бэкэнду: " + result);

        return new ResponseEntity<List<Pet>>(lResult, HttpStatus.OK);
    }

    @RequestMapping(value = "/gwtApp/search", method = PUT)
    public ResponseEntity<List<Pet>> search(@RequestBody final String sRequest) {
        //public ResponseEntity<List<Pet>> getId(@RequestParam(value="Id", required=false, defaultValue="2") String sId) {
        StringBuilder uri = new StringBuilder("http://");
        uri.append(ip).append(":").append(port).append("/search/").append(sRequest);

        //RestTemplate restTemplate = new RestTemplate();
        List<Pet> result = restTemplate.getForObject(uri.toString(), List.class);

        logger.debug("Результат запроса к бэкэнду: " + result);

        return new ResponseEntity<List<Pet>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/gwtApp/delete", method = PUT)
    public ResponseEntity<Void> delete(@RequestBody final Long lId) {
        StringBuilder uri = new StringBuilder("http://");
        uri.append(ip).append(":").append(port).append("/animals/").append(lId);

        //RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.delete(uri.toString());
        }
        catch(HttpServerErrorException e){
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/gwtApp/getAnimalType", method = GET)
    public ResponseEntity<List<AnimalType>> getAnimalType() {

        StringBuilder uri = new StringBuilder("http://");
        uri.append(ip).append(":").append(port).append("/animaltype");

        //RestTemplate restTemplate = new RestTemplate();
        List<AnimalType> result = restTemplate.getForObject(uri.toString(), List.class);
        logger.debug("Результат запроса к бэкэнду: " + result);
        logger.debug("Количество сущностей в ответе от бэкенда: " + result.size());

        return new ResponseEntity<List<AnimalType>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/gwtApp/getLocation", method = GET)
    public ResponseEntity<List<Location>> getLocation() {
        StringBuilder uri = new StringBuilder("http://");
        uri.append(ip).append(":").append(port).append("/location");

        //RestTemplate restTemplate = new RestTemplate();
        List<Location> result = restTemplate.getForObject(uri.toString(), List.class);
        logger.debug("Результат запроса к бэкэнду: " + result);
        logger.debug("Количество сущностей в ответе от бэкенда: " + result.size());

        return new ResponseEntity<List<Location>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/gwtApp/getSkinColor", method = GET)
    public ResponseEntity<List<SkinColor>> getSkinColor() {

        StringBuilder uri = new StringBuilder("http://");
        uri.append(ip).append(":").append(port).append("/skincolor");

        //RestTemplate restTemplate = new RestTemplate();
        List<SkinColor> result = restTemplate.getForObject(uri.toString(), List.class);
        logger.debug("Результат запроса к бэкэнду: " + result);
        logger.debug("Количество сущностей в ответе от бэкенда: " + result.size());

        return new ResponseEntity<List<SkinColor>>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/gwtApp/create", method = PUT)
    public ResponseEntity<Void> create(@RequestBody final Pet pet) {
        StringBuilder uri = new StringBuilder("http://");
        uri.append(ip).append(":").append(port).append("/animals/");
        //RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.postForObject(uri.toString(), pet, Pet.class);
        }
        catch(HttpServerErrorException e){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/gwtApp/update", method = PUT)
    public ResponseEntity<Void> update(@RequestBody final Pet pet) {
        StringBuilder uri = new StringBuilder("http://");
        uri.append(ip).append(":").append(port).append("/animals/");
        //RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.put(uri.toString(), pet);
        }
        catch(HttpServerErrorException e){
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
