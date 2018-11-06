package oslomet.no.s309854_mappe3;

public class Reservasjon {
    private String navn;
    private String bygning;
    private String rom;
    private String dato;
    private String fra;
    private String til;

    public Reservasjon(String navn, String bygning, String rom, String dato, String fra, String til) {
        this.navn = navn;
        this.bygning = bygning;
        this.rom = rom;
        this.dato = dato;
        this.fra = fra;
        this.til = til;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public void setBygning(String bygning) {
        this.bygning = bygning;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public void setFra(String fra) {
        this.fra = fra;
    }

    public void setTil(String til) {
        this.til = til;
    }

    public String getNavn() {
        return navn;
    }

    public String getBygning() {
        return bygning;
    }

    public String getRom() {
        return rom;
    }

    public String getDato() {
        return dato;
    }

    public String getFra() {
        return fra;
    }

    public String getTil() {
        return til;
    }
}
