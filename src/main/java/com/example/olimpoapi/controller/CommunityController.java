package com.example.olimpoapi.controller;

import com.example.olimpoapi.config.exception.ExceptionThrower;
import com.example.olimpoapi.model.postgresql.Community;
import com.example.olimpoapi.model.postgresql.CommunityUser;
import com.example.olimpoapi.model.postgresql.User;
import com.example.olimpoapi.model.redis.Solicitation;
import com.example.olimpoapi.service.CommunityService;
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
@Tag(name = "Community", description = "Endpoints de Comunidade")
@RequestMapping("/v1/community")
public class CommunityController {
    private final CommunityService communityService;
    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @Operation(summary = "Criar nova Comunidade", description = "Endpoint cria uma nova a partir de um objeto JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping("/create")
    public ResponseEntity<Community> create(
            @Parameter(description = "JSON com os dados da comunidade")
            @RequestBody Community community, BindingResult result
    ) {
        if (result.hasErrors()) {
            ExceptionThrower.throwBadRequestException(result.getAllErrors().get(0).getDefaultMessage());
        }
        return ResponseEntity.ok().body(
                communityService.save(community)
        );
    }

    @Operation(summary = "Atualizar uma Comunidade", description = "Endpoint atualiza uma Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Community> update(
            @Parameter(description = "ID da Comunidade")
            @PathVariable("id") UUID id,
            @Parameter(description = "JSON com os dados da comunidade")
            @RequestBody Community community
    ) {
        community.setId(id);
        return ResponseEntity.ok().body(
                communityService.update(id, community)
        );
    }

    @Operation(summary = "Deletar uma Comunidade", description = "Endpoint deleta uma Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Community> delete(
            @Parameter(description = "ID da Comunidade")
            @PathVariable("id") UUID id
    ) {
        communityService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar todas as Comunidades", description = "Endpoint lista todas as comunidades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidades retornadas com sucesso")
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<Community>> getAll() {
        return ResponseEntity.ok().body(
                communityService.getAll()
        );
    }

    @Operation(summary = "Listar todos os Usuários de uma Comunidade", description = "Endpoint lista todos os Usuários de uma Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada"),
            @ApiResponse(responseCode = "404", description = "Nenhum Usuário encontrado na Comunidade")
    })
    @GetMapping("/getAllUsersInCommunity/{communityId}")
    public ResponseEntity<List<User>> getAllUsersInCommunity(
            @Parameter(description = "ID da Comunidade")
            @PathVariable UUID communityId
    ) {
        return ResponseEntity.ok().body(
                communityService.getAllUsersByCommunityId(communityId)
        );
    }

    @Operation(summary = "Listar todas as Comunidades de um Usuário", description = "Endpoint lista todas as Comunidades de um Usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidades retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada"),
            @ApiResponse(responseCode = "404", description = "Nenhum Usuário encontrado na Comunidade")
    })
    @GetMapping("/getAllCommunitiesByUser/{customerId}")
    public ResponseEntity<List<Community>> getAllCommunitiesByUser(
            @Parameter(description = "ID do Usuário")
            @PathVariable UUID customerId
    ) {
        return ResponseEntity.ok().body(
                communityService.getAllCommunitiesByUserId(customerId)
        );
    }

    @Operation(summary = "Criar uma Solicitação de uma Comunidade", description = "Endpoint cria uma Solicitação de uma Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @PostMapping("/createSolicitation")
    public ResponseEntity<Solicitation> createSolicitation(
            @Parameter(description = "JSON com os dados da Solicitação")
            @RequestBody Solicitation solicitation
    ) {
        return ResponseEntity.ok().body(
                communityService.createSolicitation(solicitation)
        );
    }

    @Operation(summary = "Listar todas as Solicitações de uma Comunidade", description = "Endpoint lista todas as Solicitações de uma Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitações retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @GetMapping("/getAllSolicitations/{communityId}")
    public ResponseEntity<List<Solicitation>> getAllSolicitations(
            @Parameter(description = "ID da Comunidade")
            @PathVariable UUID communityId
    ) {
        return ResponseEntity.ok().body(
                communityService.getAllSolicitationsByCommunityId(communityId)
        );
    }

    @Operation(summary = "Aceitar uma Solicitação de uma Comunidade", description = "Endpoint aceita uma Solicitação de uma Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação aceita com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @PostMapping("/acceptSolicitation/{solicitationId}")
    public ResponseEntity<String> acceptSolicitation(
            @Parameter(description = "ID da Solicitação")
            @PathVariable UUID solicitationId
    ) {
        communityService.acceptSolicitation(solicitationId);
        return ResponseEntity.ok().body(
                "Successfully accepted"
        );
    }

    @Operation(summary = "Rejeitar uma Solicitação de uma Comunidade", description = "Endpoint rejeita uma Solicitação de uma Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Solicitação rejeitada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comunidade não encontrada")
    })
    @PostMapping("/rejectSolicitation/{solicitationId}")
    public ResponseEntity<String> rejectSolicitation(
            @PathVariable UUID solicitationId
    ) {
        communityService.rejectSolicitation(solicitationId);
        return ResponseEntity.ok().body(
                "Successfully rejected"
        );
    }

    @Operation(summary = "Adicionar um Usuário a uma Comunidade", description = "Endpoint adiciona um Usuário a uma Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade adicionada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Comunidade ou Usuário não encontrados")
    })
    @PostMapping("/addUserToCommunity/{communityId}/{customerId}")
    public ResponseEntity<CommunityUser> addUserToCommunity(@PathVariable UUID communityId, @PathVariable UUID customerId) {
        return ResponseEntity.ok().body(
                communityService.addUserToCommunity(communityId, customerId)
        );
    }

    @Operation(summary = "Remover um Usuário de uma Comunidade", description = "Endpoint remove um Usuário de uma Comunidade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comunidade removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Comunidade ou Usuário não encontrados")
    })
    @DeleteMapping("/removeUserFromCommunity/{communityId}/{customerId}")
    public ResponseEntity<CommunityUser> removeUserFromCommunity(@PathVariable UUID communityId, @PathVariable UUID customerId) {
        return ResponseEntity.ok().body(
                communityService.removeUserFromCommunity(communityId, customerId)
        );
    }
}
