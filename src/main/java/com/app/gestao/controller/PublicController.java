package com.app.gestao.controller;

import com.app.gestao.dto.auth.LoginRequestDTO;
import com.app.gestao.dto.auth.TokenResponseDTO;
import com.app.gestao.dto.projeto.ProjetoRequestDTO;
import com.app.gestao.dto.projeto.ProjetoResponseDTO;
import com.app.gestao.dto.tarefa.TarefaRequestDTO;
import com.app.gestao.dto.tarefa.TarefaResponseDTO;
import com.app.gestao.dto.usuario.UsuarioRequestDTO;
import com.app.gestao.dto.usuario.UsuarioResponseDTO;
import com.app.gestao.entity.Usuario;
import com.app.gestao.security.TokenService;
import com.app.gestao.service.ProjetoService;
import com.app.gestao.service.TarefaService;
import com.app.gestao.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/public")
@Tag(name = "Público", description = "Todos os endpoints do sistema acessíveis sem autenticação")
public class PublicController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    // ====================== Autenticação ======================

    @PostMapping("/login")
    @Operation(
        summary = "Realizar login (público)",
        description = "Autentica o usuário com email e senha e retorna um token JWT. Endpoint público.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso, token retornado"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
        }
    )
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        var auth = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var authentication = authenticationManager.authenticate(auth);
        var token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }

    // ====================== Usuários ======================

    @PostMapping("/usuarios")
    @Operation(
        summary = "Cadastrar usuário (público)",
        description = "Cria um novo usuário no sistema. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou email já cadastrado")
        }
    )
    public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody @Valid UsuarioRequestDTO dto,
                                                           UriComponentsBuilder uriBuilder) {
        var response = usuarioService.criar(dto);
        var uri = uriBuilder.path("/public/usuarios/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/usuarios")
    @Operation(
        summary = "Listar usuários (público)",
        description = "Retorna todos os usuários paginados. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
        }
    )
    public ResponseEntity<Page<UsuarioResponseDTO>> listarUsuarios(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listarTodos(pageable));
    }

    @GetMapping("/usuarios/{id}")
    @Operation(
        summary = "Buscar usuário por ID (público)",
        description = "Retorna os dados de um usuário específico. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    public ResponseEntity<UsuarioResponseDTO> buscarUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/usuarios/{id}")
    @Operation(
        summary = "Atualizar usuário (público)",
        description = "Atualiza os dados de um usuário existente. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @RequestBody @Valid UsuarioRequestDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @DeleteMapping("/usuarios/{id}")
    @Operation(
        summary = "Deletar usuário (público)",
        description = "Remove um usuário do sistema. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        }
    )
    public ResponseEntity<Void> deletarUsuario(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ====================== Projetos ======================

    @PostMapping("/projetos")
    @Operation(
        summary = "Criar projeto (público)",
        description = "Cria um novo projeto. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
        }
    )
    public ResponseEntity<ProjetoResponseDTO> criarProjeto(@RequestBody @Valid ProjetoRequestDTO dto,
                                                          UriComponentsBuilder uriBuilder) {
        var response = projetoService.criar(dto);
        var uri = uriBuilder.path("/public/projetos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/projetos")
    @Operation(
        summary = "Listar projetos (público)",
        description = "Retorna todos os projetos paginados. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
        }
    )
    public ResponseEntity<Page<ProjetoResponseDTO>> listarProjetos(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(projetoService.listarTodos(pageable));
    }

    @GetMapping("/projetos/{id}")
    @Operation(
        summary = "Buscar projeto por ID (público)",
        description = "Retorna os dados de um projeto específico. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Projeto encontrado"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
        }
    )
    public ResponseEntity<ProjetoResponseDTO> buscarProjeto(
            @Parameter(description = "ID do projeto") @PathVariable Long id) {
        return ResponseEntity.ok(projetoService.buscarPorId(id));
    }

    @PutMapping("/projetos/{id}")
    @Operation(
        summary = "Atualizar projeto (público)",
        description = "Atualiza os dados de um projeto existente. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Projeto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
        }
    )
    public ResponseEntity<ProjetoResponseDTO> atualizarProjeto(
            @Parameter(description = "ID do projeto") @PathVariable Long id,
            @RequestBody @Valid ProjetoRequestDTO dto) {
        return ResponseEntity.ok(projetoService.atualizar(id, dto));
    }

    @DeleteMapping("/projetos/{id}")
    @Operation(
        summary = "Deletar projeto (público)",
        description = "Remove um projeto do sistema. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Projeto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
        }
    )
    public ResponseEntity<Void> deletarProjeto(
            @Parameter(description = "ID do projeto") @PathVariable Long id) {
        projetoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ====================== Tarefas ======================

    @PostMapping("/tarefas")
    @Operation(
        summary = "Criar tarefa (público)",
        description = "Cria uma nova tarefa vinculada a um projeto EM_ANDAMENTO com responsável existente. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou regra de negócio violada")
        }
    )
    public ResponseEntity<TarefaResponseDTO> criarTarefa(@RequestBody @Valid TarefaRequestDTO dto,
                                                        UriComponentsBuilder uriBuilder) {
        var response = tarefaService.criar(dto);
        var uri = uriBuilder.path("/public/tarefas/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/tarefas")
    @Operation(
        summary = "Listar tarefas (público)",
        description = "Retorna todas as tarefas paginadas. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
        }
    )
    public ResponseEntity<Page<TarefaResponseDTO>> listarTarefas(
            @PageableDefault(size = 10, sort = "dataExecucao") Pageable pageable) {
        return ResponseEntity.ok(tarefaService.listarTodas(pageable));
    }

    @GetMapping("/tarefas/{id}")
    @Operation(
        summary = "Buscar tarefa por ID (público)",
        description = "Retorna os dados de uma tarefa específica. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
        }
    )
    public ResponseEntity<TarefaResponseDTO> buscarTarefa(
            @Parameter(description = "ID da tarefa") @PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @PutMapping("/tarefas/{id}")
    @Operation(
        summary = "Atualizar tarefa (público)",
        description = "Atualiza os dados de uma tarefa existente. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
        }
    )
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(
            @Parameter(description = "ID da tarefa") @PathVariable Long id,
            @RequestBody @Valid TarefaRequestDTO dto) {
        return ResponseEntity.ok(tarefaService.atualizar(id, dto));
    }

    @DeleteMapping("/tarefas/{id}")
    @Operation(
        summary = "Deletar tarefa (público)",
        description = "Remove uma tarefa do sistema. Endpoint público, não requer autenticação.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Tarefa removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
        }
    )
    public ResponseEntity<Void> deletarTarefa(
            @Parameter(description = "ID da tarefa") @PathVariable Long id) {
        tarefaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
