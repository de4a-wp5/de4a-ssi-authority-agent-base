package um.si.de4a.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SignedVerifiableCredentialUpdated {
    @SerializedName("@context")
    private String[] context;
    private String id;
    private String[] type;
    private String issuer;
    @SerializedName("issuanceDate")
    private String issuanceDate;
    private String issued;
    @SerializedName("validFrom")
    private String validFrom;
    @SerializedName("expirationDate")
    private String expirationDate;
    @SerializedName("credentialSubject")
    private CredentialSubjectUpdated credentialSubject;
    @SerializedName("credentialSchema")
    private ArrayList<CredentialSchema> credentialSchema;
    private Proof proof;

    public SignedVerifiableCredentialUpdated(String[] context, String id, String[] type, String issuer, String issuanceDate, String issued, String validFrom, String expirationDate, CredentialSubjectUpdated credentialSubject, ArrayList<CredentialSchema> credentialSchema, Proof proof) {
        this.context = context;
        this.id = id;
        this.type = type;
        this.issuer = issuer;
        this.issuanceDate = issuanceDate;
        this.issued = issued;
        this.validFrom = validFrom;
        this.expirationDate = expirationDate;
        this.credentialSubject = credentialSubject;
        this.credentialSchema = credentialSchema;
        this.proof = proof;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public void setCredentialSchema(ArrayList<CredentialSchema> credentialSchema) {
        this.credentialSchema = credentialSchema;
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

    public CredentialSubjectUpdated getCredentialSubject() {
        return credentialSubject;
    }

    public void setCredentialSubject(CredentialSubjectUpdated credentialSubject) {
        this.credentialSubject = credentialSubject;
    }

    public Proof getProof() {
        return proof;
    }

    public void setProof(Proof proof) {
        this.proof = proof;
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
}
