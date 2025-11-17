package com.saep2025.saep_db1.controller;

import com.saep2025.saep_db1.model.Usuario;
import com.saep2025.saep_db1.repository.UsuarioRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuario/lista";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario) {

        // Se for edição e senha vier vazia, não altera a senha
        if (usuario.getId() != null) {
            Usuario original = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (original != null && (usuario.getSenha() == null || usuario.getSenha().isBlank())) {
                usuario.setSenha(original.getSenha());
            } else {
                usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            }
        } else {
            // Novo usuário → criptografar senha normalmente
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        usuarioRepository.save(usuario);

        // ⬅⬅⬅ AQUI ESTÁ A CORREÇÃO
        return "redirect:/usuarios";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioRepository.findById(id).orElse(new Usuario()));
        return "usuario/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios";
    }
}
