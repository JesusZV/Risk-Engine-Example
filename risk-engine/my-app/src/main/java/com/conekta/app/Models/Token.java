package com.conekta.app.Models;

public class Token {

    private String _tokenId;
    private String _ipAddress;
    private String _deviceFingerprint;
    private String _cardHash;
    private String _binNumber;
    private String _lastFour;
    private String _name;

    public Token(String tokenId, String ipAddress, String deviceFingerPrint, String cardHash, String binNumber,
                 String lastFour, String name) {

        this._tokenId = tokenId;
        this._ipAddress = ipAddress;
        this._deviceFingerprint = deviceFingerPrint;
        this._cardHash = cardHash;
        this._binNumber = binNumber;
        this._lastFour = lastFour;
        this._name  = name;
    }

    public String get_tokenId() {
        return _tokenId;
    }

    public String getIpAddress() {
        return _ipAddress;
    }

    public String getDeviceFingerprint() {
        return _deviceFingerprint;
    }

    public String getCardHash() {
        return _cardHash;
    }

    public String getBinNumber() {
        return _binNumber;
    }

    public String getLastFour() {
        return _lastFour;
    }

    public String getName() {
        return _name;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token_id=" + _tokenId  +
                ", ip_address = '" + _ipAddress + '\'' +
                ", device_fingerprint = '" + _deviceFingerprint + '\'' +
                ", card_hash = '" + _cardHash + '\'' +
                ", bin_number = '" + _binNumber + '\'' +
                ", last4 = '" + _lastFour + '\'' +
                ", name = '" + _name + '\'' +
                '}';
    }

}
