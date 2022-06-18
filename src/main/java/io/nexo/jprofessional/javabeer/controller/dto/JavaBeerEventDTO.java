package io.nexo.jprofessional.javabeer.controller.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JavaBeerEventDTO {
    private Long id;
    private String sponsor;
    private String location;
    private LocalDateTime startAt;
}
