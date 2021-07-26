import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class main {
    public static void main(String[] args) throws Exception{
        XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("XMLEXP.xml")));
        xmlDecoder.readObject();
        xmlDecoder.close();
    }
}