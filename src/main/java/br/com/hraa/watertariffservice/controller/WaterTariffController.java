package br.com.hraa.watertariffservice.controller;

import br.com.hraa.watertariffservice.dto.CalculatedWaterTariffDto;
import br.com.hraa.watertariffservice.dto.ClientCategoryWaterTariffTablesUploadFileDto;
import br.com.hraa.watertariffservice.dto.CreateClientCategoryWaterTariffTablesRequestDto;
import br.com.hraa.watertariffservice.dto.GetAllClientCategoryWaterTariffTablesResponseDto;
import br.com.hraa.watertariffservice.dto.WaterTariffCalculationRequestDto;
import br.com.hraa.watertariffservice.service.WaterTariffService;
import br.com.hraa.watertariffservice.validation.ClientCategoryWaterTariffTableDtoListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WaterTariffController {
    private final ObjectMapper objectMapper;

    public WaterTariffController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    private WaterTariffService waterTariffService;

    @GetMapping(value = "/tabelas-tarifarias")
    public GetAllClientCategoryWaterTariffTablesResponseDto getAllClientCategoryWaterTariffTables() {
        var categoryTariffTableRangesList = waterTariffService.getAllClientCategoryWaterTariffTables();
        return GetAllClientCategoryWaterTariffTablesResponseDto.from(categoryTariffTableRangesList);
    }

    @PostMapping(value = "/tabelas-tarifarias")
    public ResponseEntity<List<Long>> createWaterTariffParameterTables(
            @RequestBody CreateClientCategoryWaterTariffTablesRequestDto request) {
        var clientCategoryWaterTariffTableList = request.getClientCategoryWaterTariffTableList();
        ClientCategoryWaterTariffTableDtoListValidator.validateList(clientCategoryWaterTariffTableList);
        var createdIdList = waterTariffService.createClientCategoryWaterTariffTables(
            request.toNewClientCategoryWaterTariffTableList());
        return new ResponseEntity<>(createdIdList, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/tabelas-tarifarias/{id}")
    public ResponseEntity<String> deleteWaterTariffTable(@PathVariable("id") Long id) {
        if (id < 0) {
            throw new IllegalArgumentException("The id must be a non-negative number.");
        }
        waterTariffService.softDeleteWaterTariffTable(id);
        return new ResponseEntity<>("Table deleted.", HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/tabelas-tarifarias/upload")
    public ResponseEntity<List<Long>> uploadWaterTariffTablesFile(@RequestParam("arquivo") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty.");
        }
        try {
            var categoryWaterTariffTablesFile = objectMapper.readValue(file.getInputStream(),
                ClientCategoryWaterTariffTablesUploadFileDto.class);
            var categoryTariffTableList = categoryWaterTariffTablesFile.getClientCategoryWaterTariffTableList();
            ClientCategoryWaterTariffTableDtoListValidator.validateList(categoryTariffTableList);
            var createdIdList = waterTariffService.createClientCategoryWaterTariffTables(
                categoryWaterTariffTablesFile.toNewClientCategoryWaterTariffTableList());
            return new ResponseEntity<>(createdIdList, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/tarifa")
    public CalculatedWaterTariffDto calculateWaterTariff(@RequestBody WaterTariffCalculationRequestDto request) {
        var clientCategoryName = request.getClientCategoryName();
        if (clientCategoryName == null || clientCategoryName.isBlank()) {
            throw new IllegalArgumentException("Client category must not be blank.");
        }
        var consumedVolumeInM3 = request.getConsumedVolumeInM3();
        if (consumedVolumeInM3 < 0) {
            throw new IllegalArgumentException("The consumed volume must be non-negative.");
        }
        var waterTariff = waterTariffService.calculateWaterTariff(clientCategoryName, consumedVolumeInM3);
        return CalculatedWaterTariffDto.from(waterTariff, clientCategoryName);
    }
}