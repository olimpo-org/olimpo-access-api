package com.example.olimpoapi.controller;

import com.example.olimpoapi.config.exception.ExceptionThrower;
import com.example.olimpoapi.model.postgresql.Admin;
import com.example.olimpoapi.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
public class AdminController {
    AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/create")
    @Operation(summary = "Cria um novo administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
    })
    public ResponseEntity<Admin> createAdministrator(
            @Parameter(description = "JSON com os dados do administrador")
            @RequestBody Admin administrator,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            ExceptionThrower.throwBadRequestException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ResponseEntity.ok(
                adminService.create(administrator)
        );
    }

    @PostMapping("/verify")
    @Operation(summary = "Verifica se um administrador existe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador existe"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    public ResponseEntity<Admin> verify(
            @Parameter(description = "JSON com os dados do administrador")
            @RequestBody Admin administrator
    ) {
        return ResponseEntity.ok(
                adminService.verify(
                        administrator.getUsername(),
                        administrator.getPassword()
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Deleta um administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    public ResponseEntity delete(
            @Parameter(description = "JSON com os dados do administrador")
            @PathVariable("id") Integer id
    ) {
        adminService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado")
    })
    public ResponseEntity<Admin> update(
            @Parameter(description = "ID do administrador")
            @PathVariable("id") Integer id,
            @Parameter(description = "JSON com os dados do administrador")
            @RequestBody Admin admin
    ) {
        return ResponseEntity.ok().body(
                adminService.update(id, admin)
        );
    }
}
