import java.time.LocalDate;

public class GPWIndex implements Comparable<GPWIndex>{
    String nazwa;
    private String waluta;
    private float otwarcie;
    private float max;
    private float min;
    private float zamknięcie;
    private float zmiana;
    private float obrot;
    LocalDate date;

    static int equals_call_counter = 0;

    GPWIndex(String nazwa, String waluta, float otwarcie, float max, float min, float zamknięcie, float zmiana,
            float obrot, String date) {
        this.nazwa = nazwa;
        this.waluta = waluta;
        this.otwarcie = otwarcie;
        this.max = max;
        this.min = min;
        this.zamknięcie = zamknięcie;
        this.zmiana = zmiana;
        this.obrot = obrot;
        this.date = LocalDate.parse(date);
    }

    GPWIndex(String[] values, String date) {
        this.nazwa = values[0];
        this.waluta = values[1];
        this.otwarcie = Float.parseFloat(values[2]);
        this.max = Float.parseFloat(values[3]);
        this.min = Float.parseFloat(values[4]);
        this.zamknięcie = Float.parseFloat(values[5]);
        this.zmiana = Float.parseFloat(values[6]);
        this.obrot = Float.parseFloat(values[7]);
        this.date = LocalDate.parse(date);
    }

    @Override
    public boolean equals(Object obj) {
        equals_call_counter++;
        if (obj == null) {
            return false;
        }
        if (obj instanceof GPWIndex) {
            GPWIndex otherGPWIndex = (GPWIndex) obj;
            return nazwa.equals(otherGPWIndex.nazwa);
        }
        return false;
    }

    public float getOpenValue(){
        return otwarcie;
    }

    @Override
    public String toString() {
        return nazwa;
    }

    @Override
    public int hashCode() {
        return nazwa.hashCode();
    }

    @Override
    public int compareTo(GPWIndex o) {
        return nazwa.compareTo(o.nazwa);
    }
}