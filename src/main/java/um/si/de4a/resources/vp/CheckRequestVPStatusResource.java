package um.si.de4a.resources.vp;


import org.jboss.resteasy.annotations.Query;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import um.si.de4a.aries.AriesUtil;
import um.si.de4a.db.DBUtil;
import um.si.de4a.db.VCStatusEnum;
import um.si.de4a.db.VPStatus;
import um.si.de4a.db.VPStatusEnum;
import um.si.de4a.util.DE4ALogger;

import javax.ws.rs.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Path("/check-request-vp-response")
public class CheckRequestVPStatusResource {

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("{userId}")
    public int getStatus(@PathParam("userId") String userId) throws IOException {
        Logger logger = DE4ALogger.getLogger();
        LogRecord logRecordInfo = new LogRecord(Level.INFO, "");
        LogRecord logRecordSevere = new LogRecord(Level.SEVERE, "");

        int vpRequestStatus = 0;
        DBUtil dbUtil = new DBUtil();
        // DONE: call database getVPStatus(userID): VPStatus object
        VPStatus vpStatus = null;
        try{
            vpStatus = dbUtil.getVPStatus(userId);
        }
        catch(Exception ex){
            logRecordSevere.setMessage("CHECK-VP-REQUEST-STATUS: Error accessing data on Authority Agent DR.");
            Object[] params = new Object[]{"Authority Agent DR", "eProcedure Portal DE", "1010"};
            logRecordSevere.setParameters(params);
            logger.log(logRecordSevere);
        }
        // if (VPStatus == null): return -1 (request was never sent)
        if(vpStatus == null)
            vpRequestStatus = -1; // return -1 (request was never sent)
        else{

            if (vpStatus.getVPStatusEnum() == VPStatusEnum.VP_RECEIVED)
                vpRequestStatus = 1; // return 1 (VP received)
            else if (vpStatus.getVPStatusEnum() == VPStatusEnum.VP_REJECTED)
                vpRequestStatus = -2; // return -2 (request rejected)
            else if(vpStatus.getVPStatusEnum() == VPStatusEnum.VP_VALID)
                vpRequestStatus = 2;
            else if (vpStatus.getVPStatusEnum() == VPStatusEnum.VP_NOT_VALID)
                vpRequestStatus = -3;
            else if (vpStatus.getVPStatusEnum() == VPStatusEnum.REQUEST_SENT){
                // DONE: call Aries /presentproof/actions: [action]
                AriesUtil ariesUtil = new AriesUtil();
                try {
                    JSONObject action = ariesUtil.getActionVP(vpStatus.getPiid());
                    if (action != null){
                        JSONObject msg = (JSONObject) action.get("Msg");

                        if(msg.get("@type").equals("https://didcomm.org/present-proof/2.0/presentation") && msg.get("description") == null){
                            boolean acceptResult = ariesUtil.acceptPresentation(vpStatus.getPiid(), new NamesObj(new String[]{"vp-" + vpStatus.getUserId() + "-" + vpStatus.getPiid()}));
                            if(acceptResult == true){
                                boolean updateStatus = false;
                                try {
                                    updateStatus = dbUtil.updateVPStatus(userId, "vp-" + vpStatus.getUserId() + "-" + vpStatus.getPiid(), VPStatusEnum.VP_RECEIVED);
                                    logRecordInfo.setMessage("CHECK-VP-REQUEST-STATUS: Stored current state in the Authority Agent DR database.");
                                    Object[] params = new Object[]{"Authority Agent DR", "eProcedure Portal DE", "01006"};
                                    logRecordInfo.setParameters(params);
                                    logger.log(logRecordInfo);
                                } catch (Exception ex) {
                                    logRecordSevere.setMessage("CHECK-VP-REQUEST-STATUS: Error saving data on Authority Agent DR.");
                                    Object[] params = new Object[]{"Authority Agent DR", "eProcedure Portal DE", "1010"};
                                    logRecordSevere.setParameters(params);
                                    logger.log(logRecordSevere);
                                }

                                if (updateStatus == true)
                                    vpRequestStatus = 1; // (vp received)

                            }
                        }
                        else {
                            JSONObject description = (JSONObject) msg.get("description");
                            if (description.get("code").equals("rejected")) {
                                try{
                                    dbUtil.updateVPStatus(userId, VPStatusEnum.VP_REJECTED);
                                    logRecordInfo.setMessage("CHECK-VP-REQUEST-STATUS: Stored current state in the Authority Agent DR database.");
                                    Object[] params = new Object[]{"Authority Agent DR", "eProcedure Portal DE", "01006"};
                                    logRecordInfo.setParameters(params);
                                    logger.log(logRecordInfo);
                                }
                                catch(Exception ex){
                                    logRecordSevere.setMessage("CHECK-VP-REQUEST-STATUS: Error saving data on Authority Agent DR.");
                                    Object[] params = new Object[]{"Authority Agent DR", "eProcedure Portal DE", "1010"};
                                    logRecordSevere.setParameters(params);
                                    logger.log(logRecordSevere);
                                }
                                vpRequestStatus = -2; // return -2 (vp rejected)
                            }
                        }
                    }
                } catch (ParseException e) {
                   // e.printStackTrace();
                    logRecordSevere.setMessage( "CHECK-VP-REQUEST-STATUS: Error on response from the Aries Government Agent.");
                    Object[] params = new Object[]{"Authority Agent DR", "Aries Government Agent", "1002"};
                    logRecordSevere.setParameters(params);
                    logger.log(logRecordSevere);
                }
            }
        }

        return vpRequestStatus;
    }
}
