package um.si.de4a.model.json;

import com.google.gson.annotations.SerializedName;

public class VerifiableCredential {
    @SerializedName("@context")
     private String[] context;
     private String id;
     private String[] type;
     private String issuer;
     private String issuanceDate;
     private String validFrom;
     private String expirationDate;
     private CredentialSubject credentialSubject;
    // private CredentialStatus credentialStatus;
    // private CredentialSchema credentialSchema;
    //  private Evidence evidence;


    public VerifiableCredential(String[] context, String id, String[] type, String issuer, String issuanceDate, String validFrom, String expirationDate, CredentialSubject credentialSubject) {
        this.context = context;
        this.id = id;
        this.type = type;
        this.issuer = issuer;
        this.issuanceDate = issuanceDate;
        this.validFrom = validFrom;
        this.expirationDate = expirationDate;
        this.credentialSubject = credentialSubject;
    }

    public String[] getContext() {
        return context;
    }

    public void setContext(String[] context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(String issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public CredentialSubject getCredentialSubject() {
        return credentialSubject;
    }

    public void setCredentialSubject(CredentialSubject credentialSubject) {
        this.credentialSubject = credentialSubject;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    /*
    public CredentialStatus getCredentialStatus() {
        return credentialStatus;
    }

    public void setCredentialStatus(CredentialStatus credentialStatus) {
        this.credentialStatus = credentialStatus;
    }


    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }*/
}
