package io.nexo.jprofessional.javabeer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.AUTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class JavaBeerEvent {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String sponsor;

    private String location;

    private LocalDateTime startAt;
}
