package com.mottu.mottu.service;

import com.mottu.mottu.dto.MotoDTO;
import com.mottu.mottu.exception.MotoDTOException;
import com.mottu.mottu.model.Moto;
import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Component;

@Component
public class MotoConversor {

    public Moto conversor (MotoDTO motoDTO) throws MotoDTOException {

        try {
            Moto moto = new Moto();
            moto.setChassi(motoDTO.getChassi());
            moto.setPlaca(motoDTO.getPlaca());
            moto.setCodigoRegistro(motoDTO.getCodigoRegistro());
            moto.setGalpao(motoDTO.getGalpao());
            moto.setModelo(motoDTO.getModelo());
            return moto;
        } catch (Exception e) {
            throw new MotoDTOException(
                    "Falha no conversor do DTO de moto para entidade " + motoDTO
            );
        }
    }

}
