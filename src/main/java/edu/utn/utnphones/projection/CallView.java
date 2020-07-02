package edu.utn.utnphones.projection;

import java.time.LocalDateTime;
import java.util.Date;

public interface CallView {

    String getDni();
    String getPhoneNumberOrigin();
    String getPhoneNumberDestination();
    String getCityOrigin();
    String getCityDestination();
    String getTotalPrice();
    Integer getDuration();
    LocalDateTime getDate();



    void setDni(String dni);
    void setPhoneNumberOrigin(String phoneNumberOrigin);
    void setPhoneNumberDestination(String phoneNumberDestination);
    void setCityOrigin(String cityOrigin);
    void setCityDestination(String cityDestination);
    void setDuration(Integer duration);
    void setDate(Date date );
}
