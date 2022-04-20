package um.si.de4a.resources.webhook;

import jnr.ffi.annotations.Encoding;
import org.jboss.resteasy.annotations.ContentEncoding;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import um.si.de4a.AppConfig;
import um.si.de4a.util.DE4ALogger;

import javax.ws.rs.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Path("/webhook")
public class WebHookResource {

    private AppConfig appConfig = null;

    @POST
    @Consumes({"application/json", "text/plain", "text/html", "application/x-www-form-urlencoded", "*/*"})
    @Produces({"application/json", "text/plain", "text/html", "application/x-www-form-urlencoded", "*/*"})
    public void receiveWebhookInfo(String contents) throws IOException, ParseException {
        Logger logger = DE4ALogger.getLogger();
        LogRecord logRecordInfo = new LogRecord(Level.INFO, "");
        LogRecord logRecordSevere = new LogRecord(Level.SEVERE, "");

        System.out.println("Received webhook message: " + contents.toString());
        JSONParser jsonParser = new JSONParser();

        JSONObject jsonContents = (JSONObject) jsonParser.parse(contents);
        System.out.println("Topic name: " + jsonContents.get("topic"));

        //appConfig = new AppConfig();

    }
}

