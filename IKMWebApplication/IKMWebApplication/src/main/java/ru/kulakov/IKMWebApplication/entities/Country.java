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
@Table(name = "countries", schema = "public", indexes = {
        @Index(name = "countries_name_key", columnList = "name", unique = true)
})
public class Country {
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
    @JoinColumn(name = "continent_id", nullable = false)
    private Continent continent;

}