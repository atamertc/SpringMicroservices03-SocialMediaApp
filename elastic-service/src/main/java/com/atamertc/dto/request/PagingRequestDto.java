package com.atamertc.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingRequestDto {
    //Her bir istek icin gondermek istedigimiz kayit sayisi
    Integer pageSize;
    //Su an talep edip bulundugumuz sayfa numarasi
    Integer currentPage;
    //Hangi alana gore siralama yapilacak
    String sortParameter;
    //Siralama yonu, ASC, DESC
    String direction;


}
