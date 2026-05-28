package org.example.siimiltech.dto;


import lombok.Data;

import lombok.Data;
import java.util.List;

@Data
public class EmailRequest {

    private ConfigParams configParams;
    private Receivers receivers;
    private Email email;

    @Data
    public static class ConfigParams {
        private String idUser;
        private String idMessage;
    }

    @Data
    public static class Receivers {
        private String emailOrigen;
        private List<String> to;
        private List<String> copyTo;
        private List<String> hiddenCopyTo;
    }

    @Data
    public static class Email {
        private String subject;
        private String urlHeader;
        private String urlFooter;
        private String message;
        private List<String> url_files;
    }
}