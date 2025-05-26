package com.mottu.mottu.service;

import com.mottu.mottu.dto.MotoDTO;
import com.mottu.mottu.exception.MotoDTOException;
import com.mottu.mottu.model.Moto;
import com.mottu.mottu.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroMoto {

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private MotoConversor service;

    public void cadastro(MotoDTO motoDTO) {

        try {
            Moto moto = service.conversor(motoDTO);
            motoRepository.saveAndFlush(moto);
        } catch (MotoDTOException e) {
            e.printStackTrace();
        }

    }
}
