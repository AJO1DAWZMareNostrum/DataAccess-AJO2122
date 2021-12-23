import java.util.Date;

enum PaintingTypes {
    OILPAINTING,
    WATERCOLOUR,
    PASTEL
}

class Painting extends ArtWork {

    protected PaintingTypes type;
    protected float width;
    protected float height;

    public Painting() {}

    public Painting(int code, String title, Date dated, Styles style, String authorCode,
                    PaintingTypes type, float width, float height) {
        super(code, title, dated, style, authorCode);
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public PaintingTypes getType() { return type; }
    public void setType(PaintingTypes value) {
        if (value == PaintingTypes.OILPAINTING || value == PaintingTypes.WATERCOLOUR
            || value == PaintingTypes.PASTEL)
            type = value;
        else
            System.out.println("Value introduced is not a valid painting type.");
    }

    public float getWidth() { return width; }
    public void setWidth(float value) {
        if (value > 0)
            width = value;
        else
            System.out.println("The number introduced MUST be bigger than zero.");

    }

    public float getHeight() { return height; }
    public void setHeight(float value) {
        if (value > 0)
            height = value;
        else
            System.out.println("The number introduced MUST be bigger than zero.");
    }

    @Override
    public String toString() {
        return
                super.toString() +
                " type = " + type +
                ", width = " + width +
                ", height = " + height;
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;

        if ( !(other instanceof Painting) )
            return false;

        Painting otherPainting = (Painting)other;
        return (this.type == otherPainting.type &&
                this.width == otherPainting.width &&
                this.height == otherPainting.height);
    }
}
