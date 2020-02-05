package com.conekta.app.Implementations;

import com.conekta.app.Models.Token;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

public class MapTokens implements MapFunction<String, Token> {

    static private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Token map(String jsonStr) throws Exception {

        JsonNode rootJson = mapper.readTree(jsonStr);
        JsonNode tokenIDNode  = rootJson.get("token_id");
        JsonNode ipNode = rootJson.get("ip_address");
        JsonNode deviceNode = rootJson.get("device_fingerprint");
        JsonNode cardHashNode = rootJson.get("card_hash");
        JsonNode binNumberNode = rootJson.get("bin_number");
        JsonNode lastFourNode = rootJson.get("last4");
        JsonNode nameNode = rootJson.get("name");


        String tokenId = tokenIDNode == null ? "" : tokenIDNode.asText();
        String ip = ipNode == null ? "" : ipNode.asText();
        String device = deviceNode == null ? "" : deviceNode.asText();
        String cardHash = cardHashNode == null ? "" : cardHashNode.asText();
        String binNumber = binNumberNode == null ? "" : binNumberNode.asText();
        String lastFour = lastFourNode == null ? "" : lastFourNode.asText();
        String name = nameNode == null ? "" : nameNode.asText();

        return new Token(tokenId, ip, device, cardHash, binNumber, lastFour, name);
    }
}
