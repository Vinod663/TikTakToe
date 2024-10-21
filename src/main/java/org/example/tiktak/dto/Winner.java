package org.example.tiktak.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Winner {
    private String playerName;
    private boolean isAiWinner;
}
