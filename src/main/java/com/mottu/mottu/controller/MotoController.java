package com.mottu.mottu.controller;

import com.mottu.mottu.dto.MotoDTO;
import com.mottu.mottu.exception.MotoNotFoundException;
import com.mottu.mottu.model.Moto;
import com.mottu.mottu.repository.MotoRepository;
import com.mottu.mottu.service.BuscarMotoService;
import com.mottu.mottu.service.CadastroMoto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api")
public class MotoController {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private BuscarMotoService serviceBuscar;

    @Autowired
    private CadastroMoto cadastroMoto;

    @GetMapping(path = "/moto")
    public List<Moto> listaMoto() {
        return serviceBuscar.buscarTodasAsMotos();
    }

    @GetMapping(path = "/moto/id/{id}")
    public Moto buscarMotoPorId(
            @PathVariable(name = "id", required = true)Long id) throws MotoNotFoundException {
        return serviceBuscar.buscarMotoPorId(id);
    }

    @PostMapping(path = "/moto/save")
    public void salvarMoto(@RequestBody @Valid MotoDTO motoDTO) {
        cadastroMoto.cadastro(motoDTO);
    }

    @DeleteMapping(path = "/moto/delete/{id}")
    public void deletarMoto(@PathVariable (name = "id", required = true)Long id) throws MotoNotFoundException {
        serviceBuscar.deletarMotoPorId(id);
    }
}
