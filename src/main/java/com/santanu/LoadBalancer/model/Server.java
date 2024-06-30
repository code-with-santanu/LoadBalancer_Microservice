package com.santanu.LoadBalancer.model;

import jakarta.annotation.Nullable;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Component
public class Server {

    private int id;

    @Nullable
    private String serverIp;

    @Nullable
    private int port;

    @NonNull
    private Integer maxWeight;

    private Long currentLoad;

    @Value("0")
    private float loadFactor; // (activeConnection/ maxWeight)

    private Boolean status;

    @NonNull
    private String serverURL;

}
