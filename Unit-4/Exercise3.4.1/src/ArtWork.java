import java.util.Date;

enum Styles {
    GRECOROMAN,
    NEOCLASSIC,
    CUBISM
}

class ArtWork {

    protected int code;
    protected String title;
    protected Date dated;
    protected Styles style;
    protected String authorCode;

    public ArtWork() {

    }

    public ArtWork(int code, String title, Date dated, Styles style, String authorCode) {
        this.code = code;
        this.title = title;
        this.dated = dated;
        this.style = style;
        this.authorCode = authorCode;
    }

    public int getCode() { return code; }
    public void setCode(int value) { code = value;}

    public String getTitle() { return title; }
    public void setTitle(String value) { title = value; }

    public Date getDate() { return dated; }
    public void setDated(Date date) { dated = date; }

    public Styles getStyle() { return style; }
    public void setStyle(Styles value) {
        if (value == Styles.GRECOROMAN || value == Styles.NEOCLASSIC || value == Styles.CUBISM)
            style = value;
        else
            System.out.println("Value introduced is not a valid style.");
    }

    public String getAuthorCode() { return authorCode; }
    public void setAuthorCode(String value) {

        // Checks that code has a format beginning in 3 letters and 4 numbers (initials and year of birth)
        if (value.matches("...\\d\\d\\d\\d"))
            authorCode = value;
    }

    @Override
    public String toString() {
        return "ArtWork: " +
                "code = " + code +
                ", title = '" + title + '\'' +
                ", dated = " + dated +
                ", style = " + style +
                ", authorCode = " + authorCode;
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;

        if ( !(other instanceof ArtWork))
            return false;

        ArtWork otherArtWork = (ArtWork)other;
        return (this.code == otherArtWork.code &&
                this.title.equals(otherArtWork.title) &&
                this.dated == otherArtWork.dated &&
                this.style == otherArtWork.style &&
                this.authorCode.equals(otherArtWork.authorCode));
    }

}
