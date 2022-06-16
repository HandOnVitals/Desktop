package hov.libs;
import pt.gov.cartaodecidadao.*;

public class CardReader {
    static {
        try {
            System.loadLibrary("pteidlibj"); // ??
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load. \n" + e);
            System.exit(1);
        }
    }

    public CardReader() {
        try {
            PTEID_ReaderSet.initSDK();
        // (...) - Código restante
            PTEID_ReaderSet.releaseSDK();
        } catch (PTEID_Exception e) {
            System.err.println("Error: " + e.GetMessage());
        }
    }
}

