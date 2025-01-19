package ru.kulakov.IKMWebApplication.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "continents", schema = "public", indexes = {
        @Index(name = "continents_name_key", columnList = "name", unique = true)
})
public class Continent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "planet_id", nullable = false)
    private Planet planet;

}