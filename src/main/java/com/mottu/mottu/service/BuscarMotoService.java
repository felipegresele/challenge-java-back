package com.mottu.mottu.service;

import com.mottu.mottu.exception.MotoNotFoundException;
import com.mottu.mottu.model.Moto;
import com.mottu.mottu.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuscarMotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> buscarTodasAsMotos() {
        List<Moto> listMoto = motoRepository.findAll();
        return listMoto;
    }

    public Moto buscarMotoPorId(Long id) throws MotoNotFoundException{
        Optional<Moto> optionalMoto = motoRepository.findById(id);

        Moto moto = null;
        if (!optionalMoto.isPresent()) {
            throw new MotoNotFoundException(
                    "Moto não foi encontrada pelo id: " + id
            );
        } else {
          moto = optionalMoto.get();
        }
        return moto;
    }

    private Optional<Moto> getOptional(Long id) {
        Optional<Moto> optionalMoto = motoRepository.findById(id);
        return optionalMoto;
    }

    public void deletarMotoPorId(Long id) throws MotoNotFoundException {
        Optional<Moto> optionalMoto = getOptional(id);
        if (!optionalMoto.isPresent()) {
            throw new MotoNotFoundException(
                    "Moto não foi deletada pelo id: " + id
            );
        } else {
        motoRepository.delete(optionalMoto.get());
        }
    }
}
