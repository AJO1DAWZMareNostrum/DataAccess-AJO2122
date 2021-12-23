import java.util.Date;

enum MaterialTypes {
        IRON,
        BRONZE,
        MARBLE
}

public class Sculpture extends ArtWork {

    protected MaterialTypes material;
    protected float weigth;

    public Sculpture() {

    }

    public Sculpture(int code, String title, Date dated, Styles style, String authorCode,
                     MaterialTypes material, float weigth) {
        super(code, title, dated, style, authorCode);
        this.material = material;
        this.weigth = weigth;
    }

    public MaterialTypes getMaterial() { return material; }
    public void setMaterial(MaterialTypes value) {
        if (value == MaterialTypes.IRON || value == MaterialTypes.BRONZE ||
                value == MaterialTypes.MARBLE)
            material = value;
        else
            System.out.println("Value introduced is not a valid sculpture material.");
    }

    public float getWeigth() { return weigth; }
    public void setWeigth(float value) {
        if (value > 0)
            weigth = value;
        else
            System.out.println("The number introduced MUST be bigger than zero.");
    }

    @Override
    public String toString() {
        return super.toString() +
                ", material =" + material +
                ", weigth =" + weigth;
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;

        if ( !(other instanceof Sculpture) )
            return false;

        Sculpture otherSculpture = (Sculpture)other;
        return (this.material == otherSculpture.material &&
                this.weigth == otherSculpture.weigth);
    }
}
