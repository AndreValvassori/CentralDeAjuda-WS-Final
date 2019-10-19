package Faczz.Drevelopment.centraldeajuda.Model;

import java.math.BigDecimal;
import java.util.Date;

public class Aviso {

    private String uid;
    private String User_uid;
    private String Nome;
    private String Local;
    private String Detalhes;
    private Date horario;
    private double latitude;
    private double Longitude;

    public Aviso() {

    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_uid() {
        return User_uid;
    }

    public void setUser_uid(String user_uid) {
        User_uid = user_uid;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String local) {
        Local = local;
    }

    public String getDetalhes() {
        return Detalhes;
    }

    public void setDetalhes(String detalhes) {
        Detalhes = detalhes;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "Aviso{" +
                "uid='" + uid + '\'' +
                ", User_uid='" + User_uid + '\'' +
                ", Nome='" + Nome + '\'' +
                ", Local='" + Local + '\'' +
                ", Detalhes='" + Detalhes + '\'' +
                ", horario=" + horario +
                ", latitude=" + latitude +
                ", Longitude=" + Longitude +
                '}';
    }
}
