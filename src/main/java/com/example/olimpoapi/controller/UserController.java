package com.example.olimpoapi.controller;

import com.example.olimpoapi.config.exception.ExceptionThrower;
import com.example.olimpoapi.model.postgresql.Administrator;
import com.example.olimpoapi.model.postgresql.User;
import com.example.olimpoapi.model.utils.Login;
import com.example.olimpoapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "User", description = "Endpoints de Usuário")
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/manterApi")
    public ResponseEntity manterApi() {
        return ResponseEntity.ok().build();
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Operation(summary = "Criar novo Usuário", description = "Endpoint cria uma nova a partir de um objeto JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping("/create")
    public ResponseEntity<User> create(
            @Parameter(description = "JSON com os dados do usuário")
            @RequestBody User user, BindingResult result
    ) {
        if (result.hasErrors()) {
            ExceptionThrower.throwBadRequestException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ResponseEntity.ok().body(
                userService.save(user)
        );
    }

    @Operation(summary = "Realizar Login", description = "Endpoint realiza o login de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Senha ou email inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),

    })
    @PostMapping("/login")
    public ResponseEntity<User> login(
            @Parameter(description = "JSON com os dados de login")
            @RequestBody Login login
    ) {
        return ResponseEntity.ok().body(
                userService.login(login)
        );
    }

    @Operation(summary = "Atualizar Usuário", description = "Endpoint atualiza um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<User> update(
            @Parameter(description = "ID do Usuário")
            @PathVariable("id") UUID id,
            @Parameter(description = "JSON com os dados do usuário")
            @RequestBody User user
    ) {
        return ResponseEntity.ok().body(
                userService.update(id, user)
        );
    }

    @Operation(summary = "Deletar Usuário", description = "Endpoint deleta um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> delete(
            @Parameter(description = "ID do Usuário")
            @PathVariable("id") UUID id
    ) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Listar todos os Usuários", description = "Endpoint lista todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuários não encontrados")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
            return ResponseEntity.ok().body(
                    userService.getAll()
            );
    }

    @Operation(summary = "Listar um Usuário pelo ID", description = "Endpoint lista um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/getById/{id}")
    public ResponseEntity<User> getById(
            @Parameter(description = "ID do Usuário")
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(
                userService.findById(id)
        );
    }

    @Operation(summary = "Verificar se um Usuário existe pelo ID", description = "Endpoint verifica se um usuário existe pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário existe"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(
            @Parameter(description = "ID do Usuário")
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(
                userService.verifyIfUserExistsById(id)
        );
    }

    @Operation(summary = "Dar permissão de administrador", description = "Endpoint da permissão de administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping("/grantAdministrator")
    public ResponseEntity<Administrator> grantAdministrator(
            @Parameter(description = "Administrador a ser criado")
            @RequestBody Administrator administrator, BindingResult result
    ) {
        if (result.hasErrors()) {
            ExceptionThrower.throwBadRequestException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ResponseEntity.ok().body(
                userService.grantAdministrator(administrator)
        );
    }

    @Operation(summary = "Verificar se o Usuário é administrador", description = "Endpoint verifica se o Usuário é administrador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Administrador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @GetMapping("/isAdministrator/{customerId}/{communityId}")
    public ResponseEntity<Boolean> verifyIfIsAdministrator(
            @Parameter(description = "ID do Usuário")
            @PathVariable String customerId,
            @Parameter(description = "ID da comunidade")
            @PathVariable UUID communityId
            ) {
        return ResponseEntity.ok().body(
                userService.verifyIfIsAdministrator(customerId, communityId)
        );
    }
}
