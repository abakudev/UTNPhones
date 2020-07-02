package edu.utn.utnphones.dto;

import edu.utn.utnphones.domain.enums.TypeLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.platform.commons.util.StringUtils;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientRequestDto implements Serializable {

    private Long cityId;

    private String firstname;

    private String lastname;

    private String dni;

    private String password;

    @Enumerated(EnumType.STRING)
    private TypeLine typeLine;

    public Boolean isValid() {
        return cityId != null && !StringUtils.isBlank(dni) && !StringUtils.isBlank(password)  && !StringUtils.isBlank(firstname) && !StringUtils.isBlank(lastname);
    }

}
