import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {

    public static void main(String[] args) {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();

            saxParser.parse("contacts.xml", new myXMLparser());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

class myXMLparser extends DefaultHandler {
    protected String currentTag;
    protected String tagContent;

    // Tag opening found
    public void startElement( String uri, String localName, String qName,
                             Attributes attributes ) throws SAXException {

        currentTag = qName;
        if (currentTag.equals("contact")) {
            System.out.println("ID: " + attributes.getValue("id"));
        }
    }

    // Tag content
    public void characters( char ch[], int start, int length ) throws SAXException {
        tagContent = new String( ch, start, length );
    }

    // Tag ending
    public void endElement( String uri, String localName, String qName )
                throws SAXException {
        if ( !currentTag.isEmpty() ) {
            if (currentTag.equals("name"))
                System.out.print(" " + "Full name: " + tagContent);
            currentTag = "";
            if (currentTag.equals("surname"))
                System.out.println(" " + tagContent);
            currentTag = "";

            if (currentTag.equals("cell"))
                System.out.print(" " + currentTag + " number: " + tagContent);
            currentTag = "";
            if (currentTag.equals("work"))
                System.out.print(" " + currentTag + " number: " + tagContent);
            currentTag = "";
            if (currentTag.equals("home"))
                System.out.print(" " + currentTag + " number: " + tagContent);
            currentTag = "";
        }
    }
}
