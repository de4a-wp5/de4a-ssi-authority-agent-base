package um.si.de4a.resources.vp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import um.si.de4a.aries.AriesUtil;
import um.si.de4a.db.DBUtil;
import um.si.de4a.db.DIDConn;
import um.si.de4a.db.VPStatusEnum;

import javax.ws.rs.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

@Path("/send-vp-request")
public class SendVPRequestResource {
    @POST
    @Consumes("application/json")
    @Produces("application/json")
     public boolean sendVPRequest(String vpRequest) throws IOException, ParseException {
        boolean vpRequestStatus = false;
        JSONObject jsonRequest = null;
        DBUtil dbUtil = new DBUtil();

        JSONParser jsonParser = new JSONParser();
        try {
            jsonRequest = (JSONObject) jsonParser.parse(vpRequest);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(jsonRequest != null) {
            System.out.println("[SEND VP REQUEST] User ID: " + jsonRequest.get("userId"));

            String userID = jsonRequest.get("userId").toString();

            // DONE: call database getDIDConnStatus(userID): DIDConn object
            DIDConn userDIDConn = dbUtil.getDIDConnStatus(userID);

            if(userDIDConn != null) { // if invitation is generated
                if (!userDIDConn.getConnectionId().equals("")) { // if DIDConn is established
                    // DONE: generate VPRequest (format, myDID, theirDID): VPRequest object
                    String format = new JsonObj
                            (new String[]{"https://www.w3.org/2018/credentials/v1", "https://www.w3.org/2018/credentials/examples/v1"},
                                    new String[]{"VerifiablePresentation", "CredentialManagerPresentation"}).toString();
                    RequestPresentationAttachObj rpa = new RequestPresentationAttachObj(new DataObj(Base64.getEncoder().encodeToString(format.getBytes(StandardCharsets.UTF_8))));
                    ArrayList<RequestPresentationAttachObj> rpaList = new ArrayList<>();
                    rpaList.add(rpa);
                    VPRequest vpRequestObj = new VPRequest(userDIDConn.getMyDID(), new RequestPresentationObj(rpaList), userDIDConn.getTheirDID());

                    // DONE: call Aries /presentproof/send-request-presentation(VPRequest):  PIID
                    AriesUtil ariesUtil = new AriesUtil();
                    String piid = ariesUtil.sendRequest(vpRequestObj);
                    if (piid != "") {
                        System.out.println("[SEND VP REQUEST] Received PIID: " + piid);

                        // DONE: call database saveVPStatus(userId, PIID, status: request_sent): boolean
                        boolean response = dbUtil.saveVPStatus(userID, piid, null, VPStatusEnum.REQUEST_SENT);
                        if (response == true)
                            vpRequestStatus = true;
                    }
                }
            }
        }
        return vpRequestStatus;
    }
}
