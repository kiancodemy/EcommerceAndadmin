package com.Main.Ecommerce.dto.request;

import lombok.Builder;

@Builder
public record EmailSenderRequest( String token,String email) {
}
