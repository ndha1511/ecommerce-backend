package com.code.salesappbackend.utils;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String recipient;
    private String msgBody;
    private String subject;
    private MultipartFile attachment;
}
