package com.feisystems.bham.infrastructure;



public interface EmailServicesGateway {

    public void sendVerificationToken(EmailServiceTokenModel model);
}
