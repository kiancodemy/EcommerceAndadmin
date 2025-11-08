package com.Main.Ecommerce.auth.dto.request;

import lombok.Builder;

@Builder
public record EmailSenderRequest( String token,String email) {
}
