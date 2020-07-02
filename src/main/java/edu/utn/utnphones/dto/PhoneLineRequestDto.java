package edu.utn.utnphones.dto;

import edu.utn.utnphones.domain.enums.TypeLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhoneLineRequestDto {

    private String dni;

    @Enumerated(value = EnumType.STRING)
    private TypeLine typeLine;

}
