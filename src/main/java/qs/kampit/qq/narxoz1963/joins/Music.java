package qs.sukaworkplea.qq.narxoz1963.joins;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "music")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "singer")
    private String singer;

    @Column(name = "songname")
    private String songname;

    @Column(name = "date")
    private int date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

}
