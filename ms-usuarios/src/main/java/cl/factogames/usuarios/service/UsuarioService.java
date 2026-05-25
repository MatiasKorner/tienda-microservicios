package cl.factogames.usuarios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.factogames.usuarios.dto.UsuarioRequest;
import cl.factogames.usuarios.dto.UsuarioResponse;
import cl.factogames.common.exception.*;
import cl.factogames.usuarios.mapper.UsuarioMapper;
import cl.factogames.usuarios.model.Usuario;
import cl.factogames.usuarios.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public List<UsuarioResponse> findAll() {
        return usuarioMapper.toResponseList(usuarioRepository.findAll());
    }

    public UsuarioResponse findById(long id) {
        return usuarioMapper.toResponse(getUsuarioById(id));
    }

    public UsuarioResponse findByEmail(String email) {
        return usuarioMapper.toResponse(getUsuarioByEmail(email));
    }

    @Transactional
    public UsuarioResponse create(UsuarioRequest request) {
        validateEmailUnico(request.getEmail());
        Usuario usuario = new Usuario();
        usuarioMapper.updateEntity(request, usuario);
        usuario.setActivo(true);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse update(long id, UsuarioRequest request) {
        Usuario usuario = getUsuarioById(id);
        if (!usuario.getEmail().equalsIgnoreCase(request.getEmail())) {
            validateEmailUnico(request.getEmail());
        }
        usuarioMapper.updateEntity(request, usuario);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public void deleteById(long id) {
        Usuario usuario = getUsuarioById(id);
        if (usuario != null) {
            usuarioRepository.delete(usuario);
        }
    }

    @Transactional
    public UsuarioResponse activar(long id) {
        Usuario usuario = getUsuarioById(id);
        usuario.setActivo(true);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponse desactivar(long id) {
        Usuario usuario = getUsuarioById(id);
        usuario.setActivo(false);
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    private Usuario getUsuarioById(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuarios", "ID", id));
    }

    private Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuarios", "email", email));
    }

    private void validateEmailUnico(String email) {
        usuarioRepository.findByEmail(email).ifPresent(u -> {
            throw new DuplicateResourceException(
                "Un Usuario", "email", email,
                u.getNombre() + " " + u.getApellido()
            );
        });
    }
}
